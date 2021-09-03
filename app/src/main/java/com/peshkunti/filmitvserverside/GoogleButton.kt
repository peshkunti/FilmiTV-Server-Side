package com.peshkunti.filmitvserverside

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun GoogleButton(onClicked: suspend () -> Boolean) {
    val text = "Sign In with Google"
    val loadingText = "Sign in progress ..."
    val icon: Int = R.drawable.ic_google_logo
    val shape: Shape = MaterialTheme.shapes.medium
    val progressIndicatorColor: Color = MaterialTheme.colors.primary
    val borderColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
    val composableScope = rememberCoroutineScope()
    val surface = MaterialTheme.colors.surface
    var clicked by remember { mutableStateOf(false) }

    Surface(
        onClick = { clicked = true },
        shape = shape,
        color = surface,
        contentColor = if (clicked) MaterialTheme.colors.onSurface.copy(alpha = 0.2f) else MaterialTheme.colors.onSurface,
        elevation = 1.dp,
        border = BorderStroke(width = 1.dp, color = borderColor),
        enabled = !clicked,
    ) {
        if (clicked) composableScope.launch { clicked = onClicked() }

        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))
            if (clicked) {
                Text(text = loadingText)
                Spacer(modifier = Modifier.width(16.dp))

                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            } else
                Text(text = text)
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun GoogleButtonPreview() {
    GoogleButton { false }
}
