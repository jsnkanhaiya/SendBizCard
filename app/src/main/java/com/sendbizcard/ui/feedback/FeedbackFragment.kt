package com.sendbizcard.ui.feedback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sendbizcard.HomeActivity
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackBinding
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.showErrorDialog
import com.sendbizcard.utils.visible
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
        feedBackViewModel.feedbackResponse.observe(this) {
            hideProgressBar()
            val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
        }

        feedBackViewModel.showNetworkError.observe(this) {
            hideProgressBar()
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        }

        feedBackViewModel.showUnknownError.observe(this) {
            hideProgressBar()
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        }


        feedBackViewModel.showServerError.observe(this) { errorMessage ->
            hideProgressBar()

        }
    }

    private fun initOnClicks() {
        binding.btnSave.setOnClickListener {
            val strFeedback = binding.etFeedBack.text.toString()
            if (feedBackViewModel.isValidFeedbackData(strFeedback)) {
                showProgressBar()
                feedBackViewModel.sendFeedBAck(strFeedback)
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedbackBinding
        get() = FragmentFeedbackBinding::inflate
}