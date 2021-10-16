package com.sendbizcard.dialog

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sendbizcard.R
import com.sendbizcard.databinding.DialogConfirmationBinding
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.printLog
import com.sendbizcard.utils.visible

class ConfirmationDialogFragment : DialogFragment() {

    private lateinit var binding: DialogConfirmationBinding
    private var onButtonClickListener: OnButtonClickListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogConfirmationBinding.inflate(LayoutInflater.from(context))

        val cancelable =
            if (arguments != null && requireArguments().containsKey(PARAM_CANCELABLE)) {
                printLog(TAG, "" + requireArguments()[PARAM_CANCELABLE] as Boolean)
                requireArguments()[PARAM_CANCELABLE] as Boolean

            } else {

                true
            }
        isCancelable = cancelable

        return binding.root

    }

    override fun onDismiss(dialog: DialogInterface) {
        printLog(this.javaClass.name, "tick tick dialog dismiss->>>>>>>")
        super.onDismiss(dialog)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null && requireArguments().containsKey(PARAM_TITLE)) {
            binding.titleTextView.text = requireArguments()[PARAM_TITLE] as CharSequence
        }

        if (arguments != null && requireArguments().containsKey(PARAM_MESSAGE)) {

            if (arguments != null && requireArguments().containsKey(PARAM_IS_HTML)) {
                binding.messageTextView.text = Html.fromHtml(
                    requireArguments()[PARAM_MESSAGE].toString(), 0
                )
                printLog(TAG, "message to show html")
            } else {
                printLog(TAG, "message not html")

                binding.messageTextView.text = requireArguments()[PARAM_MESSAGE] as CharSequence
            }

        }

        if (arguments != null && requireArguments().containsKey(PARAM_BUTTON_ACCEPT)) {
            binding.acceptButton.text = requireArguments()[PARAM_BUTTON_ACCEPT].toString()
        }

        if (arguments != null && requireArguments().containsKey(PARAM_BUTTON_DECLINE)) {
            binding.declineButton.text = requireArguments()[PARAM_BUTTON_DECLINE].toString()
        } else {
            binding.declineButton.gone()
        }

        if (arguments != null && requireArguments().containsKey(PARAM_MESSAGE_TEXT_COLOR)) {

            val color = requireArguments()[PARAM_MESSAGE_TEXT_COLOR] as Int
            binding.messageTextView.setTextColor(color)
        }
        if (arguments != null && requireArguments().containsKey(PARAM_ALERT_IMAGE)) {
            binding.alertAppCompatImageView.setImageResource(
                requireArguments().getInt(
                    PARAM_ALERT_IMAGE
                )
            )
            binding.alertAppCompatImageView.visible()
        } else {
            binding.alertAppCompatImageView.gone()

        }

        binding.acceptButton.setOnClickListener {
            printLog(this.javaClass.name, "in aaacept click $onButtonClickListener")
            onButtonClickListener?.onAcceptClick()
        }

        binding.declineButton.setOnClickListener {
            onButtonClickListener?.onRejectClick()
        }
    }

    class Builder {

        private val args = Bundle()

        fun setTitle(str: CharSequence): Builder {
            this.args.putCharSequence(PARAM_TITLE, str)
            return this
        }

        fun setMessage(str: CharSequence): Builder {
            this.args.putCharSequence(PARAM_MESSAGE, str)
            return this
        }

        fun setAcceptButton(str: String): Builder {
            this.args.putString(PARAM_BUTTON_ACCEPT, str)
            return this
        }

        fun setDeclineButton(str: String): Builder {
            this.args.putString(PARAM_BUTTON_DECLINE, str)
            return this
        }

        fun setMessageTextColor(colorId: Int): Builder {
            this.args.putInt(PARAM_MESSAGE_TEXT_COLOR, colorId)
            return this
        }

        fun setAlertImage(alertImage: Int): Builder {
            this.args.putInt(PARAM_ALERT_IMAGE, alertImage)
            return this
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            this.args.putBoolean(PARAM_CANCELABLE, isCancelable)
            return this
        }

        fun setIsHtmlText(isHtml: Boolean): Builder {
            this.args.putBoolean(PARAM_IS_HTML, isHtml)
            return this
        }

        fun build(): ConfirmationDialogFragment {
            val confirmDialogFragment =
                ConfirmationDialogFragment()
            confirmDialogFragment.arguments = this.args
            return confirmDialogFragment
        }
    }

    interface OnButtonClickListener {
        fun onAcceptClick()
        fun onRejectClick()
    }

    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener
    }

    companion object {
        val TAG = ConfirmationDialogFragment::class.java.name

        private val PARAM_BUTTON_ACCEPT = "buttonAccept"
        private val PARAM_BUTTON_DECLINE = "buttonDecline"
        private val PARAM_MESSAGE = "message"
        private val PARAM_TITLE = "title"
        private val PARAM_MESSAGE_TEXT_COLOR = "messageColor"
        private val PARAM_ALERT_IMAGE = "alertImage"
        private val PARAM_CANCELABLE = "isCancelable"
        private val PARAM_IS_HTML = "isHtml"

        fun dismissAllDialogs(manager: FragmentManager) {
            val fragments: List<Fragment> = manager.fragments
            for (fragment in fragments) {
                if (fragment is DialogFragment) {
                    val dialogFragment = fragment as DialogFragment
                    dialogFragment.dismissAllowingStateLoss()
                }
                val childFragmentManager: FragmentManager = fragment.childFragmentManager
                if (childFragmentManager != null) dismissAllDialogs(childFragmentManager)
            }
        }


    }
}