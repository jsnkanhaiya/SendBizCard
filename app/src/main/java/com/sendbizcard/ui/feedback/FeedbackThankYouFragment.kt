package com.sendbizcard.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackThankYouBinding

class FeedbackThankYouFragment : BaseFragment<FragmentFeedbackThankYouBinding>() {

    private lateinit var binding: FragmentFeedbackThankYouBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedbackThankYouBinding
        get() = FragmentFeedbackThankYouBinding::inflate
}