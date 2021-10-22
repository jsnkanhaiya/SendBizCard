package com.sendbizcard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.R
import com.sendbizcard.base.BaseDialogFragment
import com.sendbizcard.databinding.DialogSelectCameraGalleryBinding
import com.sendbizcard.utils.setWidthPercent

class SelectCameraGalleryDialog : BaseDialogFragment<DialogSelectCameraGalleryBinding>() {

    var callbacks: Callbacks? = null
    private lateinit var binding: DialogSelectCameraGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
        binding = getViewBinding()
        initOnClicks()
    }

    private fun initOnClicks() {
         binding.cameraView.setOnClickListener {
             dismiss()
             callbacks?.onCameraOptionSelected()
         }

         binding.galleryView.setOnClickListener {
             dismiss()
             callbacks?.onGalleryOptionSelected()
         }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSelectCameraGalleryBinding
        get() = DialogSelectCameraGalleryBinding::inflate


    interface Callbacks{
        fun onCameraOptionSelected()
        fun onGalleryOptionSelected()
    }

    companion object {
        fun newInstance() = SelectCameraGalleryDialog()
    }


}