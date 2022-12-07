package uz.gita.bookapi.presentation.screen.signIn

import android.os.Bundle
import android.util.Log
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
import uz.gita.bookapi.R
import uz.gita.bookapi.databinding.ScreenSignInBinding
import uz.gita.bookapi.presentation.viewModel.SignInViewModelImpl
import uz.gita.bookapi.utils.connectivityManager
import uz.gita.bookapi.utils.mLog

@AndroidEntryPoint
class SignInScreen : Fragment(R.layout.screen_sign_in) {
    private val viewModel: SignInViewModel by viewModels<SignInViewModelImpl>()
    private val viewBinding: ScreenSignInBinding by viewBinding(ScreenSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectivityManager(requireContext()){
            Toast.makeText(requireContext(), "Connected!", Toast.LENGTH_SHORT).show()
        }

        viewBinding.btnSignUp.setOnClickListener { viewModel.openRegister() }
        viewBinding.btnSignIn.setOnClickListener {
            val phone: String = viewBinding.etPhone.text.toString()
            val password: String = viewBinding.etPassword.text.toString()
            Log.d("zzz", "screen submit clicked $phone ,$password")
            viewModel.signIn(password, phone)
//            viewModel.signIn("123ASD", "+998915914308")
            mLog(viewBinding.etPhone.rawText)
        }

        viewModel.loading.onEach {
            Log.d("zzz", "loading = $it")
            if (it) {
                viewBinding.progress.visibility = View.VISIBLE
                viewBinding.loadingView.visibility = View.VISIBLE
                delay(5000)
                viewBinding.progress.hide()
                viewBinding.loadingView.visibility = View.GONE
            } else {
                viewBinding.progress.visibility = View.GONE
                viewBinding.loadingView.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.hasConnection.onEach {
            Log.d("zzz", "Screen_SignIn:hasConnection = $it")
            if (it) Toast.makeText(requireContext(), "Yes Internet", Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.failureFlow.onEach {
            Log.d("zzz", "failure = $it")
            Toast.makeText(requireContext(), "$it - oops", Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlow.onEach {
            Log.d("zzz", "success = $it")
            viewModel.openHome()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}