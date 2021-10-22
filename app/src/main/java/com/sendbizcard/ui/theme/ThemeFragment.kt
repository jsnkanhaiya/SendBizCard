package com.sendbizcard.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentThemeBinding
import com.sendbizcard.models.response.theme.ListThemeItem
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThemeFragment : BaseFragment<FragmentThemeBinding>() {


    @Inject
    lateinit var themeAdapter: ThemeAdapter

    private val themeViewModel: ThemeViewModel by viewModels()

    private lateinit var binding: FragmentThemeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        binding.progressBarContainer.visible()
        observeData()
        themeViewModel.getThemeList()

    }

    private fun observeData() {
        themeViewModel.themeList.observe(this) { themeList ->
            setUpAdapter(themeList)
            binding.progressBarContainer.gone()
        }
    }

    private fun setUpAdapter(themeList: List<ListThemeItem>) {
        themeAdapter.addAll(themeList)
        binding.rvSelectTheme.adapter = themeAdapter
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentThemeBinding
        get() = FragmentThemeBinding::inflate

}