package com.dfeverx.dcert.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dfeverx.dcert.R

val Bloom = FontFamily(
    Font(
        resId = R.font.nunito_sans_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.nunito_sans_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.nunito_sans_light,
        weight = FontWeight.Light
    )

)

// Set of Material typography styles to start with
val typography = Typography(
    h1 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = .15.sp
    ),
    h6 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Bold,
        fontSize = 8.sp,
        letterSpacing = .15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
    ),
    body2 = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        letterSpacing = 0.sp
    ),
    button = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.sp
    ),
    caption = TextStyle(
        fontFamily = Bloom,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 0.sp
    )

)
