package com.dfeverx.dcert.ui.crop

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dfeverx.dcert.MainActivity
import com.dfeverx.dcert.databinding.FragmentCropBinding
import com.scanlibrary.IScanner
import com.scanlibrary.ScanConstants
import com.scanlibrary.SingleButtonDialogFragment
import com.scanlibrary.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class CropFragment : Fragment() {
    lateinit var binding: FragmentCropBinding
    val cropperVM: CropperVM by viewModels()
var rotation = 0f

    private var scanner: IScanner? = null
    private var original: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button3.setOnClickListener {
            rotation += 90
            binding.frameLayout2.rotation = rotation
        }
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity !is IScanner) {
            throw ClassCastException("Activity must implement IScanner")
        }
        scanner = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCropBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.scanButton.setOnClickListener(ScanButtonClickListener())
        binding.sourceFrame.post {
            original = bitmap
            if (original != null) {
                setBitmap(original!!)
            }
        }
    }

    private val bitmap: Bitmap?
        get() {
            val uri = Uri.parse(cropperVM.uriString())
            try {
                return Utils.getBitmap(activity, uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    private val uri: Uri?
        get() = requireArguments().getParcelable(ScanConstants.SELECTED_BITMAP)

    private fun setBitmap(original: Bitmap) {
        val scaledBitmap =
            scaledBitmap(original, binding.sourceFrame.width, binding.sourceFrame.height)
        binding.sourceImageView.setImageBitmap(scaledBitmap)
        val tempBitmap = (binding.sourceImageView.drawable as BitmapDrawable).bitmap
        val pointFs = getEdgePoints(tempBitmap)
        binding.polygonView.points = pointFs
        binding.polygonView.visibility = View.VISIBLE
        val padding = resources.getDimension(com.scanlibrary.R.dimen.scanPadding)
            .toInt()
        val layoutParams = FrameLayout.LayoutParams(
            tempBitmap.width + 2 * padding,
            tempBitmap.height + 2 * padding
        )
        layoutParams.gravity = Gravity.CENTER
        binding.polygonView.layoutParams = layoutParams
    }

    private fun getEdgePoints(tempBitmap: Bitmap): Map<Int, PointF> {
        val pointFs = getContourEdgePoints(tempBitmap)
        return orderedValidEdgePoints(tempBitmap, pointFs)
    }

    private fun getContourEdgePoints(tempBitmap: Bitmap): List<PointF> {
        val points = (activity as MainActivity?)!!.getPoints(tempBitmap)
        val x1 = points[0]
        val x2 = points[1]
        val x3 = points[2]
        val x4 = points[3]
        val y1 = points[4]
        val y2 = points[5]
        val y3 = points[6]
        val y4 = points[7]
        val pointFs: MutableList<PointF> = ArrayList()
        pointFs.add(PointF(x1, y1))
        pointFs.add(PointF(x2, y2))
        pointFs.add(PointF(x3, y3))
        pointFs.add(PointF(x4, y4))
        return pointFs
    }

    private fun getOutlinePoints(tempBitmap: Bitmap): Map<Int, PointF> {
        val outlinePoints: MutableMap<Int, PointF> = HashMap()
        outlinePoints[0] = PointF(0F, 0F)
        outlinePoints[1] = PointF(tempBitmap.width.toFloat(), 0F)
        outlinePoints[2] = PointF(0F, tempBitmap.height.toFloat())
        outlinePoints[3] = PointF(
            tempBitmap.width.toFloat(), tempBitmap.height.toFloat()
        )
        return outlinePoints
    }

    private fun orderedValidEdgePoints(
        tempBitmap: Bitmap,
        pointFs: List<PointF>
    ): Map<Int, PointF> {
        var orderedPoints = binding.polygonView.getOrderedPoints(pointFs)
        if (!binding.polygonView.isValidShape(orderedPoints)) {
            orderedPoints = getOutlinePoints(tempBitmap)
        }
        return orderedPoints
    }

    private inner class ScanButtonClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            val points = binding.polygonView.points
            if (isScanPointsValid(points)) {
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        //Loading anim
                    }
                    withContext(Dispatchers.Default) {
                        val bitmap = getScannedBitmap(original, points)
                        val uri = Utils.getUrix(cropperVM.uriString(), activity, bitmap)
                        scanner!!.onScanFinish(uri)
                        bitmap.recycle()
                        withContext(Dispatchers.Main) {
                            //Hide loader
                        }
                    }


                }
            } else {
                showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        val fragment = SingleButtonDialogFragment(
            com.scanlibrary.R.string.ok,
            getString(com.scanlibrary.R.string.cantCrop),
            "Error",
            true
        )
        val fm = requireActivity().fragmentManager
        fragment.show(fm, SingleButtonDialogFragment::class.java.toString())
    }

    private fun isScanPointsValid(points: Map<Int, PointF>): Boolean {
        return points.size == 4
    }

    private fun scaledBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        val m = Matrix()
        m.setRectToRect(
            RectF(0F, 0F, bitmap.width.toFloat(), bitmap.height.toFloat()),
            RectF(0F, 0F, width.toFloat(), height.toFloat()),
            Matrix.ScaleToFit.CENTER
        )
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
    }

    private fun getScannedBitmap(
        original: Bitmap?,
        points: Map<Int, PointF>
    ): Bitmap {
        val width = original!!.width
        val height = original.height
        val xRatio = original.width.toFloat() / binding.sourceImageView.width
        val yRatio = original.height.toFloat() / binding.sourceImageView.height
        val x1 = points[0]!!.x * xRatio
        val x2 = points[1]!!.x * xRatio
        val x3 = points[2]!!.x * xRatio
        val x4 = points[3]!!.x * xRatio
        val y1 = points[0]!!.y * yRatio
        val y2 = points[1]!!.y * yRatio
        val y3 = points[2]!!.y * yRatio
        val y4 = points[3]!!.y * yRatio
        Log.d("", "POints($x1,$y1)($x2,$y2)($x3,$y3)($x4,$y4)")
        return (activity as MainActivity?)!!.getScannedBitmap(
            original,
            x1,
            y1,
            x2,
            y2,
            x3,
            y3,
            x4,
            y4
        )
    }


    protected fun showProgressDialog(message: String?) {
        /* Toast.makeText(requireContext(), "loading", Toast.LENGTH_LONG).show();
        progressDialogFragment = new ProgressDialogFragment(message);
        FragmentManager fm = getChildFragmentManager();
        progressDialogFragment.show(fm, ProgressDialogFragment.class.toString());*/
    }

    protected fun dismissDialog() {
//        progressDialogFragment!!.dismissAllowingStateLoss()
    }
}