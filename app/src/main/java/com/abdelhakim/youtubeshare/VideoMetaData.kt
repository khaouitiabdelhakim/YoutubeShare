package com.abdelhakim.youtubeshare

data class VideoMetadata(
    val title: String,
    val authorName: String,
    val authorUrl: String,
    val type: String,
    val height: Int,
    val width: Int,
    val version: String,
    val providerName: String,
    val providerUrl: String,
    val thumbnailHeight: Int,
    val thumbnailWidth: Int,
    val thumbnailUrl: String,
    val html: String
){
    override fun toString(): String {
        return """
            |title: $title
            |authorName: $authorName
            |authorUrl: $authorUrl
            |thumbnailUrl: $thumbnailUrl
        """.trimMargin()
    }
}