package com.sendbizcard.ui.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentThemeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThemeFragment : BaseFragment<FragmentThemeBinding>() {


    private val themeViewModel: ThemeViewModel by viewModels()

    private lateinit var binding: FragmentThemeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()

    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentThemeBinding
        get() = FragmentThemeBinding::inflate

}