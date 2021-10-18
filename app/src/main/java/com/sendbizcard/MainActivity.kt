package com.sendbizcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityHomeBinding
import com.sendbizcard.databinding.ActivityMainBinding
import com.sendbizcard.prefs.PreferenceSourceImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferenceSourceImpl: PreferenceSourceImpl

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
       // val host = findNavController(R.id.fragment_nav_host)
        /*if (preferenceSourceImpl.isFirstTimeUser){
            host.navigate(R.id.nav_intro)
        }else{
            host.navigate(R.id.nav_login)
        }*/

       // setSupportActionBar(binding.appBarHome.toolbar)
    }

}