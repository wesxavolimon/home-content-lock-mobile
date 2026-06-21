package com.homecontentlock.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homecontentlock.domain.model.CustomBlockedSite
import java.time.Instant

@Entity(tableName = "custom_sites")
data class CustomSiteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val domain: String,
    val addedAt: Long, // Store as Long for Room compatibility
    val isActive: Boolean = true
) {
    fun toDomainModel(): CustomBlockedSite {
        return CustomBlockedSite(
            id = id,
            domain = domain,
            addedAt = Instant.ofEpochMilli(addedAt),
            isActive = isActive
        )
    }

    companion object {
        fun fromDomainModel(model: CustomBlockedSite): CustomSiteEntity {
            return CustomSiteEntity(
                id = model.id,
                domain = model.domain,
                addedAt = model.addedAt.toEpochMilli(),
                isActive = model.isActive
            )
        }
    }
}