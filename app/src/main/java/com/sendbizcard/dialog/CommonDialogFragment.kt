package com.sendbizcard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.R
import com.sendbizcard.base.BaseDialogFragment
import com.sendbizcard.databinding.DialogCommonFragmentBinding
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.setWidthPercent

class CommonDialogFragment : BaseDialogFragment<DialogCommonFragmentBinding>() {

    var title = ""
    var message = ""
    var buttonText = ""
    var icon = R.drawable.ic_icon_error
    var callbacks: CommonDialogFragment.Callbacks? = null


    private lateinit var binding: DialogCommonFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        arguments?.let { args ->
            title = args.getString(TITLE,"") ?: ""
            message = args.getString(MESSAGE,"") ?: ""
            buttonText = args.getString(BUTTON_TEXT,"") ?: ""
            icon = args.getInt(ICON,R.drawable.ic_icon_error)
        }
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
        binding = getViewBinding()
        initOnClicks()
        setDataToUI()
    }

    private fun setDataToUI() {

        binding.imgIcon.setImageResource(icon)

        if (title.isNotEmpty()){
            binding.tvTitle.text = title
        } else {
            binding.tvTitle.gone()
        }

        if (message.isNotEmpty()){
            binding.tvMessage.text = message
        } else {
            binding.tvMessage.gone()
        }

        if (buttonText.isNotEmpty()){
            binding.btnConfirm.text = buttonText
        } else {
            binding.btnConfirm.gone()
        }
    }

    private fun initOnClicks(){
        binding.btnCancel.setOnClickListener {
            dismiss()
            callbacks?.onDismissClicked()
        }
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogCommonFragmentBinding
        get() = DialogCommonFragmentBinding::inflate


    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val BUTTON_TEXT = "button_text"
        private const val ICON = "icon"

        fun newInstance(title: String, message: String, buttonText: String,icon: Int) : CommonDialogFragment {
            val commonDialogFragment = CommonDialogFragment()
            val bundle = Bundle()
            bundle.putString(TITLE,title)
            bundle.putString(MESSAGE,message)
            bundle.putString(BUTTON_TEXT,buttonText)
            bundle.putInt(ICON,icon)
            commonDialogFragment.arguments = bundle
            return commonDialogFragment
        }
    }

    interface Callbacks {
        fun onDismissClicked()
    }
}