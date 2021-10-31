package com.sendbizcard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.R
import com.sendbizcard.base.BaseDialogFragment
import com.sendbizcard.databinding.DialogServerErrorFragmentBinding
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.setWidthPercent

class ServerErrorDialogFragment : BaseDialogFragment<DialogServerErrorFragmentBinding>() {

    var title = ""
    var message = ""
    var callbacks: Callbacks? = null
    private lateinit var binding: DialogServerErrorFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
        binding = getViewBinding()
        initOnClicks()
    }

    private fun initOnClicks(){
        binding.btnOk.setOnClickListener {
            dismiss()
            callbacks?.onOKClicked()
        }
    }

    companion object {
        fun newInstance() = ServerErrorDialogFragment()
    }

    interface Callbacks {
        fun onOKClicked()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogServerErrorFragmentBinding
        get() = DialogServerErrorFragmentBinding::inflate
}