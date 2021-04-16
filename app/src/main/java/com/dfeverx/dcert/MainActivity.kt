package com.dfeverx.dcert

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.scanlibrary.ScanActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ScanActivity() {
    private val TAG = "MainActivity"

    val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBitmapSelect(uri: Uri?) {
//        super.onBitmapSelect(uri)
        Log.d(TAG, "onBitmapSelect: $uri")

    }

    override fun onScanFinish(uri: Uri?) {
//        super.onScanFinish(uri)
        Log.d(TAG, "onScanFinish: $uri")
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.imgUri = uri
            findNavController(R.id.host_fragment).navigate(R.id.editFrag)
        }
    }

    companion object {
        /*    init {
                System.loadLibrary("opencv_java3")
                System.loadLibrary("Scanner")
            }*/
    }
}


@Composable
fun ContainerView() {
    /*AndroidViewBinding(ContainerBinding::inflate, modifier = Modifier.fillMaxSize()) {

    }*/
}