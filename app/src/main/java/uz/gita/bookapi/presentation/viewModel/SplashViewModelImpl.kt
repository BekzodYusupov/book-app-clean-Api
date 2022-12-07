package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bookapi.navigation.Navigator
import uz.gita.bookapi.presentation.screen.splash.SplashScreenDirections
import uz.gita.bookapi.presentation.screen.splash.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val navigator: Navigator
) : SplashViewModel, ViewModel() {

    override fun openSignIn() {
        viewModelScope.launch {
            delay(1500)
            navigator.navigateTo(SplashScreenDirections.actionSplashScreenToSignInScreen())
        }
    }
}