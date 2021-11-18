package com.example.movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import kotlinx.android.synthetic.main.movie_row.view.*

//https://ithelp.ithome.com.tw/articles/10220196 看範例
//https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/743038/
//https://developer.android.com/topic/libraries/view-binding/migration
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersAdapter.kt

class MovieAdapter ( val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val TAG: String? = MovieAdapter::class.java.simpleName

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val poster = itemView.movie_poster
        val title = itemView.movie_title
        var popularity = itemView.movie_popularity
        /*fun bind( movies : List<Movie>)  {
            title.text = movies.get(10).toString()
            popularity.text = movies.get(7).toString()
        }*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        //建立ViewHolder 實例化然後把 View 與ViewHolder 綁定及支援多種 layout 的需求
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_row, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        //依據 position 把data跟 ViewHolder 綁定在一起。
        movies?.run {
            val movie = movies.get(position)
            holder.title.setText(movie.title)
            holder.popularity.setText(movie.popularity.toString())
            holder.poster.load("https://image.tmdb.org/t/p/w500${movie.poster_path}") {
                Log.d(TAG, "MovieAdapter : https://image.tmdb.org/t/p/w500${movie.poster_path}")
                placeholder(R.drawable.picture) //預設圖片
                //transformations(CircleCropTransformation()) //圓形圖片
                //crossfade(true)
                //transformations(RoundedCornersTransformation(0.5F)) //邊角圓型
                transformations(BlurTransformation(holder.poster.context))//模糊
                error(R.drawable.error) //出現讀取錯誤
            }
        }
    }
    override fun getItemCount(): Int {
        //回傳整個 Adapter 包含幾筆資料
        return movies.size?:0
    }
}