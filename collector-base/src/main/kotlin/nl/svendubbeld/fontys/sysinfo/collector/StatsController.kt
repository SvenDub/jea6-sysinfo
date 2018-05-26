package nl.svendubbeld.fontys.sysinfo.collector

import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stats")
class StatsController(private val statsProvider: StatsProvider) {

    @GetMapping
    fun getStats(): ResponseEntity<StatsResponse> {
        return ResponseEntity.ok(statsProvider.getStats())
    }
}
