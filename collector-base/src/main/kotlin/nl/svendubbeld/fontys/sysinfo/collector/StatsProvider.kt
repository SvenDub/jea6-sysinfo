package nl.svendubbeld.fontys.sysinfo.collector

/**
 * Provides system stats.
 */
interface StatsProvider {

    /**
     * Get the current stats.
     */
    fun getStats(): StatsResponse
}
