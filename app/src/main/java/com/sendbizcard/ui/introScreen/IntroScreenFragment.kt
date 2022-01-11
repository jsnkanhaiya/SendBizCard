package com.sendbizcard.ui.introScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentIntroScreenBinding
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroScreenFragment : BaseFragment<FragmentIntroScreenBinding>() {

    @Inject
    lateinit var introScreenAdapter: IntroScreenAdapter

    private lateinit var binding: FragmentIntroScreenBinding

    private val introScreenViewModel : IntroScreenViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setUpAdapter()
        initOnClicks()
        introScreenViewModel.setFirstTimeUser(false)
    }

    private fun initOnClicks() {
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }

        binding.tvNext.setOnClickListener {
            binding.introViewPager.setCurrentItem(binding.introViewPager.currentItem + 1, true)
        }

        binding.btnGetStarted.setOnClickListener {
            val direction = IntroScreenFragmentDirections.actionIntroFragmentToSignInFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setUpAdapter() {
        val listOfIntroScreenImages = ArrayList<Int>()
        listOfIntroScreenImages.add(R.drawable.theme_1)
        listOfIntroScreenImages.add(R.drawable.theme_2)
        listOfIntroScreenImages.add(R.drawable.theme_3)
        listOfIntroScreenImages.add(R.drawable.theme_4)

        introScreenAdapter.addAll(listOfIntroScreenImages)

        binding.introViewPager.apply {
            adapter = introScreenAdapter
            registerOnPageChangeCallback(viewPagerChangeCallback)
        }
    }

    private val viewPagerChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.indicatorViewPager.selection = position
            setGetStartedButtonText(position)
        }
    }

    private fun setGetStartedButtonText(position: Int) {
        if (position == introScreenAdapter.listSize - 1) {
            binding.btnGetStarted.visible()
            binding.tvNext.gone()
            binding.tvSkip.gone()
        } else {
            binding.btnGetStarted.gone()
            binding.tvNext.visible()
            binding.tvSkip.visible()
        }
    }

    override fun onDestroy() {
        binding.introViewPager.unregisterOnPageChangeCallback(viewPagerChangeCallback)
        super.onDestroy()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIntroScreenBinding
        get() = FragmentIntroScreenBinding::inflate


}