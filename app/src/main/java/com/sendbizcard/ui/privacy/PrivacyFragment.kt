package com.sendbizcard.ui.privacy

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentThemeBinding
import com.sendbizcard.databinding.FragmentViewCardBinding
import com.sendbizcard.ui.sharecard.ViewCardFragment
import com.sendbizcard.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyFragment: BaseFragment<FragmentViewCardBinding>()  {

    private var m_downX = 0f
    private lateinit var binding: FragmentViewCardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        //dummy Load URL
        loadUrl()
    }

    private fun loadUrl() {
        binding.webView.settings.javaScriptEnabled = true
        initWebView()
        binding.progressBarContainer.visibility = View.VISIBLE
        binding.webView.loadUrl(AppConstants.PRIVACY_POLICY)
    }

    private fun initWebView() {
        binding.webView.webChromeClient = MyWebChromeClient(requireContext())
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBarContainer.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url != null) {
                    binding.webView.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBarContainer.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                binding.progressBarContainer.visibility = View.GONE
            }
        }
        binding.webView.clearCache(true)
        binding.webView.clearHistory()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.isHorizontalScrollBarEnabled = false
        binding.webView.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.pointerCount > 1) {
                //Multi touch detected
                return@OnTouchListener true
            }
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    // save the x
                    m_downX = event.x
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {

                    // set x so that it doesn't move
                    event.setLocation(m_downX, event.y)
                }
            }
            false
        })
    }


    private class MyWebChromeClient(context: Context) : WebChromeClient() {
        var context: Context

        init {
            this.context = context
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentViewCardBinding
        get() = FragmentViewCardBinding::inflate
}