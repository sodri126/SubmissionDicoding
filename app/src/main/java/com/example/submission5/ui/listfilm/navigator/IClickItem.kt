package com.example.submission5.ui.listfilm.navigator

interface IClickItem {
    fun detailInformation(movie: Any)
    fun favoriteMovie(movie: Any)
    fun unFavoriteMovie(movie: Any)
}