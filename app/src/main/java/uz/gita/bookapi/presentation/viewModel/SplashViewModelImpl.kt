package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.local.shared.SharedPref
import uz.gita.bookapi.navigation.Navigator
import uz.gita.bookapi.presentation.screen.splash.SplashScreenDirections
import uz.gita.bookapi.presentation.screen.splash.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val navigator: Navigator,
    private val shP: SharedPref
) : SplashViewModel, ViewModel() {

    override fun openSignInOrHome() {
        viewModelScope.launch {
            delay(1500)
            if (shP.signedIn) {
                shP.signedIn = false
                navigator.navigateTo(SplashScreenDirections.actionSplashScreenToSignInScreen())
            } else {
                navigator.navigateTo(SplashScreenDirections.actionSplashScreenToBaseScreen())
            }

        }
    }
}