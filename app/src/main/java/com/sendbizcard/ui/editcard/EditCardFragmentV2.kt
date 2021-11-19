package com.sendbizcard.ui.editcard

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.google.android.gms.location.*
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentEditCardV2Binding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.dialog.SelectCameraGalleryDialog
import com.sendbizcard.dialog.ServerErrorDialogFragment
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.ui.home.HomeViewModel
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.utils.*
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*

@AndroidEntryPoint
class EditCardFragmentV2 : BaseFragment<FragmentEditCardV2Binding>() {

    private val APPNAME = "SendBizCardApp"
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentEditCardV2Binding
    private var backgroundColour = "#ef5e42"
    private val REQUEST_CODE_CAMERA = 0x1001
    private val REQUEST_CODE_IMAGE_CAPTURE = 0x1002
    private val REQUEST_CODE_GALLERY = 0x1003
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x1004
    private val REQUEST_CODE_LOCATION = 0x1005
    private val REQUEST_CALL_PERMISSION = 0x1006
    private val IMAGE_MIME_TYPE = "image/*"
    lateinit var currentPhotoPath: String
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var isUserImageSelected = false
    private var isCompanyLogoSelected = false
    private var userImageBase64String = ""
    private var companyLogoBase64String = ""
    private var cardDetailsItem: CardDetailsItem? = null
    private var isFromPreviewCard = false
    private var isBackgroungColourChanged = false
    private var isCameraOptionSelected = false
    private var isGalleryOptionSelected = false
    var bitmap: Bitmap? = null
    var mobileNumber = ""

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

