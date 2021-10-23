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
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import android.view.MotionEvent

import android.graphics.Bitmap
import android.view.View.OnTouchListener
import android.webkit.*
import android.widget.Toast


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
        initViews()
        viewCardViewModel.getCardURL(cardId,viewCardViewModel.getThemeId())
        setupObservers()
        //dummy Load URL
       // loadUrl()
    }

    private fun initViews() {

        val bundle = this.arguments
        if (bundle != null) {
            cardId = bundle.getString("id").toString()
        }
    }

    private fun loadUrl(redirectUrl:String) {
        binding.webView.settings.javaScriptEnabled = true
        initWebView()
        binding.progressBarContainer.visibility = View.VISIBLE
        binding.webView.loadUrl(redirectUrl)
    }

    private fun setupObservers() {

        viewCardViewModel.viewCardResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
           //load URl
            loadUrl(it.redirectUrl.toString())
        })

        viewCardViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        viewCardViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })


        viewCardViewModel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }

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
       /* binding.webView.setOnTouchListener(OnTouchListener { v, event ->
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
        })*/
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