package uz.gita.bookapi.presentation.screen.signInVerify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.databinding.ScreenSignInVerifyBinding
import uz.gita.bookapi.presentation.viewModel.SignInVerifyViewModelImpl

@AndroidEntryPoint
class SignInVerifyScreen : Fragment(R.layout.screen_sign_in_verify) {
    private val viewBinding: ScreenSignInVerifyBinding by viewBinding(ScreenSignInVerifyBinding::bind)
    private val viewModel: SignInVerifyViewModel by viewModels<SignInVerifyViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            var timer = 59
            var zero = ""
            (1..59).forEach { _ ->
                zero = if (timer < 10) "0" else ""
                delay(1000)
                timer--
                viewBinding.timerTextview.text = "0:$zero$timer"
            }
        }

        viewBinding.veryfyButton.setOnClickListener {
            val smsCode = viewBinding.smsCodeView.enteredCode
            if (smsCode.length == 6) {
                viewModel.signInVerify(VerifyRequest(smsCode))
            } else Toast.makeText(requireContext(), "Wrong input", Toast.LENGTH_SHORT).show()
        }

        viewModel.successFlow.onEach {
            viewModel.openHomeScreen()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

}