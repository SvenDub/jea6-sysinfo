package nl.svendubbeld.fontys.sysinfo.collector.net

import nl.svendubbeld.fontys.sysinfo.collector.StatsProvider
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.streams.toList

@Component
class NetStatsProvider : StatsProvider {

    @Value("\${sysinfo.net.interfaces}")
    lateinit var interfaces: Array<String>

    private val pattern = Pattern.compile("\\D*(?<rx>\\d+\\.\\d+).*(?<tx>\\d+\\.\\d+)\\D*")

    override fun getStats(): List<StatsData> {
        return interfaces.flatMap {iface ->
            val ifstat = ProcessBuilder()
                    .command("ifstat", "-i", iface, "1", "1")
                    .start()

            if (ifstat.waitFor(5, TimeUnit.SECONDS)) {
                BufferedReader(InputStreamReader(ifstat.inputStream)).use {
                    val result = it.lines().toList().lastOrNull()

                    if (result != null) {
                        val matcher = pattern.matcher(result)
                        if (matcher.find()) {
                            val rx = matcher.group("rx").toDouble()
                            val tx = matcher.group("tx").toDouble()

                            return@flatMap listOf(
                                    StatsData("net.$iface.rx", rx * 1024, "bytes"),
                                    StatsData("net.$iface.tx", tx * 1024, "bytes")
                            )
                        }
                    }
                }
            } else {
                ifstat.destroy()
            }

            emptyList<StatsData>()
        }
    }
}
