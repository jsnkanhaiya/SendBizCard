package com.sendbizcard.ui.contactList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentGetContactListBinding
import dagger.hilt.android.AndroidEntryPoint
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.dialog.ServerErrorDialogFragment
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.ui.sharecard.ViewCardViewModel
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.shareApp
import com.sendbizcard.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class GetContactListFragment : BaseFragment<FragmentGetContactListBinding>() {

    @Inject
    lateinit var contactListAdapter: ContactListAdapter
    private val viewCardViewModel: ViewCardViewModel by viewModels()

    private val contactListViewModel: GetContactListViewModel by viewModels()
    private lateinit var binding: FragmentGetContactListBinding
    var deletedId = -1
    private val REQUEST_CODE_CONTACTS = 0x1006
    var isSearch = false
    private val contactList: ArrayList<String> by lazy {
        ArrayList()
    }

    private val sortedContactList: ArrayList<CardDetailsItem> by lazy {
        ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setUpObservers()
        requestReadContactPermission()
        initOnTextChangedListener()
    }

    private fun setUpObservers(){

        viewCardViewModel.deleteCardResponse.observe(this) {
            binding.progressBarContainer.gone()
            hideProgressBar()
            var deletedCardIndex = -1

            for ((index, value) in contactListAdapter.list.withIndex()) {
                val id = value.id ?: -1
                if (id == deletedId){
                    deletedCardIndex = index
                    break
                }
            }
            if (deletedCardIndex!=-1){
                showSuccessDialog()
                contactListAdapter.list.removeAt(deletedCardIndex)
                contactListAdapter.notifyItemRemoved(deletedCardIndex)
            }
        }

        viewCardViewModel.viewCardResponse.observe(this) { cardList ->
            hideProgressBar()
            cardList.redirectUrl?.let { shareApp(requireContext(), it) }
        }


        contactListViewModel.cardListLiveData.observe(this) { cardList ->
            if (cardList.isNotEmpty()) {
                compareContactList(cardList)
            } else {
                hideProgressBar()
            }
        }

        contactListViewModel.cardSearchLiveData.observe(this) { searchCardList ->
            hideProgressBar()
            setUpSearchDataInAdapter(ArrayList(searchCardList))
        }

        contactListViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        contactListViewModel.showServerError.observe(this) { serverError ->
            if (serverError.code == 401) {
                showServerErrorMessage()
            } else {
                showErrorMessage(serverError.errorMessage)
            }
        }

        contactListViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }

    private fun showSuccessDialog() {
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.success_title),
            resources.getString(R.string.success_title_card_delete),"",R.drawable.ic_icon_success)
        fragment.show(parentFragmentManager,"CardListFragment")
    }


    private fun showServerErrorMessage() {
        hideProgressBar()
        val fragment = ServerErrorDialogFragment.newInstance()
        fragment.callbacks = object : ServerErrorDialogFragment.Callbacks {
            override fun onOKClicked() {
                contactListViewModel.clearAllData()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        fragment.show(parentFragmentManager, "ContactListFragment")
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"", R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"ContactListFragment")
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }

    private fun initOnTextChangedListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val searchData = charSequence.toString()
                /*val cardList = contactListViewModel.cardListLiveData.value ?: ArrayList()
                if (searchData.isEmpty() && cardList.isNotEmpty()&& searchData.length>=3){
                    setUpSearchDataInAdapter(cardList)
                }*/
                if (searchData.isNotEmpty() && searchData.length>=3) {
                    showProgressBar()
                    isSearch = true
                    contactListViewModel.getCardSearchList(searchData)
                }else if (searchData.isEmpty() && isSearch){
                    showProgressBar()
                    isSearch =false
                    contactListViewModel.getCardList()
                }

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpSearchDataInAdapter(cardList: List<CardDetailsItem>) {
        contactListAdapter.addAll(cardList)
        contactListAdapter.notifyDataSetChanged()
    }

    private fun requestReadContactPermission(){
        if (checkReadContactsPermission()){
            readContactsInBackgroundThread()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_CONTACTS
            )
        }
    }

    private fun checkReadContactsPermission() : Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContactsInBackgroundThread()
                } else {
                    Toast.makeText(requireContext(), "Allow Permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun readContactsInBackgroundThread() {
        showProgressBar()
        viewLifecycleOwner.lifecycleScope.launch {
            val contactList = withContext(Dispatchers.IO){
                readContacts()
            }
            if (contactList.isNotEmpty()){
                contactListViewModel.getCardList()
            } else {
                hideProgressBar()
            }
        }
    }

    private fun compareContactList(cardList: List<CardDetailsItem>) {
        viewLifecycleOwner.lifecycleScope.launch {
            val list = withContext(Dispatchers.IO){
                compareListInBackgroundThread(cardList)
            }
            hideProgressBar()
            if (list.isNotEmpty()){
                binding.tvNotfound.gone()
                binding.rvCardList.visible()
                binding.clSearchBar.visible()
                setUpAdapter(list)
            }
            else
            {
                binding.rvCardList.gone()
                binding.clSearchBar.gone()
                binding.tvNotfound.visible()
            }

        }
    }

    private fun compareListInBackgroundThread(cardList: List<CardDetailsItem>) : ArrayList<CardDetailsItem> {
        sortedContactList.clear()
        for (contact in contactList) {
            for (item in cardList){
                if (item.contactNo == contact){
                    sortedContactList.add(item)
                    break
                }
            }
        }
        return sortedContactList
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter(cardList: ArrayList<CardDetailsItem>) {
        contactListAdapter.addAll(cardList)
        contactListAdapter.cardClickListener = object : BaseViewHolder.ItemCardClickCallback<CardDetailsItem> {
            override fun onEditClicked(data: CardDetailsItem, pos: Int) {
                Log.d("CardListFragment", "EditClickedCallback")
                if (contactListViewModel.getThemeId() == "3"){
                    findNavController().navigate(R.id.nav_edit_card, bundleOf("cardItem" to data,"isFromPreviewCard" to false),
                        getDefaultNavigationAnimation()
                    )
                } else {
                    findNavController().navigate(R.id.nav_edit_card_v2, bundleOf("cardItem" to data,"isFromPreviewCard" to false),
                        getDefaultNavigationAnimation()
                    )
                }

            }

            override fun onPreviewClicked(data: CardDetailsItem, pos: Int) {
                Log.d("CardListFragment", "PreviewClickedCallback")
                /*if (contactListViewModel.getThemeId() == "3"){
                    findNavController().navigate(R.id.nav_edit_card, bundleOf("cardItem" to data,"isFromPreviewCard" to true),
                        getDefaultNavigationAnimation()
                    )
                } else {
                    findNavController().navigate(R.id.nav_edit_card_v2, bundleOf("cardItem" to data,"isFromPreviewCard" to true),
                        getDefaultNavigationAnimation()
                    )
                }*/
                findNavController().navigate(
                    R.id.nav_view_card, bundleOf("id" to data.id),
                    getDefaultNavigationAnimation()
                )
            }

            override fun onShareClicked(data: CardDetailsItem, pos: Int) {
                Log.d("CardListFragment", "ShareClickedCallback")
                viewCardViewModel.getCardURL(data.id.toString(), contactListViewModel.getThemeId())
                // shareApp(requireContext(),data.)
            }

            override fun onDeleteClicked(data: CardDetailsItem, pos: Int) {
                Log.d("CardListFragment", "DeleteClickedCallback")
                deletedId = data.id ?: -1
                binding.progressBarContainer.visible()
                viewCardViewModel.deleteCard(data.id.toString())

            }

        }
        binding.rvCardList.adapter = contactListAdapter
        contactListAdapter.notifyDataSetChanged()
    }

    // function to read contacts using content resolver
    @SuppressLint("Range")
    private fun readContacts() : List<String> {
        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhoneNumber > 0 ){
                    val cp = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(id),
                        null
                    )
                    if (cp != null && cp.moveToFirst()) {
                        val phone =
                            cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        cp.close()
                        contactList.add(phone)
                    }
                }
            } while (cursor.moveToNext())
        }
        return contactList.distinct()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGetContactListBinding
        get() = FragmentGetContactListBinding::inflate
}