package com.dfeverx.dcert.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage

@Composable
fun Wish() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { /*TODO*/ }) {
                CoilImage(
                    data = "https://picsum.photos/300/300",
                    contentDescription = "My content description",
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    },
                    modifier = Modifier
                        .size(40.dp)

                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {}, modifier = Modifier
//                    .size(40.dp)
//                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Subtitle1(text = "Hi John doe", Modifier.padding(top = 8.dp))
        H6(text = "Good Morning")
    }
}


