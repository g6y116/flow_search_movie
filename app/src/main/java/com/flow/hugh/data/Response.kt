package com.flow.hugh.data

data class MovieListResponse(
    val display: Int,
    val items: List<MovieItemResponse>?,
    val lastBuildDate: String,
    val start: Int,
    val total: Int,
)

data class MovieItemResponse(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
) {
    fun toMovie() = this.run {
        Movie(
            title = title,
            link = link,
            image = image,
            subtitle = subtitle,
            pubDate = pubDate,
            director = director.split("|").dropLast(1),
            actor = actor.split("|").dropLast(1),
            userRating = userRating
        )
    }
}

