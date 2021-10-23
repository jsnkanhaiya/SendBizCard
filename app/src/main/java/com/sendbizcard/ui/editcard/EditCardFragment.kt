package com.sendbizcard.ui.editcard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentEditCardBinding
import com.sendbizcard.databinding.FragmentHomeBinding
import com.sendbizcard.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditCardFragment : BaseFragment<FragmentEditCardBinding>(){


    private val editCardViewModel: EditCardViewModel by viewModels()
    private lateinit var binding: FragmentEditCardBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()

        initOnClicks()
        observeData()
    }

    private fun observeData() {

    }

    private fun initOnClicks() {

    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditCardBinding
        get() = FragmentEditCardBinding::inflate
}