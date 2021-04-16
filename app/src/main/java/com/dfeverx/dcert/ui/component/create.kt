package com.dfeverx.dcert.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun Create(toPermission:()->(Unit)) {

    Row(
        modifier = Modifier
            .border(2.dp, SolidColor(Color.LightGray), RectangleShape)
            .padding(8.dp),

        ) {

        MyCard(modifier = Modifier.weight(1f), Icons.Outlined.Add, "Camera"){
            toPermission()
        }
        MyCard(modifier = Modifier.weight(1f), Icons.Outlined.Place, "Gallery")
        MyCard(modifier = Modifier.weight(1f), Icons.Outlined.Person, "Sample")

    }

}
