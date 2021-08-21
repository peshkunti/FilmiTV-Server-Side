package com.peshkunti.filmitvserverside

import android.content.res.AssetManager
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlin.time.ExperimentalTime

@ExperimentalAnimationApi
@ExperimentalTime
@Composable
fun LoadAssets(assetManager: AssetManager) {

    fun readHtmlAsset(assetManager: AssetManager, fileName: String): Result<String> =
        runCatching { assetManager.open(fileName).bufferedReader().use { it.readText() } }

    Column {
        val events = assetManager
            .list("")!!
            .filter { it.takeLast(5) == ".html" }
            .map { fileName: String ->
                readHtmlAsset(assetManager, fileName)
                    .apply {
                        when {
                            isSuccess -> Text(
                                text = "$fileName loaded successfully",
                                color = MaterialTheme.colors.primary
                            )
                            isFailure -> Text(
                                text = "$fileName fail",
                                color = MaterialTheme.colors.error
                            )
                        }
                    }
            }
    }
}