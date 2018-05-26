package nl.svendubbeld.fontys.sysinfo.shared.entity

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class StatsResponse(
        var time: OffsetDateTime,
        @OneToMany(cascade = [CascadeType.ALL])
        @MapKeyColumn(length = 150)
        var data: Map<String, StatsData>,
        @Id
        @GeneratedValue
        var id: Long? = null
)
