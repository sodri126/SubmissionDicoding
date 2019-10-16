package com.example.moviecatalogfavorite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogfavorite.R
import com.example.moviecatalogfavorite.data.model.FavoriteMovie
import com.example.moviecatalogfavorite.utils.glideLoadImage
import kotlinx.android.synthetic.main.item_list_movies.view.*
import java.util.*

class MainAdapter<T> : RecyclerView.Adapter<MainHolder>(){
    lateinit var movies: ArrayList<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movies, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        return when(movies[position]) {
            is FavoriteMovie -> {
                holder.bind(movies[position] as FavoriteMovie)
            }
            else -> {

            }
        }
    }
}

class MainHolder(view: View): RecyclerView.ViewHolder(view) {
    private val imgMovie: ImageView = view.img_movie
    private val txtTitleMovie: TextView = view.txt_title_movie
    private val txtReleaseDate: TextView = view.txt_release_date
    private val txtContent: TextView = view.txt_content
    private val txtRating: TextView = view.txt_rating
    private val progressbarImg: ProgressBar = view.progress_bar_img

    fun bind(movie: FavoriteMovie) {
        imgMovie.glideLoadImage(
            "https://image.tmdb.org/t/p/w780${movie.movieImagePath}",
            780,
            500,
            progressbarImg
        )
        txtTitleMovie.text = if (movie.movieTitle.length >= 24) "${movie.movieTitle.substring(
            0,
            24
        )}..." else movie.movieTitle
        txtReleaseDate.text = movie.movieDate

        txtContent.text = if (movie.movieOverview.length > 130) "${movie.movieOverview.substring(
            0,
            movie.movieOverview.substring(0, 130).lastIndexOf(' ')
        )}..." else movie.movieOverview
        txtRating.text = movie.movieRate.toString()
    }
}