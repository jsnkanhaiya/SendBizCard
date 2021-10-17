package com.sendbizcard.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sendbizcard.R
import com.sendbizcard.databinding.DialogPermissionNeededBinding

class PermissionNeededDialog : DialogFragment() {

    private lateinit var binding: DialogPermissionNeededBinding
    var onButtonClickListener: OnButtonClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(
            requireContext(),
            R.style.DialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogPermissionNeededBinding.inflate(LayoutInflater.from(context))
        isCancelable = true
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.tvPermissionNeededAcknowledge.setOnClickDelayListener {
            onButtonClickListener?.okClicked()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    interface OnButtonClickListener {
        fun okClicked()
    }

    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener
    }


    class Builder {

        fun build(): PermissionNeededDialog {
            return PermissionNeededDialog()
        }
    }

    companion object {
        val TAG = PermissionNeededDialog::class.java.name
    }
}