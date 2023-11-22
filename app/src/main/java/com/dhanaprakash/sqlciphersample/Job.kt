package com.dhanaprakash.sqlciphersample

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Job(
    @PrimaryKey
    val id: String = "",
    @Embedded
    var attributes: JobAttributes? = null,
    val status: String? = null
)

data class JobAttributes(
    val documents: List<DownloadDocument>? = null,
    val clientName: String? = null,
)

data class DownloadDocument(
    val fileName: String? = null,
    val url: String? = null
)