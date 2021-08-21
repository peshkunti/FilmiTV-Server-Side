package com.peshkunti.filmitvserverside

import android.content.res.AssetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.peshkunti.filmitvserverside.ui.theme.FilmiTVServerSideTheme
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {
    @ExperimentalTime
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmiTVServerSideTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val assetManager: AssetManager = this.assets
                    LazyColumn {
                        item {
                            LoadAssets(assetManager)
                        }
                    }
                }
            }
        }
    }
}