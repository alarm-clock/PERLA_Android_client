package com.example.jmb_bms.view.point

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.jmb_bms.viewModel.LiveLocationFromLoc
import com.example.jmb_bms.viewModel.LiveTime

@Composable
fun VideoPlay(currLoc: LiveLocationFromLoc, currTime: LiveTime, uri: Uri, backHandler: () -> Unit)
{
    val ctx = LocalContext.current
    val mediaItem = MediaItem.Builder().setUri(uri).build()

    val exoPlayer = remember(ctx,mediaItem){
        ExoPlayer.Builder(ctx).build().also { exoPl ->
            exoPl.setMediaItem(mediaItem)
            exoPl.prepare()
            exoPl.playWhenReady = false
            exoPl.repeatMode = ExoPlayer.REPEAT_MODE_OFF
        }
    }

    DisposableEffect(AndroidView(factory = {
        PlayerView(it).apply {
            player = exoPlayer
        }
    })){
        onDispose { exoPlayer.release() }
    }

}