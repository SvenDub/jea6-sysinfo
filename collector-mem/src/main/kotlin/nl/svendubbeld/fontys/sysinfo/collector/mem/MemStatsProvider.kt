package nl.svendubbeld.fontys.sysinfo.collector.mem

import com.sun.management.OperatingSystemMXBean
import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.shared.StatsData
import nl.svendubbeld.fontys.sysinfo.shared.StatsResponse
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.time.OffsetDateTime

@Component
class MemStatsProvider : StatsProvider {
    override fun getStats(): StatsResponse {
        val sysinfo = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

        return StatsResponse(OffsetDateTime.now(), mapOf(
                "mem.total" to StatsData(sysinfo.totalPhysicalMemorySize, "bytes"),
                "mem.free" to StatsData(sysinfo.freePhysicalMemorySize, "bytes"),
                "swap.total" to StatsData(sysinfo.totalSwapSpaceSize, "bytes"),
                "swap.free" to StatsData(sysinfo.freeSwapSpaceSize, "bytes")
        ))
    }
}
