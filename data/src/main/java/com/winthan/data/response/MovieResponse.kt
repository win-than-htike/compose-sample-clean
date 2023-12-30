package com.winthan.data.response

import com.google.gson.annotations.SerializedName
import com.winthan.data.response.dto.DatesDto
import com.winthan.data.response.dto.MovieDto

data class MovieResponse(
    @SerializedName("dates")
    val dates: DatesDto?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)