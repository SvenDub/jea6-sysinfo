package nl.svendubbeld.fontys.sysinfo.shared.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class StatsData(
        var value: Number,
        var unit: String = "number",
        @Id
        @GeneratedValue
        @JsonIgnore
        var id: Long? = null
)
