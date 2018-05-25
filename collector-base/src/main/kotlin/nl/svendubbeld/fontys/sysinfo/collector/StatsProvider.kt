package nl.svendubbeld.fontys.sysinfo.collector

import nl.svendubbeld.fontys.sysinfo.shared.StatsResponse

/**
 * Provides system stats.
 */
interface StatsProvider {

    /**
     * Get the current stats.
     */
    fun getStats(): StatsResponse
}
