package nl.svendubbeld.fontys.sysinfo.aggregator.repository

import nl.svendubbeld.fontys.sysinfo.shared.entity.StatsResponse
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface StatsResponseRepository : PagingAndSortingRepository<StatsResponse, Long?>