        val bundle = this.arguments
        if (bundle != null) {
            cardDetailsItem = bundle.getParcelable("cardItem")
            isFromPreviewCard = bundle.getBoolean("isFromPreviewCard", false)

            if (isFromPreviewCard) {
                binding.imgEdit.visible()
                binding.colorPalette.gone()
                binding.colorPalette.isEnabled = false
                binding.imgCamera.isEnabled = false
                binding.imgCompanyLogo.isEnabled = false
                binding.etName.isEnabled = false
                binding.etCompanyName.isEnabled = false
                binding.etDesignation.isEnabled = false
                binding.etMobileNumber.isEnabled = false
                binding.etEmail.isEnabled = false
                binding.etWebsite.isEnabled = false
                binding.etLocation.isEnabled = false
                binding.ourServicesCL.isEnabled = false
                binding.imgArrow.isEnabled = false
                binding.socialMediaCL.isEnabled = false
                binding.imgArrowIcon.isEnabled = false
                binding.imgSave.isEnabled = false
                binding.imgShare.isEnabled = false
            }

            if (isBackgroungColourChanged) {
                binding.imgCardBack.setBackgroundColor(Color.parseColor(backgroundColour))
            } else {
                backgroundColour = cardDetailsItem?.themeColor ?: "#ef5e42"
                binding.imgCardBack.setBackgroundColor(Color.parseColor(backgroundColour))
            }
            binding.imgCompanyLogo.visible()
            if (isCompanyLogoSelected) {
                bitmap?.let { binding.imgCompanyLogo.loadCompanyBitmap(it) }
            } else {
                val companyLogoUrl = cardDetailsItem?.companyLogo ?: ""
                if (companyLogoUrl.isNotEmpty()) {
                    binding.imgCompanyLogo.loadImages("https://xapi.sendbusinesscard.com/storage/$companyLogoUrl")
                } else {
                    binding.imgCompanyLogo.setImageResource(R.drawable.ic_company_logo)
                }
            }


            binding.etName.setText(cardDetailsItem?.name ?: "")

            binding.etCompanyName.setText(cardDetailsItem?.companyName ?: "")

            binding.etDesignation.setText(cardDetailsItem?.designation ?: "")

            binding.etMobileNumber.setText(cardDetailsItem?.contactNo ?: "")

            binding.etEmail.setText(cardDetailsItem?.email ?: "")

            binding.etWebsite.setText(cardDetailsItem?.website ?: "")

            binding.etLocation.setText(cardDetailsItem?.location ?: "")

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

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
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
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.error),
            errorMessage, "", R.drawable.ic_icon_error
        )
        fragment.show(parentFragmentManager, "HomeFragment")
    }

    private fun checkCallPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun makeCall() {
        val intent = Intent(Intent.ACTION_DIAL);
        intent.data = Uri.parse("tel:$mobileNumber");
        startActivity(Intent.createChooser(intent, "Complete action using?"));
    }


    private fun openEmailClient(emailId: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailId))
                putExtra(Intent.EXTRA_SUBJECT, "Request for subscription of MI Membership in bulk account")
            }
            startActivity(intent)
        } catch (exception: Exception) {
            Toast.makeText(activity, "There are no email client installed on your device.", Toast.LENGTH_SHORT).show()
        }

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
            isFromPreviewCard = false
            binding.colorPalette.visible()
        }

        binding.imgShare.setOnClickListener {
            shareApp(requireContext(), "")
        }

        binding.imgMobileNumber.setOnClickListener {
            mobileNumber = binding.etMobileNumber.text.toString()
            if (mobileNumber.length == 10) {
                if (checkCallPermission()) {
                    makeCall()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
                }
            }
        }

        binding.imgEmail.setOnClickListener {
            val emailId = cardDetailsItem?.email ?: ""
            if (emailId.isNotEmpty()) {
                openEmailClient(emailId)
            }
        }

        binding.etMobileNumber.setOnClickListener {
            mobileNumber = binding.etMobileNumber.text.toString()
            if (mobileNumber.length == 10) {
                if (checkCallPermission()) {
                    makeCall()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
                }
            }
        }

        binding.etEmail.setOnClickListener {
            val emailId = cardDetailsItem?.email ?: ""
            if (emailId.isNotEmpty()) {
                openEmailClient(emailId)
            }
        }

        binding.etWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cardDetailsItem?.website))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                // Log.d(TAG, "onClick: inTryBrowser")
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                //  Log.e(TAG, "onClick: in inCatchBrowser", ex)
                intent.setPackage(null)
                startActivity(Intent.createChooser(intent, "Select Browser"))
            }
        }

        binding.etLocation.setOnClickListener {
            val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(cardDetailsItem?.location))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        binding.imgWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cardDetailsItem?.website))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                // Log.d(TAG, "onClick: inTryBrowser")
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                //  Log.e(TAG, "onClick: in inCatchBrowser", ex)
                intent.setPackage(null)
                startActivity(Intent.createChooser(intent, "Select Browser"))
            }
        }

        binding.imgLocation.setOnClickListener {
            val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(cardDetailsItem?.location))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }


        binding.colorPalette.setOnClickListener {
            showColourPattle()
        }

        binding.ourServicesCL.setOnClickListener {
            findNavController().navigate(
                R.id.nav_our_services,
                bundleOf("services" to cardDetailsItem?.services, "isFromEdit" to true,"isFromPreviewCard" to isFromPreviewCard)
            )
        }

        binding.socialMediaCL.setOnClickListener {
            // findNavController().navigate(R.id.nav_social_media_links)
            findNavController().navigate(
                R.id.nav_social_media_links,
                bundleOf("socialLinks" to cardDetailsItem?.socialLinks, "isFromEdit" to true,"isFromPreviewCard" to isFromPreviewCard)
            )

        }

        binding.imgCamera.setOnClickListener {
            val dialog = SelectCameraGalleryDialog.newInstance()
            dialog.callbacks = object : SelectCameraGalleryDialog.Callbacks {
                override fun onCameraOptionSelected() {
                    requestCameraPermission()
                    isCameraOptionSelected = true
                    isGalleryOptionSelected = false
                }

                override fun onGalleryOptionSelected() {
                    isUserImageSelected = true
                    isCompanyLogoSelected = false
                    isCameraOptionSelected = false
                    isGalleryOptionSelected = true
                    requestGalleryPermission()
                }
            }
            dialog.show(parentFragmentManager, "Select Camera Gallery")
        }

        binding.tvCompanyLogo.setOnClickListener {
            isUserImageSelected = false
            isCompanyLogoSelected = true
            isGalleryOptionSelected = true
            requestGalleryPermission()
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
                    cardDetailsItem?.id?.let { it1 ->
                        homeViewModel.editCardRequest(
                            name,
                            designation,
                            mobileNumber,
                            emailId,
                            website,
                            location,
                            userImageBase64String,
                            companyLogoBase64String,
                            backgroundColour,
                            companyName,
                            it1
                        )
                    }
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
        if (checkLocationPermission()) {
            getLocation()

        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (isLocationEnabled()) {
            mFusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val address = addresses.getOrNull(0)
                        ?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
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
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses.getOrNull(0)
                ?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
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

            REQUEST_CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == REQUEST_CALL_PERMISSION) {
                    makeCall()
                } else {
                    Toast.makeText(requireContext(), "Please allow permission to continue.", Toast.LENGTH_LONG).show()
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


    private fun startCrop(uri: Uri) {
        val destinationFileName = "SampleCropImage.jpg"
        val uCrop =
            UCrop.of(uri, Uri.fromFile(File(requireContext().cacheDir, destinationFileName)))
        uCrop.start(requireContext(), this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_IMAGE_CAPTURE -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    if (::currentPhotoPath.isInitialized) {
                        val bitmap =
                            ImageCompressUtility.decodeSampledBitmapFromFile(
                                currentPhotoPath,
                                300,
                                300
                            )

                        withDelayOnMain(300) {
                            userImageBase64String = convertBitmapToBase64(bitmap)
                        }

                    }

                }
            }
            REQUEST_CODE_GALLERY -> {
                val imageUri = data?.data
                if (imageUri != null) {
                    startCrop(imageUri)
                } else {
                    showErrorMessage("Selected image file might be invalid or not available on device")
                }
            }
            UCrop.REQUEST_CROP -> {
                val uri = UCrop.getOutput(data!!)
                if (uri != null) {
                    if (isCameraOptionSelected) {
                        bitmap =
                            ImageCompressUtility.decodeSampledBitmapFromFile(
                                File(uri.path!!).absolutePath,
                                300,
                                300
                            )

                        bitmap?.let {
                            withDelayOnMain(300) {

                                userImageBase64String = convertBitmapToBase64(it)
                            }
                        }

                    }

                    if (isGalleryOptionSelected) {
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            null
                        }
                        // Continue only if the File was successfully created
                        photoFile?.also {
                            copyImageUriToExternalFilesDir(uri, photoFile)
                        }
                    }
                } else {
                    showErrorMessage("Selected image file might be invalid or not available on device")
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
                    bitmap =
                        ImageCompressUtility.decodeSampledBitmapFromFile(path, 300, 300)
                    bitmap?.let {
                        if (isUserImageSelected) {
                            userImageBase64String = convertBitmapToBase64(it)
                        } else {
                            binding.imgCompanyLogo.visible()
                            binding.imgCompanyLogo.loadCompanyBitmap(it)
                            companyLogoBase64String = convertBitmapToBase64(it)
                            companyLogoBase64String = convertBitmapToBase64(it)
                        }
                    }
                }
                else -> {
                    Toast.makeText(requireContext(), "Size is not in limit", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
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

    private fun showColourPattle() {
        ColorPickerDialog
            .Builder(requireContext())                        // Pass Activity Instance
            .setTitle("Pick Theme")            // Default "Choose Color"
            // .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
            .setDefaultColor(R.color.orange_100)     // Pass Default Color
            .setColorListener { color, colorHex ->
                // Handle Color Selection
                // Toast.makeText(requireContext(), colorHex.toString(), Toast.LENGTH_LONG).show()
                backgroundColour = colorHex
                binding.imgCardBack.setBackgroundColor(color)
                isBackgroungColourChanged = true
            }
            .show()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditCardV2Binding
        get() = FragmentEditCardV2Binding::inflate
}