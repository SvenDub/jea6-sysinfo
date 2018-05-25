package nl.svendubbeld.fontys.sysinfo.collector.mem

import com.sun.management.OperatingSystemMXBean
import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.collector.StatsResponse
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.time.OffsetDateTime

@Component
class MemStatsProvider : StatsProvider {
    override fun getStats(): StatsResponse {
        val sysinfo = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

        return StatsResponse(OffsetDateTime.now(), mapOf(
                "mem.total" to sysinfo.totalPhysicalMemorySize,
                "mem.free" to sysinfo.freePhysicalMemorySize,
                "swap.total" to sysinfo.totalSwapSpaceSize,
                "swap.free" to sysinfo.freeSwapSpaceSize
        ))
    }
}
