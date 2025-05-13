package com.example.moviesapp.ui.moviesapp

import com.example.moviesapp.model.Movie

sealed interface FavoritesUiState {
    object Loading : FavoritesUiState
    data class Success(val favorites: List<Movie>) : FavoritesUiState
    object Error : FavoritesUiState
}
