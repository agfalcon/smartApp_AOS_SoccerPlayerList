package kr.ac.kumoh.s20180094.soccerplayerlist

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.collection.LruCache
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import kr.ac.kumoh.s20180094.soccerplayerlist.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    companion object {
        const val KEY_NAME = "PlayerName"
        const val KEY_NATION = "PlayerNation"
        const val KEY_TEAM = "PlayerTeam"
        const val KEY_IMAGE = "PlayerImage"
    }

    private lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        imageLoader = ImageLoader(
            Volley.newRequestQueue(this),
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(100)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })

        binding.imagePlayer.setImageUrl(intent.getStringExtra(KEY_IMAGE), imageLoader)
        binding.textPlayerName.text = intent.getStringExtra(KEY_NAME)
        binding.textPlayerTeam.text = intent.getStringExtra(KEY_TEAM)
        binding.textPlayerNation.text = intent.getStringExtra(KEY_NATION)
    }
}