package nl.svendubbeld.fontys.sysinfo.collector.cpu

import nl.svendubbeld.fontys.sysinfo.collector.EnableStats
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableStats
class CpuApplication

fun main(args: Array<String>) {
    runApplication<CpuApplication>(*args)
}
