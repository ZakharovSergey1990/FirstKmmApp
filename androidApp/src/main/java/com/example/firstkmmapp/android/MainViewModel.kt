package com.example.firstkmmapp.android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmmapp.data.User
import com.example.firstkmmapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var users: List<User> by mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            userRepository.getAllUsers().collect {
                if (it != null) {
                    users = it
                }
            }
        }
    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
        }
    }
}