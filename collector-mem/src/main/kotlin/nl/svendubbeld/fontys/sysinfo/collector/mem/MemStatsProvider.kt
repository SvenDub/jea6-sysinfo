package nl.svendubbeld.fontys.sysinfo.collector.mem

import com.sun.management.OperatingSystemMXBean
import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory

@Component
class MemStatsProvider : StatsProvider {
    override fun getStats(): List<StatsData> {
        val sysinfo = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

        return listOf(
                 StatsData("mem.total", sysinfo.totalPhysicalMemorySize, "bytes"),
                 StatsData("mem.free", sysinfo.freePhysicalMemorySize, "bytes"),
                 StatsData("swap.total", sysinfo.totalSwapSpaceSize, "bytes"),
                 StatsData("swap.free", sysinfo.freeSwapSpaceSize, "bytes")
        )
    }
}
