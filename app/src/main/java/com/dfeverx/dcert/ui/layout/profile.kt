package com.dfeverx.dcert.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate

@Composable
fun Profile(navController: NavController) {
    Surface(color = MaterialTheme.colors.primary) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(onClick = { navController.navigate("main") }) {
                Text(text = "Pro")
            }
        }
    }
}



