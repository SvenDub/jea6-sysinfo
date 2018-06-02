package nl.svendubbeld.fontys.sysinfo.dashboard

import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import nl.svendubbeld.fontys.sysinfo.shared.typeRef
import org.slf4j.LoggerFactory
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api")
class ApiController(private val discoveryClient: DiscoveryClient, private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(ApiController::class.java)

    private val aggregatorSerivce
        get() = discoveryClient.services
                .flatMap(discoveryClient::getInstances)
                .firstOrNull { it.metadata.containsKey("aggregator") }

    @GetMapping("/graph")
    fun getGraph(): ResponseEntity<List<Dataset>> {
        val uri = aggregatorSerivce?.uri ?: return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()

        val statsEntity = restTemplate.exchange("$uri/aggregate?from=${OffsetDateTime.now().minusHours(1).toInstant().toEpochMilli()}", HttpMethod.GET, null, typeRef<List<StatsData>>())
        if (!statsEntity.hasBody()) {
            return ResponseEntity.noContent().build()
        }

        val datasets = statsEntity.body!!
                .groupBy { it.key }
                .map { Dataset(it.key, it.value.map { Datapoint(it.time, it.value) }.sortedBy { it.t }) }

        return ResponseEntity.ok(datasets)
    }

    @GetMapping("/graph/{key}")
    fun getGraphByKey(@PathVariable key: String): ResponseEntity<List<Dataset>> {
        val uri = aggregatorSerivce?.uri ?: return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()

        val statsEntity = restTemplate.exchange("$uri/aggregate/$key?from=${OffsetDateTime.now().minusHours(1).toInstant().toEpochMilli()}", HttpMethod.GET, null, typeRef<List<StatsData>>())

        if (!statsEntity.hasBody()) {
            return ResponseEntity.noContent().build()
        }

        val datasets = statsEntity.body!!
                .groupBy { it.key }
                .map { Dataset(it.key, it.value.map { Datapoint(it.time, it.value) }.sortedBy { it.t }) }

        return ResponseEntity.ok(datasets)
    }

    @GetMapping("/stats/{key}")
    fun getStats(@PathVariable key: String): ResponseEntity<List<StatsData>> {
        val collectors = discoveryClient.services
                .flatMap(discoveryClient::getInstances)
                .filter { it.metadata.containsKey("collector") }
                .filter { it.metadata["collector"] == key }

        for (collector in collectors) {
            try {
                val statsEntity = restTemplate.exchange("${collector.uri}/stats", HttpMethod.GET, null, typeRef<List<StatsData>>())
                if (statsEntity.hasBody() && statsEntity.statusCode == HttpStatus.OK) {
                    return statsEntity
                }
            } catch (e: RestClientException) {
                logger.warn("Could not connect to stats collector '$key'.", e)
            }
        }

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
    }
}
