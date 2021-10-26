package com.sendbizcard.ui.theme

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.FragmentThemeBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.models.response.theme.ListThemeItem
import com.sendbizcard.utils.PickingLayoutManager
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThemeFragment : BaseFragment<FragmentThemeBinding>() {

    @Inject
    lateinit var themeAdapter: ThemeAdapter

    private val themeViewModel: ThemeViewModel by viewModels()

    private lateinit var binding: FragmentThemeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        observeData()
        initOnClick()
        showProgressBar()
        themeViewModel.getThemeList()
    }

    private fun initOnClick() {
        binding.btnSave.setOnClickListener {

            val theme = themeAdapter.list.firstOrNull {
                it.isSelected
            }
            themeViewModel.saveThemeId(theme?.id.toString())

            var intent = Intent(requireContext(),HomeActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun observeData() {
        themeViewModel.themeList.observe(this) { themeList ->
            hideProgressBar()
            setUpAdapter(themeList)
        }

        themeViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        themeViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        themeViewModel.showServerError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"", R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"LoginFragment")
    }

    private fun setUpAdapter(themeList: List<ListThemeItem>) {
        if (themeList.isNotEmpty()) {
            binding.rvSelectTheme.layoutManager =
                PickingLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false,
                    0.64f
                )
            val snapHelper = PagerSnapHelper()
            binding.rvSelectTheme.onFlingListener = null
            snapHelper.attachToRecyclerView(binding.rvSelectTheme)
            themeList.getOrNull(0)?.isSelected = true
            themeAdapter.addAll(themeList)
            themeAdapter.clickListenerWithPosition =
                object : BaseViewHolder.ItemClickedCallBackWithPosition<ListThemeItem> {
                    override fun onItemClicked(data: ListThemeItem, pos: Int) {
                        themeAdapter.list.forEach {
                            it.isSelected = false
                        }

                        themeAdapter.list[pos].isSelected = true
                        binding.rvSelectTheme.smoothScrollToPosition(pos)
                        themeAdapter.notifyDataSetChanged()
                    }

                }
            binding.rvSelectTheme.adapter = themeAdapter
            binding.btnSave.visible()
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentThemeBinding
        get() = FragmentThemeBinding::inflate

}