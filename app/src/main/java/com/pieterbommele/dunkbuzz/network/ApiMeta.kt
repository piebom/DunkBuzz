package com.pieterbommele.dunkbuzz.network

import kotlinx.serialization.Serializable

/**
 * Represents metadata information related to an API response.
 *
 * @property total_pages The total number of pages available in the API result set.
 * @property current_page The current page number of the API result set.
 * @property next_page The next page number, if available. Null if there is no next page.
 * @property per_page The number of items per page in the API result set.
 * @property total_count The total count of items in the API result set.
 */
@Serializable
data class ApiMeta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)
