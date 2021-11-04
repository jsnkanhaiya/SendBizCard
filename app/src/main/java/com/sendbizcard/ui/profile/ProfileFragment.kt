package com.sendbizcard.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentProfileBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.dialog.SelectCameraGalleryDialog
import com.sendbizcard.dialog.ServerErrorDialogFragment
import com.sendbizcard.models.response.UserProfileResponse
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.*


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(){

    private val APPNAME = "SendBizCardApp"
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    private val REQUEST_CODE_CAMERA = 0x1001
    private val REQUEST_CODE_IMAGE_CAPTURE = 0x1002
    private val REQUEST_CODE_GALLERY = 0x1003
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x1004

    private val IMAGE_MIME_TYPE = "image/*"
    lateinit var currentPhotoPath: String
    private var userImageBase64String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setupObservers()
        initOnClicks()
        showProgressBar()
        profileViewModel.getUserProfileData()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        profileViewModel.userProfileResponse.observe(this) {
            hideProgressBar()
            setUserData(it)
        }

        profileViewModel.updateUserProfileResponse.observe(this)  {
            hideProgressBar()
            showSuccessDialog()
        }

        profileViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        profileViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        profileViewModel.showServerError.observe(this) { serverError ->
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
                profileViewModel.clearAllData()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        fragment.show(parentFragmentManager, "ProfileFragment")
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"",R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"ProfileFragment")
    }

    private fun initOnClicks() {

        binding.imgCamera.setOnClickListener {
            val dialog = SelectCameraGalleryDialog.newInstance()
            dialog.callbacks = object : SelectCameraGalleryDialog.Callbacks {
                override fun onCameraOptionSelected() {
                    requestCameraPermission()
                }

                override fun onGalleryOptionSelected() {
                    requestGalleryPermission()
                }
            }
            dialog.show(parentFragmentManager, "Select Camera Gallery")
        }

        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgot_password, bundleOf("isChangepassword" to true),
                getDefaultNavigationAnimation())
        }

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()
            val designation = binding.etDesignation.text.toString()
            val email = binding.etEmail.text.toString()
            val mobile = binding.etMobileNumber.text.toString()
            val website = binding.etWebsite.text.toString()

            if (profileViewModel.isValidUserProfileData(name,mobile,email,website,designation)){
                showProgressBar()
                profileViewModel.updateUserData(name,mobile,email,website,designation,userImageBase64String)
            }

        }


        binding.imgEdit.setOnClickListener {
            binding.etEmail.isEnabled =true
            binding.etDesignation.isEnabled =true
            binding.etName.isEnabled =true
            binding.etMobileNumber.isEnabled =true
            binding.etWebsite.isEnabled =true
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
                           binding.imgUser.loadBitmap(bitmap)
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
                    binding.imgUser.loadBitmap(bitmap)
                    userImageBase64String = convertBitmapToBase64(bitmap)

                }
                else -> {
                    Toast.makeText(requireContext(),"Size is not in limit", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUserData(userProfileResponse: UserProfileResponse) {
         binding.etName.setText(userProfileResponse.user?.name)
         binding.etMobileNumber.setText(userProfileResponse.user?.contact)
         binding.etWebsite.setText(userProfileResponse.user?.website)
         binding.etDesignation.setText(userProfileResponse.user?.designation)
         binding.etEmail.setText(userProfileResponse.user?.email)
        binding.imgUser.loadCircleImages(AppConstants.IMAGE_BASE_URL+userProfileResponse.user?.userImg)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        hideProgressBar()
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.update_profile_successfully),
            R.drawable.ic_success_tick,
            onDismiss = {
                if (fragmentManager != null) {
                    findNavController().navigate(R.id.nav_home, null, getDefaultNavigationAnimation())
                }
            }
        )
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
}