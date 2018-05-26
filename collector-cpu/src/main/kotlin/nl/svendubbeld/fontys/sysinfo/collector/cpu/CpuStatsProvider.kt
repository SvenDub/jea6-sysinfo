package nl.svendubbeld.fontys.sysinfo.collector.cpu

import com.sun.management.OperatingSystemMXBean
import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsResponse
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.time.OffsetDateTime

@Component
class CpuStatsProvider : StatsProvider {
    override fun getStats(): StatsResponse {
        val sysinfo = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

        return StatsResponse(OffsetDateTime.now(), mapOf(
                "cpu.load" to StatsData(sysinfo.systemCpuLoad),
                "cpu.counts" to StatsData(sysinfo.availableProcessors),
                "cpu.load_avg" to StatsData(sysinfo.systemLoadAverage)
        ))
    }
}
