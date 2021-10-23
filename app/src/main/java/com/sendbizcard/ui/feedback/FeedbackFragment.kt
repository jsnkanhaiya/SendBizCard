package com.sendbizcard.ui.feedback

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackBinding
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : BaseFragment<FragmentFeedbackBinding>() {

    private val feedBackViewModel: FeedBackViewModel by viewModels()
    private lateinit var binding: FragmentFeedbackBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = getViewBinding()
        initOnClicks()
        setupObservers()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        feedBackViewModel.feedbackResponse.observe(this) {
            hideProgressBar()
            showSuccessDialog()

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
            binding.tvFeedbackError.text = resources.getString(R.string.enter_feedback)
            val strFeedback = binding.etFeedBack.text.toString()
            if (!strFeedback.isNullOrEmpty()) {
                showProgressBar()
                feedBackViewModel.sendFeedBAck(strFeedback)
            }else{
                binding.tvFeedbackError.visible()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        binding.progressBarContainer.visibility = View.GONE
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.success_title_feedback),
            R.drawable.ic_success,
            onDismiss = {
                if (fragmentManager != null) {
                    val i = Intent(requireContext(), HomeActivity::class.java)
                    requireActivity().startActivity(i)
                    requireActivity().finish()
                }
            }
        )


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