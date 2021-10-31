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
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.dialog.ServerErrorDialogFragment
import com.sendbizcard.models.ServerError
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
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

        feedBackViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        feedBackViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }


        feedBackViewModel.showServerError.observe(this) { serverError ->
            if (serverError.code == 401){
                showServerErrorMessage()
            } else {
                showErrorMessage(serverError.errorMessage)
            }
        }
    }

    private fun showServerErrorMessage() {
        hideProgressBar()
        val fragment = ServerErrorDialogFragment.newInstance()
        fragment.callbacks = object : ServerErrorDialogFragment.Callbacks {
            override fun onOKClicked() {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        fragment.show(parentFragmentManager, "CardListFragment")
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"", R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"CardListFragment")
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