package com.dfeverx.dcert.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {
    Surface(color = MaterialTheme.colors.primary) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
        ) {
            Text(text = "Home")
        }
    }
}



