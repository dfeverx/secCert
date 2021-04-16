package com.dfeverx.dcert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCheckFrag : Fragment() {


    private val TAG = "MainCheckFrag"
    private val viewModel: ActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state().observe(viewLifecycleOwner, {


            when (it) {

                ActivityViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    findNavController()
                        .navigate(
                            MainCheckFragDirections.actionMainCheckFragToAuthFrag()

                        )
                }
                ActivityViewModel.AuthenticationState.AUTHENTICATED -> {
                    findNavController()
                        .navigate(
                            MainCheckFragDirections
                                .actionMainCheckFragToDashboardFrag()
                        )
                }

            }
        })

    }


}