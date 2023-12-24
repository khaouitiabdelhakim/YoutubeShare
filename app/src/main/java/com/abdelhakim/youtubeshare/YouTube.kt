package com.abdelhakim.youtubeshare



import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object YouTube {

    suspend fun findTitle(link: String): String = withContext(Dispatchers.IO) {
        var title = "Nothing new"
        try {
            val connection = URL("https://www.youtube.com/oembed?url=$link&format=json").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                val response = StringBuilder()

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                bufferedReader.close()

                val json = JSONObject(response.toString())
                title = json.getString("title")
                Log.d("TOTO", "title: $title")
            } else {
                Log.e("TOTO", "Error fetching HTML content. Response Code: ${connection.responseCode}")
            }

        } catch (e: IOException) {
            Log.e("TOTO", "Error fetching HTML content: $e")
        }
        return@withContext title
    }

    suspend fun findMetaData(link: String): VideoMetadata = withContext(Dispatchers.IO) {
        var videoMetadata = VideoMetadata(
            title = "Unknown",
            authorName = "",
            authorUrl = "",
            type = "",
            height = 0,
            width = 0,
            version = "",
            providerName = "",
            providerUrl = "",
            thumbnailHeight = 0,
            thumbnailWidth = 0,
            thumbnailUrl = "",
            html = ""
        )

        try {
            val connection =
                URL("https://www.youtube.com/oembed?url=$link&format=json").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                val response = StringBuilder()

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                bufferedReader.close()

                val json = JSONObject(response.toString())
                videoMetadata = VideoMetadata(
                    title = json.getString("title"),
                    authorName = json.getString("author_name"),
                    authorUrl = json.getString("author_url"),
                    type = json.getString("type"),
                    height = json.getInt("height"),
                    width = json.getInt("width"),
                    version = json.getString("version"),
                    providerName = json.getString("provider_name"),
                    providerUrl = json.getString("provider_url"),
                    thumbnailHeight = json.getInt("thumbnail_height"),
                    thumbnailWidth = json.getInt("thumbnail_width"),
                    thumbnailUrl = json.getString("thumbnail_url"),
                    html = json.getString("html")
                )
                // Log the metadata if needed
                Log.d("TOTO", "Video Metadata: $videoMetadata")
            } else {
                Log.e(
                    "TOTO",
                    "Error fetching HTML content. Response Code: ${connection.responseCode}"
                )
            }
        } catch (e: IOException) {
            Log.e("TOTO", "Error fetching HTML content: $e")
        }

        return@withContext videoMetadata
    }
}