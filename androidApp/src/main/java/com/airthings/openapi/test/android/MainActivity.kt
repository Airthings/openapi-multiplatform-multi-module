package com.airthings.openapi.test.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airthings.openapi.test.Greeter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                val viewModel: GreetingViewModel by viewModels()
                GreetingScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun GreetingScreen(viewModel: GreetingViewModel) {
    GreetingScreen(
        screenState = viewModel.state.value,
        onReloadClicked = viewModel::loadGreeting,
    )
}

@Composable
fun GreetingScreen(
    screenState: ScreenState,
    onReloadClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = when (screenState) {
                ScreenState.Loading -> "Loading.. ⏱"
                is ScreenState.Loaded -> screenState.greeting
                else -> "Something went wrong 💩"
            },
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onReloadClicked) {
            Text(text = "Reload")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GreetingScreen_Preview() {
    GreetingScreen(screenState = ScreenState.Loading, onReloadClicked = {})
}

class GreetingViewModel : ViewModel() {

    private val _state: MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)
    val state: State<ScreenState> = _state

    private val greeter = Greeter()

    init {
        loadGreeting()
    }

    fun loadGreeting() {
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            _state.value = try {
                ScreenState.Loaded(greeting = greeter.greetPet())
            } catch (throwable: Throwable) {
                ScreenState.Error
            }
        }
    }
}

sealed class ScreenState {
    object Loading : ScreenState()
    data class Loaded(val greeting: String) : ScreenState()
    object Error : ScreenState()
}