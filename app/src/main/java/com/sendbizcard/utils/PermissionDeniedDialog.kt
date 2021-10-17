package com.sendbizcard.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sendbizcard.R
import com.sendbizcard.databinding.DialogPermissionDeniedBinding

class PermissionDeniedDialog : DialogFragment() {

    private lateinit var binding: DialogPermissionDeniedBinding
    var onButtonClickListener: OnButtonClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(),
            R.style.DialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogPermissionDeniedBinding.inflate(LayoutInflater.from(context))
        isCancelable=true
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.tvErrorAcknowledge.setOnClickDelayListener {
            onButtonClickListener?.exit()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    interface OnButtonClickListener {
        fun exit()
    }

    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener
    }


    class Builder {

        fun build(): PermissionDeniedDialog {
            return PermissionDeniedDialog()
        }
    }

    companion object {
        val TAG = PermissionDeniedDialog::class.java.name
    }
}