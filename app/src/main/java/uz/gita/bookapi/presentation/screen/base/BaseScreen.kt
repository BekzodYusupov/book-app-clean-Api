package uz.gita.bookapi.presentation.screen.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapi.R
import uz.gita.bookapi.databinding.ScreenBaseBinding

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 15:07
 */

@AndroidEntryPoint
class BaseScreen : Fragment(R.layout.screen_base) {
    private val pagerAdapter: PagerAdapter by lazy(mode = LazyThreadSafetyMode.NONE) { PagerAdapter(requireActivity()) }
    private val viewBinding: ScreenBaseBinding by viewBinding(ScreenBaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.pager.adapter = pagerAdapter

        viewBinding.bottomNav.setOnItemSelectedListener {
            viewBinding.pager.currentItem = when (it.itemId) {
                R.id.home -> 0
                else -> 1
            };true
        }
    }
}