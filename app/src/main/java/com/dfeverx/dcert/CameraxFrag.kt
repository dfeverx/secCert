package com.dfeverx.dcert

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dfeverx.dcert.databinding.FragmentCameraxBinding
import com.scanlibrary.ScanActivity
import com.scanlibrary.ScanConstants
import com.scanlibrary.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService


class CameraxFrag : Fragment() {
    private val TAG = "CameraxFrag"
    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    lateinit var binding: FragmentCameraxBinding

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOutputDirectory()?.let {
            Log.d("TAG", "onViewCreated: Path non null")
            outputDirectory = it
        }
        startCamera()
        binding.btnCapture.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {

        Log.d("TAG", "takePhoto: 1")
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
        Log.d("TAG", "takePhoto: 2")
        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("TAG", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    lifecycleScope.launch(Dispatchers.Default) {
                        val savedUri = Uri.fromFile(photoFile)
                        val msg = "Photo capture succeeded: $savedUri"
                        Log.d("TAG", msg)
                        try {
                         val   bitmap = Utils.getBitmap(activity, Uri.parse(savedUri.toString()))
                            //                    binding.iv.setImageBitmap(bitmap)
                            val uri = Utils.getUrix(
                                SimpleDateFormat(
                                    FILENAME_FORMAT, Locale.US
                                ).format(System.currentTimeMillis()), activity, bitmap
                            )
                            bitmap?.recycle()
                            withContext(Dispatchers.Main) {
                                findNavController().navigate(
                                    CameraxFragDirections.actionCameraxFragToScanFragment(
                                        uri.toString()
                                    )
                                )
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "onImageSaved: Exception $e")
                            return@launch
                        }

                    }


                }
            })
    }



    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            imageCapture = ImageCapture.Builder().build()
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun getOutputDirectory(): File? {
        val mediaDir = requireActivity().getExternalFilesDir(null)?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else null
    }
    /**/
    /* private fun createCaptureUseCase(): ImageCapture {
         // 2
         val imageCaptureConfig = ImageCaptureConfig.Builder()
             .apply {
                 setLensFacing(lensFacing)
                 setTargetRotation(previewView.display.rotation)
                 // 2
                 setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
             }

         return ImageCapture(imageCaptureConfig.build())
     }*/
}