package nl.svendubbeld.fontys.sysinfo.collector

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Load the [StatsController].
 */
@Configuration
@ComponentScan(basePackageClasses = [StatsController::class])
class StatsConfiguration
