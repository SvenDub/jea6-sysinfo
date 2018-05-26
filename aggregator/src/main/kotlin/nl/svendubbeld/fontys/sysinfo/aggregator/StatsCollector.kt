package nl.svendubbeld.fontys.sysinfo.aggregator

import nl.svendubbeld.fontys.sysinfo.aggregator.repository.StatsResponseRepository
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsResponse
import org.slf4j.LoggerFactory
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class StatsCollector(private val discoveryClient: DiscoveryClient, private val restTemplate: RestTemplate, private val repository: StatsResponseRepository) {
    private val logger = LoggerFactory.getLogger(StatsCollector::class.java)

    @Scheduled(fixedRateString = "\${sysinfo.refresh-interval}")
    fun refresh() {
        logger.info("Running!")
        services().forEach {
            logger.info("Collecting from ${it.metadata["collector"]} (${it.uri})")
            val response = restTemplate.getForObject("${it.uri}/stats", StatsResponse::class.java)
            response?.let {
                repository.save(it)
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
