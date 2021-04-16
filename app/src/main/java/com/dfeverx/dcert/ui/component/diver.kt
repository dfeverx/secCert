package com.dfeverx.dcert.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LeftDividerWithText(title: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    ) {
        Divider(
            modifier = Modifier
                .height(24.dp)
                .width(8.dp)
        )
        Subtitle1(text = title, Modifier.padding(start = 8.dp))
    }

}
