package com.sendbizcard.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    override fun onResume() {
        super.onResume()
       /* withDelayOnMain(100){
            if(findNavController().currentDestination?.id == R.id.splashFragment) {
                when (splashViewModel.isUserLoggedIn) {
                    true -> {
                        CoreUtility.printLog(this.javaClass.name, "show landing called")
                        appModuleRoute.popAllPreviousScreensAndShowLandingScreen(findNavController())
                    }
                    else -> {
                        context?.let {
                            LocaleHelper.setDefaultAppLocale(it)
                        }
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                        )
                    }
                }
            }
        }*/
    }
}