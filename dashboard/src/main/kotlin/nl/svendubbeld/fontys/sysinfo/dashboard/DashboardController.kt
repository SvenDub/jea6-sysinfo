package nl.svendubbeld.fontys.sysinfo.dashboard

import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class DashboardController(private val discoveryClient: DiscoveryClient) {

    @GetMapping
    fun getDashboard(model: Model) = "index"

    @ModelAttribute("services")
    fun services() = discoveryClient.services
            .flatMap(discoveryClient::getInstances)
            .filter { it.metadata.containsKey("collector") }
            .groupBy { it.metadata["collector"] }
            .map { it.value.firstOrNull() }
            .filterNotNull()
            .map { it.metadata["collector"] }
            .sortedBy { it }
}
