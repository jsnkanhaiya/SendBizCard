package com.sendbizcard.ui.feedback

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sendbizcard.HomeActivity
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackBinding
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : BaseFragment<FragmentFeedbackBinding>() {

    private val feedBackViewModel: FeedBackViewModel by viewModels()
    private lateinit var binding: FragmentFeedbackBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = getViewBinding()
        initOnClicks()
        setupObservers()
    }

    private fun setupObservers() {
        feedBackViewModel.feedbackResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
        })

        feedBackViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        feedBackViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })


        feedBackViewModel.showServerError.observe(this) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error", errorMessage)
        }
    }

    private fun initOnClicks() {
        binding.btnSave.setOnClickListener {
            var strFeedback = binding.etFeedBack.text.toString()
            if (feedBackViewModel.isValidFeedbackData(strFeedback)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                feedBackViewModel.sendFeedBAck(strFeedback)
            }
        }
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedbackBinding
        get() = FragmentFeedbackBinding::inflate
}