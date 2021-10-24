package com.sendbizcard.firebaseRemoteConfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigImpl @Inject constructor(private val firebaseRemoteConfig: FirebaseRemoteConfig) :
    RemoteConfigProvider {

    companion object {
        private const val BASE_URL = "base_url"
        private const val LOGIN_URL = "login_url"
        private const val REGISTER_URL = "register_url"
        private const val FORGOT_PASSWORD_URL = "forgot_password_url"
        private const val CHANGE_PASSWORD_URL = "change_password_url"
        private const val VERIFY_OTP_URL = "verify_otp_url"
        private const val RESEND_OTP_URL = "resend_otp_url"
        private const val ADD_CARD_URL = "add_card_url"
        private const val FEEDBACK_URL = "feedback_url"
        private const val LOGOUT_URL = "logout_url"
        private const val THEME_LIST_URL = "theme_list_url"
        private const val VERIFY_FORGOT_OTP_URL = "verify_forgot_otp_url"
        private const val UPDATE_USER_DETAILS_URL = "update_user_details_url"
        private const val GET_USER_DETAILS_URL = "user_detail_url"
        private const val GET_CARD_SHARE_URL = "card_share_url"
        private const val GET_CARD_LIST_URL = "saved_cards_url"
        private const val GET_DELETE_CARD_URL = "delete_card_url"

    }

    override fun getBaseURL(): String {
        return firebaseRemoteConfig.getString(BASE_URL)
    }

    override fun getLoginURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(LOGIN_URL)
    }

    override fun getRegisterURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(REGISTER_URL)
    }

    override fun getForgotPasswordURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(FORGOT_PASSWORD_URL)
    }

    override fun getChangePasswordURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(CHANGE_PASSWORD_URL)
    }

    override fun getVerifyOTPURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(VERIFY_OTP_URL)
    }

    override fun getResendOTPURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(RESEND_OTP_URL)
    }

    override fun getAddCardURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(ADD_CARD_URL)
    }

    override fun getFeedbackURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(FEEDBACK_URL)
    }

    override fun getLogoutURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(LOGOUT_URL)
    }

    override fun getThemeListURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(THEME_LIST_URL)
    }

    override fun getForgotVerifyOTPURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(VERIFY_FORGOT_OTP_URL)
    }

    override fun getUpdateUserDetailsURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(UPDATE_USER_DETAILS_URL)
    }

    override fun getUserDetailsURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(GET_USER_DETAILS_URL)
    }

    override fun getCardShareURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(GET_CARD_SHARE_URL)
    }

    override fun getCardListURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(GET_CARD_LIST_URL)
    }

    override fun getDeleteCardURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(GET_DELETE_CARD_URL)
    }
}