package com.sendbizcard.ui.introScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.sendbizcard.R
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityIntroScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroScreenActivity : BaseActivity<ActivityIntroScreenBinding>() {

    @Inject
    lateinit var introScreenAdapter: IntroScreenAdapter

    private lateinit var binding: ActivityIntroScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setUpAdapter()

    }

    private fun setUpAdapter() {
        binding.introViewPager.apply {
            adapter = introScreenAdapter
            registerOnPageChangeCallback(viewPagerChangeCallback)
        }
    }

    private val viewPagerChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.indicatorViewPager.selection = position
            //setNextButtonText(position)
        }
    }

    override fun onDestroy() {
        binding.introViewPager.unregisterOnPageChangeCallback(viewPagerChangeCallback)
        super.onDestroy()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityIntroScreenBinding
        get() = ActivityIntroScreenBinding::inflate
}