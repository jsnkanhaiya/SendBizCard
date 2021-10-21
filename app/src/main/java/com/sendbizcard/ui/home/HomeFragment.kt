package com.sendbizcard.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentHomeBinding
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.location.LocationManager

import com.sendbizcard.utils.LocationGetter

import androidx.core.content.ContextCompat.getSystemService





@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var isAttachInvisible: Boolean = true
    var permissionDeniedDialog: PermissionDeniedDialog? = null
    var permissionNeededDialog: PermissionNeededDialog? = null
    private lateinit var binding: FragmentHomeBinding
    val REQUEST_LOCATION =0

    companion object {
        private const val REQUEST_CODE_CAMERA = 0x1001
        private const val REQUEST_CODE_IMAGE_CAPTURE = 0x1002
        private const val REQUEST_CODE_IMAGE_CAPTION = 0x1003
        private const val REQUEST_CODE_GALLERY = 0x1004
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x1005
        private const val REQUEST_CODE_DOCUMENT = 0x1006
        private const val IMAGE_MIME_TYPE = "image/*"
    }

    lateinit var currentPhotoPath: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initOnClicks()
        observeData()
        initViews()


    }

    private fun initViews() {
        /*binding.tvToolBar.tvBack.gone()
        binding.tvToolBar.tvTitle.visible()
        binding.tvToolBar.tvTitle.text = resources.getString(R.string.menu_home)*/

    }


    private fun observeData() {

    }

    private fun initOnClicks() {
        binding.ourServicesCL.setOnClickListener {
            findNavController().navigate(R.id.nav_our_services)
        }

        binding.imgUser.setOnClickListener {
            checkpermissionForCameraGallery()
        }

        binding.imgSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val designation = binding.etDesignation.text.toString()
            val mobileNumber = binding.etMobileNumber.text.toString()
            val emailId = binding.etEmail.text.toString()
            val website = binding.etWebsite.text.toString()
            val location = binding.etLocation.text.toString()


            when {
                name.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.enter_name),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                /*designation.isEmpty() -> {
                    return@setOnClickListener
                }*/
                mobileNumber.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.enter_mobile_number),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                /*emailId.isEmpty() -> {
                    return@setOnClickListener
                }*/
                /* website.isEmpty() -> {
                     return@setOnClickListener
                 }*/
                location.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.enter_location),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                else -> homeViewModel.addCardRequest(
                    name,
                    designation,
                    mobileNumber,
                    emailId,
                    website,
                    location
                )
            }
        }

        binding.imgLocation.setOnClickListener {
            getTheUserPermission()
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate


    fun checkpermissionForCameraGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showImageDialogView()
        } else {
            showPermissionNeededDialog()
//                    requestStoragePermission()
        }
    }

    private fun showPermissionNeededDialog() {

    }

    private fun showImageDialogView() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
// ...Irrelevant code for customizing the buttons and title
// ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        dialogBuilder.setView(inflater.inflate(com.sendbizcard.R.layout.attachment_sheet, null))
        val alertDialog: AlertDialog = dialogBuilder.create()

        var camera = alertDialog.findViewById<TextView>(com.sendbizcard.R.id.camera_view)
        var gallery = alertDialog.findViewById<TextView>(com.sendbizcard.R.id.gallery_view)

        camera.setOnClickListener {

            requestCameraPermission()
        }

        gallery.setOnClickListener {
            requestStoragePermission()
        }

        alertDialog.show()
    }


    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {
                showPermissionDeniedDialog(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE_CAMERA
                )
            }
        }
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                showPermissionDeniedDialog(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    REQUEST_CODE_READ_EXTERNAL_STORAGE
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun showPermissionDeniedDialog(
        permissions: String,
        permissionRequestCode: Int
    ) {
        permissionDeniedDialog = PermissionDeniedDialog.Builder().build()
        permissionDeniedDialog?.show(
            requireActivity().supportFragmentManager,
            PermissionDeniedDialog.TAG
        )

        permissionDeniedDialog?.setButtonClickListener(object :
            PermissionDeniedDialog.OnButtonClickListener {
            override fun exit() {
//                finish()
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permissions),
                    permissionRequestCode
                )
                permissionDeniedDialog?.dismiss()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {

            REQUEST_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImageDialogView()
                    cameraIntent()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.CAMERA
                        )
                    ) {
                        showPermissionDeniedDialog(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA)
                    }
                }
            }

            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImageDialogView()
                    galleryIntent()
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        showPermissionDeniedDialog(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            REQUEST_CODE_READ_EXTERNAL_STORAGE
                        )
                    } /*else {
//                        showMandatoryPermissionsNeedDialog()
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) && (viewModel.readStorageDeniedOnce)) {
                            showMandatoryPermissionsNeedDialog()
                        }
                    }*/
                }
            }
        }
    }


    private fun cameraIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                //  CoreUtility.printChatLog(TAG, "IOException occurred ${ex.localizedMessage}")
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    context?.packageName.toString() + ".provider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            timeStamp, ".jpg", storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            printLog("TAG", "CURRENT PHOTO PATH $currentPhotoPath")
        }
    }

    private fun galleryIntent() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = IMAGE_MIME_TYPE
        }
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            REQUEST_CODE_IMAGE_CAPTURE -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    currentPhotoPath
                    //addImageCaption(currentPhotoPath)
                }
            }
            REQUEST_CODE_GALLERY -> {

                if (resultCode == AppCompatActivity.RESULT_OK) {

                    val imageUri = data?.data
                    printLog("TAG", "IMAGE URI $imageUri")

                    if (imageUri != null) {

                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            printLog("TAG", "IOException occurred ${ex.localizedMessage}")
                            null
                        }
                        // Continue only if the File was successfully created
                        photoFile?.also {
                            //viewModel.copyImageUriToExternalFilesDir(imageUri, photoFile)
                        }
                    } else {
                        showErrorDialog(
                            "Selected image file might be invalid or not available on device",
                            requireActivity(),
                            requireContext()
                        )
                    }
                }
            }

        }
    }

    private fun getTheUserPermission() {
        var addresses=""
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
       var locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val locationGetter =
            LocationGetter(requireActivity(), REQUEST_LOCATION, locationManager)
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationGetter.OnGPS()
        } else {
            addresses =  locationGetter.getLocation()

            binding.etLocation.text.apply {
                addresses
            }
        }
    }




}