package nl.svendubbeld.fontys.sysinfo.shared.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class StatsData(
        @Column(name = "statkey")
        var key: String,
        var value: Number,
        var unit: String = "number",
        var time: OffsetDateTime = OffsetDateTime.now(),
        @Id
        @GeneratedValue
        @JsonIgnore
        var id: Long? = null
)
