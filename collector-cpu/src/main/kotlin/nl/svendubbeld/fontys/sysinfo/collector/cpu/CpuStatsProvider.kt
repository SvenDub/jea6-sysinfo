package nl.svendubbeld.fontys.sysinfo.collector.cpu

import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.collector.StatsResponse
import org.springframework.stereotype.Component

@Component
class CpuStatsProvider : StatsProvider {
    override fun getStats(): StatsResponse {
        return StatsResponse(emptyMap())
    }
}
