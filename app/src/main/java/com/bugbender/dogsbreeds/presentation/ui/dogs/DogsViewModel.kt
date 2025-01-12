package com.bugbender.dogsbreeds.presentation.ui.dogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.dogsbreeds.data.Dog
import com.bugbender.dogsbreeds.data.DogLoadResult
import com.bugbender.dogsbreeds.data.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel() {

    private val _dogState = MutableLiveData<DogState>()
    val dogState: LiveData<DogState> = _dogState

    fun fetchDog() {
        viewModelScope.launch {
            _dogState.value = DogState.Loading

            when (val result = dogRepository.fetchDog()) {
                is DogLoadResult.Success -> {
                    _dogState.value = DogState.Success(result.dog)
                }

                is DogLoadResult.Error -> {
                    _dogState.value = DogState.Error(result.message)
                }
            }

        }
    }

    sealed class DogState {
        object FirstLoading: DogState()
        object Loading : DogState()
        data class Success(val dog: Dog) : DogState()
        data class Error(val message: String) : DogState()
    }
}