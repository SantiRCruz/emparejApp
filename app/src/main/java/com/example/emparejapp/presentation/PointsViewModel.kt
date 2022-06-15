package com.example.emparejapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.emparejapp.core.Results
import com.example.emparejapp.data.local.PlayerDao
import com.example.emparejapp.data.models.PlayerEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PointsViewModel(private val dao : PlayerDao):ViewModel() {
    fun fetchPlayers(level:Int):StateFlow<Results<List<PlayerEntity>>> = flow {
        kotlin.runCatching {
            dao.getFirstPlayers(level)
        }.onSuccess {
            emit(Results.Success(it))
        }.onFailure {
            emit(Results.Failure(Exception(it.message)))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),Results.Loading())

    fun savePlayer(playerEntity: PlayerEntity):StateFlow<Results<Long>> = flow {
        kotlin.runCatching {
            dao.savePlayer(playerEntity)
        }.onSuccess {
            emit(Results.Success(it))
        }.onFailure {
            emit(Results.Failure(Exception(it.message)))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),Results.Loading())
}
class PointsViewModelFactory(private val dao : PlayerDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PlayerDao::class.java).newInstance(dao)
    }
}