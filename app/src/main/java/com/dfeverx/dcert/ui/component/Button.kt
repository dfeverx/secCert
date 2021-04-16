package com.dfeverx.dcert.ui.component

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PrimaryButton(buttonText: String, onButtonClick: () -> Unit, modifier: Modifier = Modifier) {

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        ),

        shape = MaterialTheme.shapes.medium,
        onClick = { onButtonClick() }, modifier = modifier

    ) {
        Text(text = buttonText)
    }
}

@Composable
fun SecondaryButton(buttonText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        border = null, elevation = null,
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primaryVariant
        )
    ) {
        Text(text = buttonText)
    }
}
