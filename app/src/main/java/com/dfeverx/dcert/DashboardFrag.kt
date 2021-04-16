package com.dfeverx.dcert

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dfeverx.dcert.ui.component.Create
import com.dfeverx.dcert.ui.component.LeftDividerWithText
import com.dfeverx.dcert.ui.component.Wish

class DashboardFrag : Fragment() {
    var onBackPressedCallback: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 40.dp)
                ) {
//                    item { Spacer(modifier = Modifier.height(40.dp)) }
                    item { Wish() }
                    item {
                        LeftDividerWithText(title = "Create")
                        Create() {
                            Log.d("TAG", "onCreateView: click")
                            findNavController().navigate(DashboardFragDirections.actionDashboardFragToPermissionsFragment())

                        }
                    }
                    item {
                        LeftDividerWithText(title = "Recent photos")
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }
}