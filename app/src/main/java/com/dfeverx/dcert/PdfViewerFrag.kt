package com.dfeverx.dcert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dfeverx.dcert.databinding.FragmentPdfViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PdfViewerFrag : Fragment() {
    lateinit var binding: FragmentPdfViewerBinding
    val viewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}