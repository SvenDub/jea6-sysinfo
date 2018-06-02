package nl.svendubbeld.fontys.sysinfo.aggregator.repository

import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsData
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface StatsDataRepository : PagingAndSortingRepository<StatsData, Long?> {
    fun findAllByTimeBetween(from: OffsetDateTime, to: OffsetDateTime): Iterable<StatsData>
    fun findAllByKeyAndTimeBetween(key: String, from: OffsetDateTime, to: OffsetDateTime): Iterable<StatsData>
}
