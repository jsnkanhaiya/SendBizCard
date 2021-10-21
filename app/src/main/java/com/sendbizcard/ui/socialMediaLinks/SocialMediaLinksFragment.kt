package com.sendbizcard.ui.socialMediaLinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentEnterSocialMediaLinksBinding
import com.sendbizcard.models.request.addCard.SocialLinksItem
import com.sendbizcard.utils.SocialMediaLinksEnum
import com.sendbizcard.utils.UserSessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialMediaLinksFragment : BaseFragment<FragmentEnterSocialMediaLinksBinding>() {

    private lateinit var binding: FragmentEnterSocialMediaLinksBinding

    private val socialMediaLinksList: ArrayList<SocialLinksItem> by lazy { ArrayList() }

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
                        binding.etFbUsername.text.apply {
                            socialMediaItem.link
                        }
                    }
                    SocialMediaLinksEnum.INSTAGRAM.socialMediaName -> {
                        binding.etInstaUsername.text.apply {
                            socialMediaItem.link
                        }
                    }
                    SocialMediaLinksEnum.TWITTER.socialMediaName -> {
                        binding.etTwitterUsername.text.apply {
                            socialMediaItem.link
                        }
                    }
                    SocialMediaLinksEnum.LINKEDIN.socialMediaName -> {
                        binding.etTwitterUsername.text.apply {
                            socialMediaItem.link
                        }
                    }
                }
            }
        }
    }

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
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnterSocialMediaLinksBinding
        get() = FragmentEnterSocialMediaLinksBinding::inflate
}