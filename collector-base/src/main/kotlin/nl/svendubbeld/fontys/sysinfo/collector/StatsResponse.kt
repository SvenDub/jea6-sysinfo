package nl.svendubbeld.fontys.sysinfo.collector

import java.time.OffsetDateTime

data class StatsResponse(var time: OffsetDateTime, var data: Map<String, Any>)
