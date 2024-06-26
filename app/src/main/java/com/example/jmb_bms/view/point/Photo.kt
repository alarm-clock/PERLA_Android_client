/**
 * @file Photo.kt
 * @author Jozef Michal Bukas <xbukas00@stud.fit.vutbr.cz,jozefmbukas@gmail.com>
 * Description: File containing photo view
 */
package com.example.jmb_bms.view.point

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jmb_bms.ui.theme.LocalTheme
import com.example.jmb_bms.ui.theme.TestTheme
import com.example.jmb_bms.view.BottomBar1
import com.example.jmb_bms.view.MenuTop1
import com.example.jmb_bms.viewModel.LiveLocationFromPhone
import com.example.jmb_bms.viewModel.LiveTime


@Composable
fun PhotoDetail(currTime: LiveTime, currLoc: LiveLocationFromPhone, uri: Uri, backHandler: () -> Unit)
{
    TestTheme {
        val scheme = LocalTheme.current
        Scaffold(
            topBar = { MenuTop1(currTime, currLoc) },
            bottomBar = {
                BottomBar1(
                    null,null, ButtonColors(Color.Red, Color.Red, Color.Red, Color.Red),backHandler){}
            }
        ){
            Column(modifier = Modifier.padding(it).background(scheme.primary)) {
                val painter = rememberAsyncImagePainter(model = uri)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp).fillMaxSize()
                )
            }
        }
    }
}
