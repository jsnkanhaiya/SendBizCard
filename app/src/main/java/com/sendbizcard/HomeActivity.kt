package com.sendbizcard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityHomeBinding
import com.sendbizcard.ui.home.HomeViewModel
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.ui.profile.ProfileViewModel
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    lateinit var tvName : TextView

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        binding.appBarHome.progressBarContainer.visible()
        profileViewModel.getUserProfileData()
        setSupportActionBar(binding.appBarHome.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_my_profile,
                R.id.nav_saved_cards,
                R.id.nav_feedback,
                R.id.nav_select_theme,
                R.id.nav_share_app,
                R.id.nav_logout,
            ), binding.drawerLayout
        )
        setUpDrawerLayout()
        setObservers()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setObservers() {
        homeViewModel.logoutResponse.observe(this) {
            binding.appBarHome.progressBarContainer.gone()
            showSuccessDialog()
        }
        profileViewModel.userProfileResponse.observe(this){
            binding.appBarHome.progressBarContainer.gone()
            tvName.text = it.user?.name?:""
        }

        homeViewModel.showNetworkError.observe(this, Observer {
            binding.appBarHome.progressBarContainer.visibility = View.GONE
             showErrorDialog(it,this,this)
        })

        homeViewModel.showUnknownError.observe(this, Observer {
            binding.appBarHome.progressBarContainer.visibility = View.GONE
            showErrorDialog(it,this,this)
        })

        homeViewModel.showServerError.observe(this) { errorMessage ->
            binding.appBarHome.progressBarContainer.visibility = View.GONE
            showErrorDialog(errorMessage,this,this)
        }

        profileViewModel.showNetworkError.observe(this, Observer {
            binding.appBarHome.progressBarContainer.visibility = View.GONE
            showErrorDialog(it,this,this)
        })

        profileViewModel.showUnknownError.observe(this, Observer {
            binding.appBarHome.progressBarContainer.visibility = View.GONE
            showErrorDialog(it,this,this)
        })

        profileViewModel.showServerError.observe(this) { errorMessage ->
            binding.appBarHome.progressBarContainer.visibility = View.GONE
            showErrorDialog(errorMessage,this,this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpDrawerLayout() {

        binding.navView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navView.setNavigationItemSelectedListener {dest ->
            when(dest.itemId) {
                R.id.nav_share_app -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    shareApp(this)
                }

                R.id.nav_logout -> {

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    showSuccessDialog()
                  //  binding.appBarHome.progressBarContainer.visible()

                }

                else -> {
                    NavigationUI.onNavDestinationSelected(dest, navController)
                    binding.drawerLayout.closeDrawers()
                }
            }

            true
        }

        var headerview = binding.navView.getHeaderView(0);
         tvName = headerview.findViewById<TextView>(R.id.tvName)

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        //  binding.progressBarContainer.visibility = View.GONE
        AlertDialogWithImageView.showDialog(
            supportFragmentManager.beginTransaction(),
            this,
            this.resources.getString(R.string.success_title),
            this.resources.getString(R.string.success_title_logout),
            R.drawable.ic_success,
            onDismiss = {
                homeViewModel.clearAllData()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()

            }
        )

    }

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate


    override fun onDestroy() {
        super.onDestroy()
        UserSessionManager.clearList()
    }
}