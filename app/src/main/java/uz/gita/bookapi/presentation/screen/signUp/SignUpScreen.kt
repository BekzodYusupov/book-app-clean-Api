package uz.gita.bookapi.presentation.screen.signUp

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
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.databinding.ScreenSignUpBinding
import uz.gita.bookapi.presentation.viewModel.SignUpViewModelImpl
import uz.gita.bookapi.utils.mLog

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_sign_up) {
    private val viewBinding: ScreenSignUpBinding by viewBinding(ScreenSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels<SignUpViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnSignIn.setOnClickListener { viewModel.openSignIn() }
        viewBinding.btnSignUp.setOnClickListener {
            val signUpRequest = SignUpRequest(
                viewBinding.etPhone.text.toString(), viewBinding.etPassword.text.toString(),
                viewBinding.etLastname.text.toString(), viewBinding.etFirstname.text.toString())
            val rePassword = viewBinding.etRePassword.text.toString()
            mLog("from screenSignUp - ${signUpRequest.password} and $rePassword")
            viewModel.signUp(signUpRequest, rePassword)
        }

        viewModel.successFlow.onEach {
            mLog("successFlow open verifySignUpScreen should open verify")
            viewModel.openVerify()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loading.onEach {
            progressVisibilityChanger(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.failureFlow.onEach {
            mLog("fail - $it")
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.hasConnection.onEach {
            mLog("has Connection - $it")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.isValidFlow.onEach {
            mLog("isValid input $it")
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun progressVisibilityChanger(loading: Boolean) {
        if (loading) {
            viewBinding.apply { progress.visibility = View.VISIBLE; loadingView.visibility = View.VISIBLE }
            viewLifecycleOwner.lifecycleScope.launch {
                delay(5000)
                viewBinding.apply {
                    progress.visibility = View.GONE; loadingView.visibility = View.GONE
                    Toast.makeText(
                        requireContext(), "Your internet connection is too low. Please try again", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else viewBinding.apply { progress.visibility = View.GONE; loadingView.visibility = View.GONE }
    }
}