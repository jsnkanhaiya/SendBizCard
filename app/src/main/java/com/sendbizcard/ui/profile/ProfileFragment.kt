package com.sendbizcard.ui.profile

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentProfileBinding
import com.sendbizcard.models.response.UserProfileResponse
import com.sendbizcard.utils.AlertDialogWithImageView
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(){

    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = getViewBinding()
        binding.progressBarContainer.visibility = View.VISIBLE
        profileViewModel.getUserProfileData()
        initOnClicks()
        setupObservers()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        profileViewModel.userProfileResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            setUserData(it)
        })

        profileViewModel.updateUserProfileResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            showSuccessDialog()
        })

        profileViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        profileViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        profileViewModel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }

    }

    private fun initOnClicks() {

        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgot_password, bundleOf("isChangepassword" to true),
                getDefaultNavigationAnimation())
        }

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()
            val designation = binding.etDesignation.text.toString()
            val email = binding.etEmail.text.toString()
            val mobile = binding.etMobileNumber.text.toString()
            val website = binding.etWebsite.text.toString()

            if (profileViewModel.isValidUserProfileData(name,mobile,email,website,designation)){
                binding.progressBarContainer.visibility = View.VISIBLE
                profileViewModel.updateUserData(name,mobile,email,website,designation)
            }

        }


        binding.imgEdit.setOnClickListener {
            binding.etEmail.isEnabled =true
            binding.etDesignation.isEnabled =true
            binding.etName.isEnabled =true
            binding.etMobileNumber.isEnabled =true
            binding.etWebsite.isEnabled =true
        }

    }

    private fun setUserData(userProfileResponse: UserProfileResponse) {
         binding.etName.setText(userProfileResponse.user?.name)
         binding.etMobileNumber.setText(userProfileResponse.user?.contact)
         binding.etWebsite.setText(userProfileResponse.user?.website)
         binding.etDesignation.setText(userProfileResponse.user?.designation)
         binding.etEmail.setText(userProfileResponse.user?.email)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        binding.progressBarContainer.visibility = View.GONE
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.update_profile_successfully),
            R.drawable.ic_success_tick,
            onDismiss = {
                if (fragmentManager != null) {
                    findNavController().navigate(R.id.nav_home, null, getDefaultNavigationAnimation())
                }
            }
        )


    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
}