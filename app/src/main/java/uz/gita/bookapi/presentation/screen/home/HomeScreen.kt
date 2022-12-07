package uz.gita.bookapi.presentation.screen.home

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapi.R
import uz.gita.bookapi.databinding.ScreenHomeBinding

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 15:09
 */

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewBinding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
}