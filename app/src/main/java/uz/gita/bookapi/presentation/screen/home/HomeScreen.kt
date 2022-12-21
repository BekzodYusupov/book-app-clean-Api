package uz.gita.bookapi.presentation.screen.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.remote.dto.response.ChangeFavResponse
import uz.gita.bookapi.databinding.ScreenHomeBinding
import uz.gita.bookapi.presentation.screen.home.adapter.HomeReAdapter
import uz.gita.bookapi.presentation.screen.home.dialog.HomeDialog
import uz.gita.bookapi.presentation.viewModel.HomeViewModelImpl
import uz.gita.bookapi.utils.connectivityManager
import uz.gita.bookapi.utils.mLog

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 15:09
 */

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewBinding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val homeAdapter: HomeReAdapter = HomeReAdapter()
    private val homeDialog: HomeDialog by lazy(mode = LazyThreadSafetyMode.NONE) { HomeDialog(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectivityManager(requireContext(), {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewBinding.internetIndicator.setCardBackgroundColor(resources.getColor(R.color.green, null))
                Toast.makeText(requireContext(), "Yes Internet", Toast.LENGTH_SHORT).show()
                viewModel.getBooks()
            }
        }) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewBinding.internetIndicator.setCardBackgroundColor(resources.getColor(R.color.red, null))
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.container.adapter = homeAdapter

        homeAdapter.triggerBookMarkClickListener {
            viewModel.changeFav(it)
        }

        homeAdapter.triggerEditClickListener { bookResponseEntity ->
            mLog("sending bookResponseEntity to dialog data-> $bookResponseEntity")
            homeDialog.show()
            homeDialog.setPredefinedText?.invoke(bookResponseEntity)
        }

        homeAdapter.triggerDeleteClickListener {
            viewModel.deleteBook(it)
        }

        viewBinding.fabAdd.setOnClickListener {
            homeDialog.show()
        }

        homeDialog.triggerPostBookListener {
            viewModel.postBook(it)
        }

        homeDialog.triggerPutBookListener {
            viewModel.putBook(it)
        }

        viewModel.booksFlow.onEach {
            val stringBuilder = StringBuilder()
            it.forEach {
                stringBuilder.append(it.fav).append(" ")
            }
            mLog(stringBuilder.toString())
            homeAdapter.submitList(ArrayList(it))
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loading.onEach {
            if (it.fullScreen) {
                viewBinding.progress.visibility = View.VISIBLE
                viewBinding.loadingView.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
                viewBinding.loadingView.visibility = View.GONE
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.hasConnection.onEach {
            if (it) Toast.makeText(requireContext(), "Yes Internet", Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.failureFlow.onEach {
            Toast.makeText(requireContext(), "$it - oops", Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlow.onEach {
            when (it) {
                is String -> Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                is ChangeFavResponse -> {
                    mLog("HomeScreen isLke success - ${it.message}")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}


