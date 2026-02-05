package com.altmanifest.secondtake.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFF00D1FF)
val PrimaryFontShadowColor = Color(0xFF005466)
val PrimaryDisabledColor = Color(0xFF808080)
val PrimaryDisabledFontShadowColor = Color(0xFF333333)
val IMDBColor = Color(0xFFF4C418)
val IMDBFontShadowColor = Color(0xFF664E00)
val FilmwebColor = Color(0xFFFFC300)
val FilmwebFontShadowColor = Color(0xFF664E00)
val OnlyfilmsColor = Color(0xFF00AFF0)
val OnlyfilmsFontShadowColor = Color(0xFF004761)
val DangerColor = Color(0xFFFF4B4B)
val TextColor = Color(0xFFEBEBEB)
val SurfaceColor = Color(0xFF1E1E1E)
val SurfaceBorderColor = Color(0xFF383838)
val BackgroundColor = Color(0xFF0D0D0D)

val PrimaryButtonColor = ButtonColors(
    containerColor = PrimaryColor,
    contentColor = PrimaryFontShadowColor,
    disabledContainerColor = PrimaryDisabledColor,
    disabledContentColor = PrimaryDisabledFontShadowColor)

val IMDBButtonColor = ButtonColors(
    containerColor = IMDBColor,
    contentColor = IMDBFontShadowColor,
    disabledContainerColor = PrimaryDisabledColor,
    disabledContentColor = PrimaryDisabledFontShadowColor)

val FilmWebButtonColor = ButtonColors(
    containerColor = FilmwebColor,
    contentColor = FilmwebFontShadowColor,
    disabledContainerColor = PrimaryDisabledColor,
    disabledContentColor = PrimaryDisabledFontShadowColor)

val OnlyfilmsButtonColor = ButtonColors(
    containerColor = OnlyfilmsColor,
    contentColor = OnlyfilmsFontShadowColor,
    disabledContainerColor = PrimaryDisabledColor,
    disabledContentColor = PrimaryDisabledFontShadowColor)