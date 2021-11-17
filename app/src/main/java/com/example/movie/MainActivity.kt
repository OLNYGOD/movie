package com.example.movie

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieadapter: MovieAdapter
    val TAG: String = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //recycler setting
        recycler.setHasFixedSize(true)   //設定固定大小
        recycler.layoutManager = LinearLayoutManager(this) //條列式版面

        //處理異步執行緒
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                movieDate()
            }
        }


        //處理異步執行緒
        /*CoroutineScope(Dispatchers.IO).launch {
            val data = URL("https://api.themoviedb.org/3/movie/popular?api_key=6ef5980812b608cffc47741728e4a1ff&language=zh-TW&page=1")
                .readText()
            Log.d(TAG, "MainActivity ${data}")
            val movieResult = Gson().fromJson(data, MovieData::class.java)
            Log.d(TAG, "onCreate : "+ movieResult.page)
            movieResult.results.forEach {
                Log.d(TAG, "onCreate : "+ it.title)
            }
            movieadapter = MovieAdapter(movieResult.results)  //將movies資料放進adapter中
            runOnUiThread {
                recycler.adapter = movieadapter            //將adapter放進recycler
                movieadapter.notifyDataSetChanged()        //重新畫圖
            }
        }
        CoroutineScope(Dispatchers.IO).cancel()
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }

        private suspend fun movieDate() {
            val data =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=6ef5980812b608cffc47741728e4a1ff&language=zh-TW&page=1")
                    .readText()
            Log.d(TAG, "MainActivity ${data}")
            val movieResult = Gson().fromJson(data, MovieData::class.java)
            Log.d(TAG, "onCreate : " + movieResult)
            movieResult.results.forEach {
                Log.d(TAG, "onCreate ${it.title}")
            }
            movieadapter = MovieAdapter(movieResult.results)  //將movies資料放進adapter中
            runOnUiThread {
                recycler.adapter = movieadapter            //將adapter放進recycler
                movieadapter.notifyDataSetChanged()        //重新畫圖
            }
        }
}