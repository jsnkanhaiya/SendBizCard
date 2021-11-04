package com.sendbizcard.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentHomeV2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragmentV2  : BaseFragment<FragmentHomeV2Binding>(){

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeV2Binding
        get() = FragmentHomeV2Binding::inflate
}