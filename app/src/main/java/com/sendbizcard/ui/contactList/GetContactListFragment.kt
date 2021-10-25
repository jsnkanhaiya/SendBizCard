package com.sendbizcard.ui.contactList

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.fragment.app.viewModels


@AndroidEntryPoint
class GetContactListFragment : BaseFragment<FragmentGetContactListBinding>() {

    private val contactListViewModel: GetContactListViewModel by viewModels()

    private val REQUEST_CODE_CONTACTS = 0x1006
    private val contactList: ArrayList<String> by lazy {
        ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestReadContactPermission()
        setUpObservers()
    }

    private fun setUpObservers(){
        contactListViewModel.cardListLiveData.observe(this) {

        }

        contactListViewModel.cardSearchLiveData.observe(this){

        }
    }

    private fun requestReadContactPermission(){
        if (checkReadContactsPermission()){
            val list = readContacts()
            if (list.isNotEmpty()){
                contactListViewModel.getCardList()
            }
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
                    val list = readContacts().distinct()
                    if (list.isNotEmpty()){
                        contactListViewModel.getCardList()
                    }
                } else {
                    Toast.makeText(requireContext(), "Allow Permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // function to read contacts using content resolver
    @SuppressLint("Range")
    private fun readContacts() : ArrayList<String> {
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
        return contactList
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGetContactListBinding
        get() = FragmentGetContactListBinding::inflate
}