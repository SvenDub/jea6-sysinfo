package nl.svendubbeld.fontys.sysinfo.aggregator

import nl.svendubbeld.fontys.sysinfo.aggregator.repository.StatsDataRepository
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import nl.svendubbeld.fontys.sysinfo.shared.typeRef
import org.slf4j.LoggerFactory
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class StatsCollector(private val discoveryClient: DiscoveryClient, private val restTemplate: RestTemplate, private val repository: StatsDataRepository) {
    private val logger = LoggerFactory.getLogger(StatsCollector::class.java)

    @Scheduled(fixedRateString = "\${sysinfo.refresh-interval}")
    fun refresh() {
        logger.info("Running!")
        services().forEach {
            logger.info("Collecting from ${it.metadata["collector"]} (${it.uri})")
            //val response = restTemplate.getForObject("${it.uri}/stats", StatsResponse::class.java)
            val response = restTemplate.exchange("${it.uri}/stats", HttpMethod.GET, null, typeRef<List<StatsData>>())
            response.body?.let {
                repository.saveAll(it)
            }
        }
    }

    fun services() = discoveryClient.services
            .flatMap(discoveryClient::getInstances)
            .filter { it.metadata.containsKey("collector") }
            .groupBy { it.metadata["collector"] }
            .map { it.value.firstOrNull() }
            .filterNotNull()
            .sortedBy { it.metadata["collector"] }
}
