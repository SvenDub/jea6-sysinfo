package nl.svendubbeld.fontys.sysinfo.aggregator

import nl.svendubbeld.fontys.sysinfo.aggregator.repository.StatsDataRepository
import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/aggregate")
class AggregationController(val repository: StatsDataRepository) {

    @GetMapping
    fun getAggregate(@RequestParam from: Long, @RequestParam(required = false) to: Long?): Iterable<StatsData> {
        val fromDate = Instant.ofEpochMilli(from).atOffset(ZoneOffset.UTC)
        val toDate = to?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) } ?: OffsetDateTime.now()

        return repository.findAllByTimeBetween(fromDate, toDate)
    }

    @GetMapping("{key}")
    fun getAggregateByKey(@PathVariable key: String, @RequestParam from: Long, @RequestParam(required = false) to: Long?): Iterable<StatsData> {
        val fromDate = Instant.ofEpochMilli(from).atOffset(ZoneOffset.UTC)
        val toDate = to?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) } ?: OffsetDateTime.now()

        return repository.findAllByKeyAndTimeBetween(key, fromDate, toDate)
    }
}
