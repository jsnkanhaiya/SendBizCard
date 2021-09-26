package com.sendbizcard.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackBinding

class FeedbackFragment : BaseFragment<FragmentFeedbackBinding>() {

    private var binding: FragmentFeedbackBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedbackBinding
        get() = FragmentFeedbackBinding::inflate
}