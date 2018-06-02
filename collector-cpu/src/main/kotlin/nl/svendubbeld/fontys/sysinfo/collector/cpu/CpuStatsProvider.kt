package nl.svendubbeld.fontys.sysinfo.collector.cpu

import com.sun.management.OperatingSystemMXBean
import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory

@Component
class CpuStatsProvider : StatsProvider {
    override fun getStats(): List<StatsData> {
        val sysinfo = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean

        return listOf(
                StatsData("cpu.load", sysinfo.systemCpuLoad, "percentage"),
                StatsData("cpu.count", sysinfo.availableProcessors),
                StatsData("cpu.load_avg", sysinfo.systemLoadAverage)
        )
    }
}
