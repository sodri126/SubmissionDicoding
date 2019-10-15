package com.example.submission5.ui.listfilm.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.submission5.BuildConfig
import com.example.submission5.R
import com.example.submission5.data.api.model.DiscoverMovie
import com.example.submission5.data.api.model.DiscoverTv
import com.example.submission5.data.local.entity.MovieEntity
import com.example.submission5.data.local.entity.TvShowEntity
import com.example.submission5.ui.listfilm.navigator.IClickItem
import com.example.submission5.utils.glideLoadImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_detail.view.txt_release_date
import kotlinx.android.synthetic.main.activity_detail.view.txt_title_movie
import kotlinx.android.synthetic.main.item_list_movies.view.*
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter<T: Any>(private val clickItem: IClickItem) : RecyclerView.Adapter<MovieHolder>() {
    lateinit var movies: ArrayList<T>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_movies,
                parent,
                false
            ), clickItem
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        return when (movies[position]) {
            is DiscoverMovie -> {
               holder.bind(movies[position] as DiscoverMovie)
            }
            is DiscoverTv -> {
                holder.bind(movies[position] as DiscoverTv)
            }
            is MovieEntity -> {
                holder.bind(movies[position] as MovieEntity) {
                    movies.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
            is TvShowEntity -> {
                holder.bind(movies[position] as TvShowEntity) {
                    movies.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
            else -> {
                holder.bind(movies[position] as DiscoverMovie)
            }
        }
    }

}

class MovieHolder(view: View, private val onClickItem: IClickItem) : RecyclerView.ViewHolder(view) {
    private val imgMovie: ImageView = view.img_movie
    private val txtTitleMovie: TextView = view.txt_title_movie
    private val txtReleaseDate: TextView = view.txt_release_date
    private val txtContent: TextView = view.txt_content
    private val containerItemMovie: ConstraintLayout = view.container_item_movie
    private val txtRating: TextView = view.txt_rating
    private val progressbarImg: ProgressBar = view.progress_bar_img
    private val fabFavorite: FloatingActionButton = view.fab_favorite

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("MMMM dd, yyyy")

    fun bind(movie: DiscoverMovie) {
        imgMovie.glideLoadImage(
            "${BuildConfig.PATH_IMG}/w780${movie.posterPath}",
            780,
            500,
            progressbarImg
        )
        txtTitleMovie.text = if (movie.title.length >= 24) "${movie.title.substring(
            0,
            24
        )}..." else movie.title
        txtReleaseDate.text = if (movie.releaseDate != null) sdf.format(movie.releaseDate) else sdf.format(
            Date(0)
        )
        txtContent.text = if (movie.overview.length > 130) "${movie.overview.substring(
            0,
            movie.overview.substring(0, 130).lastIndexOf(' ')
        )}..." else movie.overview
        txtRating.text = movie.voteAverage.toString()
        if (movie.isFavorite) {
            fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
            fabFavorite.tag = R.drawable.ic_favorite_white_24dp
        } else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
            fabFavorite.tag = R.drawable.ic_favorite_border_white_24dp
        }

        fabFavorite.setOnClickListener {
            if (it.tag == R.drawable.ic_favorite_white_24dp) {
                this.onClickItem.unFavoriteMovie(movie)
                fabFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
                movie.isFavorite = false
            } else {
                this.onClickItem.favoriteMovie(movie)
                fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                movie.isFavorite = true
            }
            Log.d("TestFavorite", movie.isFavorite.toString())
        }
        containerItemMovie.setOnClickListener {
            this.onClickItem.detailInformation(movie)
        }
    }

    fun bind(movie: DiscoverTv) {
        imgMovie.glideLoadImage(
            "${BuildConfig.PATH_IMG}/w780${movie.posterPath}",
            780,
            500,
            progressbarImg
        )
        txtTitleMovie.text =
            if (movie.name.length >= 24) "${movie.name.substring(0, 24)}..." else movie.name
        txtReleaseDate.text = if (movie.firstAirDate != null) sdf.format(movie.firstAirDate) else sdf.format(Date(0))
        txtContent.text = if (movie.overview.length > 130) "${movie.overview.substring(
            0,
            movie.overview.substring(0, 130).lastIndexOf(' ')
        )}..." else movie.overview
        txtRating.text = movie.voteAverage.toString()
        if (movie.isFavorite) {
            fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
            fabFavorite.tag = R.drawable.ic_favorite_white_24dp
        } else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
            fabFavorite.tag = R.drawable.ic_favorite_border_white_24dp
        }
        fabFavorite.setOnClickListener {
            if (it.tag == R.drawable.ic_favorite_white_24dp) {
                this.onClickItem.unFavoriteMovie(movie)
                fabFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
                movie.isFavorite = false
            } else {
                this.onClickItem.favoriteMovie(movie)
                fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                movie.isFavorite = true
            }
        }
        containerItemMovie.setOnClickListener {
            this.onClickItem.detailInformation(movie)
        }
    }

    fun bind(movie: MovieEntity, deleteItem: () -> Unit) {
        imgMovie.glideLoadImage(
            "${BuildConfig.PATH_IMG}/w780${movie.movieImagePath}",
            780,
            500,
            progressbarImg
        )
        txtTitleMovie.text =
            if (movie.movieTitle.length >= 24) "${movie.movieTitle.substring(
                0,
                24
            )}..." else movie.movieTitle
        txtReleaseDate.text = movie.movieDate
        txtContent.text =
            if (movie.movieOverview.length > 130) "${movie.movieOverview.substring(
                0,
                movie.movieOverview.substring(0, 130).lastIndexOf(' ')
            )}..." else movie.movieOverview
        txtRating.text = movie.movieRate.toString()
        fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
        fabFavorite.tag = R.drawable.ic_favorite_white_24dp
        fabFavorite.setOnClickListener {
            this.onClickItem.unFavoriteMovie(movie)
            movie.isFavorite = false
            deleteItem()
        }
        containerItemMovie.setOnClickListener {
            this.onClickItem.detailInformation(movie)
        }
    }

    fun bind(movie: TvShowEntity, deleteItem: () -> Unit) {
        imgMovie.glideLoadImage(
            "${BuildConfig.PATH_IMG}/w780${movie.tvShowImagePath}",
            780,
            500,
            progressbarImg
        )
        txtTitleMovie.text =
            if (movie.tvShowTitle.length >= 24) "${movie.tvShowTitle.substring(
                0,
                24
            )}..." else movie.tvShowTitle
        txtReleaseDate.text = movie.tvShowDate
        txtContent.text =
            if (movie.tvShowOverview.length > 130) "${movie.tvShowOverview.substring(
                0,
                movie.tvShowOverview.substring(0, 130).lastIndexOf(' ')
            )}..." else movie.tvShowOverview
        txtRating.text = movie.tvShowRate.toString()
        fabFavorite.setImageResource(R.drawable.ic_favorite_white_24dp)
        fabFavorite.tag = R.drawable.ic_favorite_white_24dp
        fabFavorite.setOnClickListener {
            this.onClickItem.unFavoriteMovie(movie)
            movie.isFavorite = false
            deleteItem()
        }

        containerItemMovie.setOnClickListener {
            this.onClickItem.detailInformation(movie)
        }
    }
}