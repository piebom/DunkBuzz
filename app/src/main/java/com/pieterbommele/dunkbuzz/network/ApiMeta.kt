package com.pieterbommele.dunkbuzz.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiMeta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)