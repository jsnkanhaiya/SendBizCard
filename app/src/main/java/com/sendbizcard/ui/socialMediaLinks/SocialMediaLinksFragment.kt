package com.sendbizcard.ui.socialMediaLinks

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentEnterSocialMediaLinksBinding
import com.sendbizcard.models.request.addCard.SocialLinksItem
import com.sendbizcard.utils.AlertDialogWithImageView
import com.sendbizcard.utils.SocialMediaLinksEnum
import com.sendbizcard.utils.UserSessionManager
import com.sendbizcard.utils.getDefaultNavigationAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialMediaLinksFragment : BaseFragment<FragmentEnterSocialMediaLinksBinding>() {

    private lateinit var binding: FragmentEnterSocialMediaLinksBinding

    private val socialMediaLinksList: ArrayList<SocialLinksItem> by lazy { ArrayList() }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setDataToUI()
        initOnClicks()
    }

    private fun setDataToUI() {
        val list = UserSessionManager.getDataFromSocialMediaList()
        if (list.isNotEmpty()) {
            list.forEach { socialMediaItem ->
                when (socialMediaItem.name) {
                    SocialMediaLinksEnum.FACEBOOK.socialMediaName -> {
                        binding.etFbUsername.setText(socialMediaItem.link)
                    }
                    SocialMediaLinksEnum.INSTAGRAM.socialMediaName -> {
                        binding.etInstaUsername.setText( socialMediaItem.link)

                    }
                    SocialMediaLinksEnum.TWITTER.socialMediaName -> {
                        binding.etTwitterUsername.setText( socialMediaItem.link)
                    }
                    SocialMediaLinksEnum.LINKEDIN.socialMediaName -> {
                        binding.etLinkedInUsername.setText( socialMediaItem.link)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initOnClicks() {
        binding.btnSave.setOnClickListener {
            val fbUserName = binding.etFbUsername.text.toString()
            val instaUserName = binding.etInstaUsername.text.toString()
            val twitterUserName = binding.etTwitterUsername.text.toString()
            val linkedInUserName = binding.etLinkedInUsername.text.toString()
            socialMediaLinksList.clear()

            if (fbUserName.isNotEmpty()) {
                socialMediaLinksList.add(
                    SocialLinksItem(
                        name = SocialMediaLinksEnum.FACEBOOK.socialMediaName,
                        link = fbUserName
                    )
                )
            }

            if (instaUserName.isNotEmpty()) {
                socialMediaLinksList.add(
                    SocialLinksItem(
                        name = SocialMediaLinksEnum.INSTAGRAM.socialMediaName,
                        link = instaUserName
                    )
                )
            }

            if (twitterUserName.isNotEmpty()) {
                socialMediaLinksList.add(
                    SocialLinksItem(
                        name = SocialMediaLinksEnum.TWITTER.socialMediaName,
                        link = twitterUserName
                    )
                )
            }

            if (linkedInUserName.isNotEmpty()) {
                socialMediaLinksList.add(
                    SocialLinksItem(
                        name = SocialMediaLinksEnum.LINKEDIN.socialMediaName,
                        link = linkedInUserName
                    )
                )
            }

            UserSessionManager.addDataInSocialMediaList(socialMediaLinksList)
            showSuccessDialog()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
      // binding.progressBarContainer.visibility = View.GONE
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.saved_successfully),
            R.drawable.ic_success,
            onDismiss = {
                findNavController().popBackStack()
            }
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnterSocialMediaLinksBinding
        get() = FragmentEnterSocialMediaLinksBinding::inflate
}