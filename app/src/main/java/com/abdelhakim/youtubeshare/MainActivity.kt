package com.abdelhakim.youtubeshare

/*
 * YoutubeShare is a simple Kotlin Android app that allows you to retrieve metadata from YouTube videos.
 * Copyright (c) 2023 ABDELHAKIM KHAOUITI
 * Developer: ABDELHAKIM KHAOUITI
 * GitHub: https://github.com/khaouitiabdelhakim/YoutubeShare
 * Last Modification Date: [24/12/2023]


 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private var searchScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var progressBar:ProgressBar

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.loading)
        progressBar.visibility = View.INVISIBLE

        // Handle the incoming intent
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // Handle the new incoming intent
        handleIntent(intent)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            progressBar.visibility = View.VISIBLE


            // Do something with the shared text (e.g., display it)
            if (sharedText != null) {
                // Process the shared text
                val text = findViewById<TextView>(R.id.text)
                searchScope.launch {
                    try {
                        val metadata = YouTube.findMetaData(sharedText)
                        text.text = metadata.toString()

                        progressBar.visibility = View.INVISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                        text.text = "Error fetching metadata"
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}
