package kr.ac.kumoh.s20180094.soccerplayerlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    data class Player(var id: Int, var name: String, var nation: String, var team: String, var image: String)

    companion object{
        const val SERVER_URL = "https://expresssongdb-rjhqn.run.goorm.io/"
    }

    private val list = ArrayList<Player>()
    private val _players = MutableLiveData<ArrayList<Player>>()
    val players : LiveData<ArrayList<Player>>
        get() = _players

    private val queue: RequestQueue

    init{
        _players.value = list
        queue = Volley.newRequestQueue(getApplication())
    }

    fun requestPlayer(){
        val request =
    }
}