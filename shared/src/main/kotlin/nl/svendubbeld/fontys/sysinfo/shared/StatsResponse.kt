package nl.svendubbeld.fontys.sysinfo.shared

import java.time.OffsetDateTime

data class StatsResponse(var time: OffsetDateTime, var data: Map<String, StatsData>)
