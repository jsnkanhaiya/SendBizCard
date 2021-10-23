package com.sendbizcard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.sendbizcard.R
import com.sendbizcard.base.BaseDialogFragment
import com.sendbizcard.databinding.DialogAddServicesBinding
import com.sendbizcard.utils.setWidthPercent

class AddServicesDialog : BaseDialogFragment<DialogAddServicesBinding>() {

    private lateinit var binding: DialogAddServicesBinding
    var onSaveButtonClick: OnSaveButtonClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
        binding = getViewBinding()
        initOnClick()
    }

    private fun initOnClick() {
        binding.btnSave.setOnClickListener {
            val serviceEntered = binding.etEnterService.text.toString()
            if (serviceEntered.isNotEmpty()){
                onSaveButtonClick?.addServicesItem(serviceEntered)
                dismiss()
            }
        }
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAddServicesBinding
        get() = DialogAddServicesBinding::inflate


    companion object {

        fun newInstance() : AddServicesDialog {
            return AddServicesDialog()
        }
    }

    interface OnSaveButtonClick {
        fun addServicesItem(serviceEntered: String)
    }

}