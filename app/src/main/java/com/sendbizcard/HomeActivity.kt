package com.sendbizcard

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityHomeBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.ui.home.HomeViewModel
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding


    lateinit var tvName: TextView
    lateinit var imgFb: AppCompatImageView
    lateinit var imgInsta: AppCompatImageView
    lateinit var imgTwiter: AppCompatImageView

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()



        setSupportActionBar(binding.appBarHome.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_home_v2,
                R.id.nav_my_profile,
                R.id.nav_saved_cards,
                R.id.nav_contact_list,
                R.id.nav_feedback,
                R.id.nav_select_theme,
                R.id.nav_share_app,
                R.id.nav_logout,
            ), binding.drawerLayout
        )
        setUpDrawerLayout()
        setUpObservers()

        if (homeViewModel.getThemeId() == "3") {
            navController.navigate(R.id.nav_home)
        } else {
            navController.navigate(R.id.nav_home_v2)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpObservers() {
        homeViewModel.logoutResponse.observe(this) {
            binding.appBarHome.progressBarContainer.gone()
            showSuccessDialog()
        }
        homeViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        homeViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        homeViewModel.showServerError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage.errorMessage)
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        binding.appBarHome.progressBarContainer.visibility = View.GONE
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.error),
            errorMessage, "", R.drawable.ic_icon_error
        )
        fragment.show(supportFragmentManager, "HomeActivity")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpDrawerLayout() {

        binding.navView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.navView.setNavigationItemSelectedListener { dest ->
            when (dest.itemId) {
                R.id.nav_home -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                    if (homeViewModel.getThemeId() == "3") {
                        navController.navigate(R.id.nav_home)
                    } else {
                        navController.navigate(R.id.nav_home_v2)
                    }
                }


                R.id.nav_share_app -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    shareApp(this, "")
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

        binding.navView

        val headerview = binding.navView.getHeaderView(0);
        val socialMediaCL = binding.navView.findViewById<ConstraintLayout>(R.id.socialMediaCL);
        tvName = headerview.findViewById<TextView>(R.id.tvName)
        imgFb = socialMediaCL.findViewById<AppCompatImageView>(R.id.img_fb)
        imgInsta = socialMediaCL.findViewById<AppCompatImageView>(R.id.img_insta)
        imgTwiter = socialMediaCL.findViewById<AppCompatImageView>(R.id.img_twitter)

        tvName.text = homeViewModel.getUserName()

        imgFb.setOnClickListener {
            val open = Intent(Intent.ACTION_VIEW, Uri.parse("www.facebook.com/SendBusinessCardDigitally"))
            startActivity(open)
        }

        imgInsta.setOnClickListener {
            val open = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sendbusinesscard/"))
            startActivity(open)
        }

        imgTwiter.setOnClickListener {
            val open = Intent(Intent.ACTION_VIEW, Uri.parse(" https://www.linkedin.com/company/sendbusinesscard"))
            startActivity(open)
        }
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
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.success_title),
            resources.getString(R.string.success_title_logout), "", R.drawable.ic_icon_success
        )
        fragment.callbacks = object : CommonDialogFragment.Callbacks {
            override fun onDismissClicked() {
                homeViewModel.clearAllData()
                val i = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        fragment.show(supportFragmentManager, "HomeActivity")
    }

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate


    override fun onDestroy() {
        super.onDestroy()
        UserSessionManager.clearList()
    }
}