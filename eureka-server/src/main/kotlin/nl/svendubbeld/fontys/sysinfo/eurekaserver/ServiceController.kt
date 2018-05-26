package nl.svendubbeld.fontys.sysinfo.eurekaserver

import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController(private val discoveryClient: DiscoveryClient) {
    @RequestMapping("/service-instances/{applicationName}", produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun serviceInstancesByApplicationName(@PathVariable applicationName: String): List<ServiceInstance> =
            discoveryClient.getInstances(applicationName)

    @RequestMapping("/services", produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun services(): List<String> = discoveryClient.services
}
