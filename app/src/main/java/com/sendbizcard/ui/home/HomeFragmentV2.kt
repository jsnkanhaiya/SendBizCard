package com.sendbizcard.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.google.android.gms.location.*
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentHomeV2Binding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.dialog.SelectCameraGalleryDialog
import com.sendbizcard.dialog.ServerErrorDialogFragment
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*

@AndroidEntryPoint
class HomeFragmentV2  : BaseFragment<FragmentHomeV2Binding>(){

    private val APPNAME = "SendBizCardApp"
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeV2Binding
    private var backgroundColour = "#ef5e42"
    private val REQUEST_CODE_CAMERA = 0x1001
    private val REQUEST_CODE_IMAGE_CAPTURE = 0x1002
    private val REQUEST_CODE_GALLERY = 0x1003
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x1004
    private val REQUEST_CODE_LOCATION = 0x1005
    private val IMAGE_MIME_TYPE = "image/*"
    lateinit var currentPhotoPath: String
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var isUserImageSelected = false
    private var isCompanyLogoSelected = false
    private var userImageBase64String = ""
    private var companyLogoBase64String = ""

    private var isFromEditCard = false
    private var cardDetailsItem: CardDetailsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setDataToUI()
        initOnClicks()
        observeData()
    }

    private fun setDataToUI() {
        if (isFromEditCard){
            binding.imgEdit.visible()
            binding.etName.isEnabled = false
            binding.etDesignation.isEnabled = false
            binding.etMobileNumber.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etWebsite.isEnabled = false
            binding.etLocation.isEnabled = false
            binding.colorPalette.isEnabled = false
            binding.ourServicesCL.isEnabled = false
            binding.socialMediaCL.isEnabled = false
            binding.imgCompanyLogo.isEnabled = false
            binding.etCompanyName.isEnabled = false
            binding.imgSave.isEnabled = false
            binding.imgShare.isEnabled = false

        } else {
            binding.imgEdit.invisible()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeData() {

        homeViewModel.saveCardResponse.observe(this) {
            hideProgressBar()
            showSuccessDialog()
        }

        homeViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        homeViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        homeViewModel.showServerError.observe(this) { serverError ->
            if (serverError.code == 401) {
                showServerErrorMessage()
            } else {
                showErrorMessage(serverError.errorMessage)
            }
        }
    }

    private fun showProgressBar(){
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar(){
        binding.progressBarContainer.gone()
    }

    private fun showServerErrorMessage() {
        hideProgressBar()
        val fragment = ServerErrorDialogFragment.newInstance()
        fragment.callbacks = object : ServerErrorDialogFragment.Callbacks {
            override fun onOKClicked() {
                homeViewModel.clearAllData()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        fragment.show(parentFragmentManager, "HomeFragment")
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"", R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"HomeFragment")
    }

    private fun initOnClicks() {

        binding.imgEdit.setOnClickListener {
            binding.etName.isEnabled = true
            binding.etDesignation.isEnabled = true
            binding.etMobileNumber.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etWebsite.isEnabled = true
            binding.etLocation.isEnabled = true
            binding.colorPalette.isEnabled = true
            binding.ourServicesCL.isEnabled = true
            binding.socialMediaCL.isEnabled = true
            binding.imgCompanyLogo.isEnabled = true
            binding.etCompanyName.isEnabled = true
            binding.imgSave.isEnabled = true
            binding.imgShare.isEnabled = true
        }

        binding.colorPalette.setOnClickListener {
            showColourPattle()
        }

        binding.ourServicesCL.setOnClickListener {
            findNavController().navigate(R.id.nav_our_services)
        }

        binding.socialMediaCL.setOnClickListener {
            findNavController().navigate(R.id.nav_social_media_links)
        }

        binding.ourServicesCL.setOnClickListener {
            findNavController().navigate(R.id.nav_our_services)
        }

        binding.imgCamera.setOnClickListener {
            val dialog = SelectCameraGalleryDialog.newInstance()
            dialog.callbacks = object : SelectCameraGalleryDialog.Callbacks {
                override fun onCameraOptionSelected() {
                    requestCameraPermission()
                }

                override fun onGalleryOptionSelected() {
                    isUserImageSelected = true
                    isCompanyLogoSelected = false
                    requestGalleryPermission()
                }
            }
            dialog.show(parentFragmentManager, "Select Camera Gallery")
        }

        binding.imgCompanyLogo.setOnClickListener {
            isUserImageSelected = false
            isCompanyLogoSelected = true
            requestGalleryPermission()
        }

        binding.imgArrow.setOnClickListener {
            findNavController().navigate(R.id.nav_our_services)
        }

        binding.imgArrowIcon.setOnClickListener {
            findNavController().navigate(R.id.nav_social_media_links)
        }

        binding.imgSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val designation = binding.etDesignation.text.toString()
            val mobileNumber = binding.etMobileNumber.text.toString()
            val emailId = binding.etEmail.text.toString()
            val website = binding.etWebsite.text.toString()
            val location = binding.etLocation.text.toString()
            val companyName = binding.etCompanyName.text.toString()

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
                emailId.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.enter_email),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
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
                else -> {
                    showProgressBar()
                    homeViewModel.addCardRequest(
                        name,
                        designation,
                        mobileNumber,
                        emailId,
                        website,
                        location,
                        userImageBase64String,
                        companyLogoBase64String,
                        backgroundColour,
                        companyName
                    )
                }
            }
        }

        binding.etLocation.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun checkWriteStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkReadStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestLocationPermission() {
        if (checkLocationPermission()){
            getLocation()

        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if (isLocationEnabled()){
            mFusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location != null){
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude,longitude,1)
                    val address = addresses.getOrNull(0)?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    val city = addresses.getOrNull(0)?.locality
                    val state = addresses.getOrNull(0)?.adminArea
                    val country = addresses.getOrNull(0)?.countryName
                    val postalCode = addresses.getOrNull(0)?.postalCode
                    binding.etLocation.setText(address)
                } else {
                    requestNewLocationData()
                }
            }

        } else {
            Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val latitude = mLastLocation.latitude
            val longitude = mLastLocation.longitude
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude,longitude,1)
            val address = addresses.getOrNull(0)?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            binding.etLocation.setText(address)
        }
    }


    private fun requestCameraPermission() {

        if (checkCameraPermission() && checkWriteStoragePermission()) {
            cameraIntent()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), REQUEST_CODE_CAMERA
            )
        }
    }

    private fun requestGalleryPermission() {
        if (checkReadStoragePermission()) {
            galleryIntent()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {

            REQUEST_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent()
                } else {
                    Toast.makeText(requireContext(), "Allow Permission", Toast.LENGTH_LONG).show()
                }
            }

            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent()
                } else {
                    Toast.makeText(requireContext(), "Allow Permission", Toast.LENGTH_LONG).show()
                }
            }

            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    Toast.makeText(requireContext(), "Allow Permission", Toast.LENGTH_LONG).show()
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
                if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                    // Start the image capture intent to take photo
                    startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE)
                }


            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        //val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            APPNAME, ".jpg", storageDir
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
                    if (::currentPhotoPath.isInitialized){
                        val bitmap =
                            ImageCompressUtility.decodeSampledBitmapFromFile(currentPhotoPath, 300, 300)

                        withDelayOnMain(300){
                            userImageBase64String = convertBitmapToBase64(bitmap)
                        }

                    }

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
                            copyImageUriToExternalFilesDir(imageUri,photoFile)
                        }
                    } else {
                        showErrorMessage("Selected image file might be invalid or not available on device")
                    }
                }
            }
        }
    }

    private fun copyImageUriToExternalFilesDir(uri: Uri, fileName: File) {

        val inputStream = requireContext().contentResolver.openInputStream(uri)

        if (inputStream != null) {

            val file = File("$fileName")
            val fos = FileOutputStream(file)
            val bis = BufferedInputStream(inputStream)
            val bos = BufferedOutputStream(fos)
            val byteArray = ByteArray(4096)
            var bytes = bis.read(byteArray)

            while (bytes > 0) {
                bos.write(byteArray, 0, bytes)
                bos.flush()
                bytes = bis.read(byteArray)
            }

            bos.close()
            fos.close()

            when (file.length()) {
                in 0..16000000 -> {
                    val path = file.path
                    val bitmap =
                        ImageCompressUtility.decodeSampledBitmapFromFile(path, 300, 300)
                    if (isUserImageSelected){
                        userImageBase64String = convertBitmapToBase64(bitmap)
                    } else {
                        companyLogoBase64String = convertBitmapToBase64(bitmap)
                    }

                }
                else -> {
                    Toast.makeText(requireContext(),"Size is not in limit",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.card_saved_successfully),
            R.drawable.ic_success,
            onDismiss = {
                findNavController().popBackStack()
            }
        )
    }

    private fun showColourPattle(){
        ColorPickerDialog
            .Builder(requireContext())        				// Pass Activity Instance
            .setTitle("Pick Theme")           	// Default "Choose Color"
            // .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
            .setDefaultColor(R.color.orange_100)     // Pass Default Color
            .setColorListener { color, colorHex ->
                // Handle Color Selection
                // Toast.makeText(requireContext(), colorHex.toString(), Toast.LENGTH_LONG).show()
                backgroundColour= colorHex
                binding.imgCardBack.setBackgroundColor(color)
            }
            .show()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeV2Binding
        get() = FragmentHomeV2Binding::inflate
}