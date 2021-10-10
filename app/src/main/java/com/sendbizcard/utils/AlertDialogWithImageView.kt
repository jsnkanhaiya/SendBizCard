package com.sendbizcard.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import java.lang.IllegalStateException


class AlertDialogWithImageView : DialogFragment() {

  private lateinit var onDismiss: () -> Unit

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return LayoutInflater.from(context).inflate(R.layout.layout_alert_dialog_with_image, null)
  }

  override fun onDismiss(dialog: DialogInterface) {
    onDismiss()
    super.onDismiss(dialog)
  }

  override fun onStart() {
    super.onStart()
    dialog?.window?.setLayout(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.WRAP_CONTENT)
  }

  @RequiresApi(Build.VERSION_CODES.M)
  fun configureDialog(
    title: String,
    message: String,
    @DrawableRes imageDrawableRes: Int,
    titleColor: Int = resources.getColor(R.color.colorPrimary) ,
    messageColor: Int =  resources.getColor(R.color.textcolour),
    backgroundColor: Int = resources.getColor(R.color.white),
    onDismiss: () -> Unit = {}
  ) {
    dialog?.findViewById<ConstraintLayout>(R.id.root)?.background = dialog?.findViewById<ConstraintLayout>(R.id.root)?.background?.let {
      (it.mutate() as InsetDrawable).run {
        drawable = (drawable?.mutate() as GradientDrawable).let {
          it.setColor(backgroundColor)
          it
        }
        this
      }
    }
    dialog?.findViewById<ImageView>(R.id.dialogIv)?.setImageResource(imageDrawableRes)
    dialog?.findViewById<TextView>(R.id.dialogTitleTv)?.apply {
      text = title
      setTextColor(titleColor)
      if (title.isEmpty()) {
        View.GONE
      }
    }
    dialog?.findViewById<TextView>(R.id.dialogDescriptionTv)?.apply {
      text = message
      setTextColor(messageColor)
      setMargin(
        context.convertDpToPx(16),
        context.convertDpToPx(16),
        context.convertDpToPx(16)
      )
    }
    this.onDismiss = onDismiss
  }

  fun dismissDialog(){
    dialog?.dismiss()
  }

  companion object {
    @RequiresApi(Build.VERSION_CODES.M)
    fun showDialog(
      fragmentTransaction: FragmentTransaction,
      context: Context,
      title: String,
      message: String,
      @DrawableRes imageDrawableRes: Int,
      titleColor: Int = context.resources.getColor(R.color.colorPrimary) ,
      messageColor: Int =  context.resources.getColor(R.color.textcolour),
      backgroundColor: Int = context.resources.getColor(R.color.white),
       autoDismiss : Boolean = true,
      onDismiss: () -> Unit = {}
    ) {
      try {
        val dialogFragment = AlertDialogWithImageView()
        dialogFragment.show(fragmentTransaction, "dialog")
        withDelayOnMain(50) {
          dialogFragment.configureDialog(
            title,
            message,
            imageDrawableRes,
            titleColor,
            messageColor,
            backgroundColor,
            onDismiss
          )
        }
        if (autoDismiss) {
          withDelayOnMain(5000) {
            dialogFragment.dismissDialog()
          }
        }
      } catch (illegalStateException : IllegalStateException){

      }
    }
  }


}