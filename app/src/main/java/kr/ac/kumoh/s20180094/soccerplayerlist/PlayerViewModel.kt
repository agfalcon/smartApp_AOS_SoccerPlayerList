package kr.ac.kumoh.s20180094.soccerplayerlist

import android.app.Application
import android.graphics.Bitmap
import android.util.LruCache
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    data class Player(var id: Int, var name: String, var nation: String, var team: String, var image: String)

    companion object{
        const val QUEUE_TAG = "PlayerRequestQueue"
        const val SERVER_URL = "https://expresssongdb-rjhqn.run.goorm.io/"
    }

    private val list = ArrayList<Player>()
    private val _players = MutableLiveData<ArrayList<Player>>()
    val players : LiveData<ArrayList<Player>>
        get() = _players

    private val queue: RequestQueue
    val imageLoader: ImageLoader

    init{
        _players.value = list
        queue = Volley.newRequestQueue(getApplication())
        imageLoader = ImageLoader(
            queue, object: ImageLoader.ImageCache{
                val cache = LruCache<String, Bitmap>(100)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }

            }
        )
    }


    fun requestPlayer(){
        val request = JsonArrayRequest(
            Request.Method.GET,
            "$SERVER_URL/player",
            null,
            {
                list.clear()
                parseJson(it)
                _players.value = list
                Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    fun getImageUrl(i: Int) : String{
        return "$SERVER_URL/image/" + URLEncoder.encode(list[i].image, "utf-8")
    }
    private fun parseJson(items: JSONArray){
        for(i in 0 until items.length()){
            val item = items[i] as JSONObject
            val id = item.getInt("id")
            val name = item.getString("name")
            val nation = item.getString("nation")
            val team = item.getString("team")
            val image = item.getString("image")
            list.add(Player(id,name,nation,team,image))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}