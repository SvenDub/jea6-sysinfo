package nl.svendubbeld.fontys.sysinfo.collector

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import

/**
 * Adding this annotation to an [@Configuration][Configuration] class imports the configuration from [StatsConfiguration].
 * This requires the presence of a [StatsProvider] on the classpath.
 *
 * @see StatsConfiguration
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@MustBeDocumented
@Import(StatsConfiguration::class)
annotation class EnableStats
