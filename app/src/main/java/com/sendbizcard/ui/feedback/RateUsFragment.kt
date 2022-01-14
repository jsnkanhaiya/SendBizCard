package com.sendbizcard.ui.feedback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.HomeActivity
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentFeedbackThankYouBinding
import com.sendbizcard.databinding.FragmentRateUsBinding
import android.widget.Toast

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import androidx.activity.OnBackPressedCallback


class RateUsFragment : BaseFragment<FragmentRateUsBinding>() {


    private lateinit var binding: FragmentRateUsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initOnClicks()
    }

    private fun initOnClicks() {

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val i = Intent(requireContext(), HomeActivity::class.java)
                    requireActivity().startActivity(i)
                    requireActivity().finish()
                }
            })



        binding.rateGoogleAppImage.setOnClickListener {
            launchMarket()
        }

        binding.tvRateUs.setOnClickListener {
            launchMarket()
        }

        binding.shareGoogleAppImage.setOnClickListener {
            shareApp(requireContext(),"")
        }

        binding.tvlater.setOnClickListener {
            val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
            requireActivity().finish()
        }
    }

    private fun launchMarket() {
        val uri: Uri = Uri.parse("market://details?id=" + requireActivity().getPackageName())
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), " unable to find market app", Toast.LENGTH_LONG).show()
        }
    }


    fun shareApp(context: Context, text:String ){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            text
        )
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRateUsBinding
        get() = FragmentRateUsBinding::inflate
}