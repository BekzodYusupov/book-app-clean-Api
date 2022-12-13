package uz.gita.bookapi.presentation.screen.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponse
import uz.gita.bookapi.data.source.remote.dto.response.ChangeFavResponse
import uz.gita.bookapi.databinding.ScreenHomeBinding
import uz.gita.bookapi.presentation.screen.home.dialog.HomeDialog
import uz.gita.bookapi.presentation.viewModel.HomeViewModelImpl
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
    private val homeAdapter: HomeAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { HomeAdapter() }
    private val homeDialog: HomeDialog by lazy(mode = LazyThreadSafetyMode.NONE) { HomeDialog(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.container.adapter = homeAdapter

        homeAdapter.triggerBookMarkClickListener { id ->
            val changeFavRequest = ChangeFavRequest(bookId = id)
            viewModel.changeFav(changeFavRequest)
        }

        homeAdapter.triggerEditClickListener { booksResponseItem ->
            mLog("sending booksResponseItem to dialog data-> $booksResponseItem")
            homeDialog.setPredefinedText?.invoke(booksResponseItem)
            homeDialog.show()
        }

        homeAdapter.triggerDeleteClickListener {
            val deleteRequest = DeleteRequest(it.toString())
            viewModel.deleteBook(deleteRequest)
        }

        viewBinding.fabAdd.setOnClickListener{
            homeDialog.show()
        }

        homeDialog.triggerPostBookListener {
            viewModel.postBook(it)
        }

        homeDialog.triggerPutBookListener {
            viewModel.putBook(it)
        }

        viewModel.loading.onEach {
            mLog("ScreenHome loading - ${it.bookItem}")
            homeAdapter.progressListener?.invoke(it.bookItem)

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
                is BooksResponse -> {
                    homeAdapter.submitList(it)
                }
                is ChangeFavResponse -> {
                    mLog("HomeScreen isLke success - ${it.message}")
                    homeAdapter.changeFav?.invoke(true)
                    viewModel.getBooks()
                }
                else -> viewModel.getBooks()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
}