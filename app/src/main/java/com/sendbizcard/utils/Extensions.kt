package com.sendbizcard.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sendbizcard.R

fun withDelayOnMain(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}

fun AppCompatImageView.loadImages(url: String) {
    val options =
        RequestOptions().placeholder(R.drawable.placeholder_image).error(R.drawable.placeholder_image)
    Glide.with(context)
        .load(url).
    apply(options).into(this)
}

fun AppCompatImageView.loadImagesUser(url: String) {
    val options =
        RequestOptions().placeholder(R.drawable.ic_profile_img).error(R.drawable.ic_profile_img)
    Glide.with(context)
        .load(url).
        apply(options).into(this)
}


fun AppCompatImageView.loadCircleImages(url: String) {

    Glide.with(context).load(url) .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_profile_img).error(R.drawable.ic_profile_img)).into(this)
}

fun AppCompatImageView.loadBitmap(bitmap: Bitmap) {
    Glide.with(context)
        .load(bitmap)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun TextInputEditText.checkValidations(fieldName: String,password: String? = ""): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    textInputLayout.errorIconDrawable = null
    this.onChange { textInputLayout.error = null }

    val strInputField = this.text.toString().trim()

    when(fieldName){
        FieldEnum.EMAIL_ID.fieldName -> {
            return if (strInputField.isValidEmail()){
                true
            } else {
                textInputLayout.error = "Enter Email Id"
                false
            }
        }
        FieldEnum.PASSWORD.fieldName -> {
            return if (strInputField.isValidPassword()){
                true
            } else {
                textInputLayout.error = "Password must contain at least: 1 uppercase letter, 1 lowercase letter, 1 number, and one special character"
                false
            }
        }
        FieldEnum.MOBILE_NUMBER.fieldName -> {
            return if (strInputField.length == 10){
                true
            } else {
                textInputLayout.error = "Enter 10 Digit Mobile Number"
                false
            }
        }
        FieldEnum.OTP.fieldName -> {
            return if (strInputField.length == 6){
                true
            } else {
                textInputLayout.error = "Enter 6 Digit OTP"
                false
            }
        }

        FieldEnum.FORGOT_OTP.fieldName -> {
            return if (strInputField.length == 5){
                true
            } else {
                textInputLayout.error = "Enter 5 Digit OTP"
                false
            }
        }
        FieldEnum.NAME.fieldName -> {
            return if (strInputField.isNotEmpty()) {
                true
            } else {
                textInputLayout.error = "Enter Your Name"
                false
            }
        }
        FieldEnum.CONFIRM_PASSWORD.fieldName -> {
            return if (strInputField == password) {
                true
            } else {
                textInputLayout.error = "Confirm password does not match with password"
                false
            }
        }

        else -> {
            return if (strInputField.isNotEmpty()) {
                true
            } else {
                textInputLayout.error = "Enter Mandatory Field"
                false
            }
        }
    }

}



fun TextInputEditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun String.isValidEmail() = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    val passwordMatcher = Regex(passwordPattern)
    return this.isNotEmpty() && passwordMatcher.matches(this)
}
