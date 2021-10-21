package com.sendbizcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityHomeBinding
import com.sendbizcard.utils.UserSessionManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setSupportActionBar(binding.appBarHome.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_my_profile, R.id.nav_saved_cards, R.id.nav_feedback,R.id.nav_select_theme
            ), binding.drawerLayout
        )
        setUpDrawerLayout()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setUpDrawerLayout() {

        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate


    override fun onDestroy() {
        super.onDestroy()
        UserSessionManager.clearServiceList()
    }
}