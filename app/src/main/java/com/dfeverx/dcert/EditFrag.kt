package com.dfeverx.dcert

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.dfeverx.dcert.databinding.FragmentEditBinding
import com.scanlibrary.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileOutputStream


class EditFrag : Fragment() {
    private val TAG = "EditFrag"
    lateinit var binding: FragmentEditBinding
    val viewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.load(viewModel.imgUri)
        binding.button.setOnClickListener {

            lifecycleScope.launch(Dispatchers.Default) {
                viewModel.imgUri?.let { it1 -> convertToPdf(it1) }
            }
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(EditFragDirections.actionEditFragToStoragePermissionsFragment())
        }

    }

    fun convertToPdf(uri: Uri) {
        // Load JPG file into bitmap
//        val f: File = myJPGFile
        val bitmap = Utils.getBitmap(activity, uri)

//        val out = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 50, out)
        // Create a PdfDocument with a page of the same size as the image
        val document: PdfDocument = PdfDocument()
        val pageInfo: PdfDocument.PageInfo =
            PdfDocument.PageInfo.Builder(595, 842, 1).create()

        for (i in 1..10) {
            val page: PdfDocument.Page = document.startPage(pageInfo)


            // Draw the bitmap onto the page
            val canvas: Canvas = page.canvas
            val paint = Paint()
            paint.setColor(android.graphics.Color.parseColor("#FFFFFF"))
            canvas.drawPaint(paint)


            val mbitmap = Bitmap.createScaledBitmap(bitmap, 595, 842, false)
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100)
            val mpaint = Paint()
            mpaint.setColor(android.graphics.Color.parseColor("#FFFFFF"))
            canvas.drawBitmap(mbitmap, 0f, 0f, mpaint)
            document.finishPage(page)

        }
        // start 2nd page
        /*     val page2 = document.startPage(pageInfo)
             val canvas2: Canvas = page2.canvas
             val paint2 = Paint()
             paint2.setColor(android.graphics.Color.parseColor("#000000"))
             canvas2.drawPaint(paint2)
             document.finishPage(page2)*/

        // Write the PDF file to a file
        val directoryPath = requireActivity().getExternalFilesDir(null)?.absolutePath
        Log.d(TAG, "convertToPdf: $directoryPath")
        val fileName = "example12.pdf"
        viewModel.pdf = directoryPath + "/$fileName"
        document.writeTo(FileOutputStream("$directoryPath/$fileName"))
        document.close()
    }

    companion object {
        const val A4_WIDTH = 595
        const val A4_HEIGHT = 842
    }
}