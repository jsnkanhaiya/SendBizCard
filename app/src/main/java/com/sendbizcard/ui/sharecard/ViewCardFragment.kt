package com.sendbizcard.ui.sharecard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentViewCardBinding
import dagger.hilt.android.AndroidEntryPoint
import android.view.MotionEvent

import android.graphics.Bitmap
import android.view.View.OnTouchListener
import android.webkit.*
import com.sendbizcard.R
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible


@AndroidEntryPoint
class ViewCardFragment : BaseFragment<FragmentViewCardBinding>(){

    private val viewCardViewModel: ViewCardViewModel by viewModels()
    private lateinit var binding: FragmentViewCardBinding
    var cardId =""
    var themeId = ""
    private var m_downX = 0f
    var redirectUrl = "https://tinyurl.com/ygxh7upb"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        viewCardViewModel.getCardURL(cardId,themeId)
        setupObservers()
        //dummy Load URL
        loadUrl()
    }

    private fun loadUrl() {
        binding.webView.settings.javaScriptEnabled = true
        initWebView()
        showProgressBar()
        binding.webView.loadUrl(redirectUrl)
    }

    private fun setupObservers() {

        viewCardViewModel.viewCardResponse.observe(this) {
            hideProgressBar()
           //load URl

        }

        viewCardViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        viewCardViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }


        viewCardViewModel.showServerError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar(){
        binding.progressBarContainer.gone()
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"", R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"ViewCardFragment")
    }


    private fun initWebView() {
        binding.webView.webChromeClient = MyWebChromeClient(requireContext())
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgressBar()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url != null) {
                    binding.webView.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                hideProgressBar()
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                hideProgressBar()
            }
        }
        binding.webView.clearCache(true)
        binding.webView.clearHistory()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.isHorizontalScrollBarEnabled = false
        binding.webView.setOnTouchListener(OnTouchListener { v, event ->
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