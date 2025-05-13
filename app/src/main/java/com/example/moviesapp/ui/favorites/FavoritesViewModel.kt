package com.example.moviesapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.AppContextHolder
import com.example.moviesapp.data.LocalFavoriteMoviesRepositoryProvider
import com.example.moviesapp.data.local.FavoriteMovieEntity
import com.example.moviesapp.utils.toMovie
import com.example.moviesapp.utils.toEntity
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.moviesapp.model.Movie
import com.example.moviesapp.ui.moviesapp.FavoritesUiState

class FavoritesViewModel : ViewModel() {

    private val localRepo = LocalFavoriteMoviesRepositoryProvider
        .getRepository(AppContextHolder.appContext)

    var uiState by mutableStateOf<FavoritesUiState>(FavoritesUiState.Loading)
        private set

    init { loadFavorites() }

    fun loadFavorites() {
        viewModelScope.launch {
            uiState = FavoritesUiState.Loading
            try {
                val entities: List<FavoriteMovieEntity> = localRepo.getAllFavorites()
                val movies = entities.map { it.toMovie() }
                uiState = FavoritesUiState.Success(movies)
            } catch (_: Exception) {
                uiState = FavoritesUiState.Error
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                localRepo.getFavorite(movie.id)
                    ?.let { localRepo.removeFavorite(it) }
                    ?: localRepo.addFavorite(movie.toEntity())
                loadFavorites()
            } catch (_: Exception) {
                uiState = FavoritesUiState.Error
            }
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                localRepo.getFavorite(movieId)
                    ?.let { localRepo.removeFavorite(it) }
                loadFavorites()
            } catch (_: Exception) {
                uiState = FavoritesUiState.Error
            }
        }
    }
}
