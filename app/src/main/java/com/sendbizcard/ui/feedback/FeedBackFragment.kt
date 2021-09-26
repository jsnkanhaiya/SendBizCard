package com.sendbizcard.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.databinding.FeedbackFragmentBinding

class FeedBackFragment : Fragment() {

    private val TAG = "FeedBackFragment"

    private lateinit var feedBackViewModel: FeedBackViewModel
    private var _binding: FeedbackFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedBackViewModel =
            ViewModelProvider(this).get(FeedBackViewModel::class.java)

        _binding = FeedbackFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            if (feedBackViewModel.isValidAllValue()){
                feedBackViewModel.sendFeedBack()
            }
        }
    }

}