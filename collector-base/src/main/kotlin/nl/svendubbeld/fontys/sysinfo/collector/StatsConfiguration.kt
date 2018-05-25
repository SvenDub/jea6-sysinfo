package nl.svendubbeld.fontys.sysinfo.collector

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer



/**
 * Load the [StatsController].
 */
@Configuration
@ComponentScan(basePackageClasses = [StatsController::class])
class StatsConfiguration {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/stats").allowedOrigins("*")
            }
        }
    }
}
