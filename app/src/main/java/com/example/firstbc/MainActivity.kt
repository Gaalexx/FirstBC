package com.example.firstbc

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import android.content.res.Configuration
import android.widget.Button
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.firstbc.ui.theme.FirstBCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoxSet()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BoxSet() {
    val boxesAmount = rememberSaveable { mutableStateOf(0) }
    val scrollState = rememberLazyGridState()
    val count : Int

    val configuration = LocalConfiguration.current
    val orientation = remember{ mutableStateOf(configuration.orientation) }
    when (orientation.value) {
        Configuration.ORIENTATION_LANDSCAPE -> count = 4
        Configuration.ORIENTATION_PORTRAIT -> count = 3
        else -> count = 3
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        items(boxesAmount.value, key = { it }) { index ->
            BoxItem(index)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { boxesAmount.value++ },
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            Text(text = stringResource(R.string.addBoxButton))
        }
    }
}

@Composable
fun BoxItem(index: Int){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                if (index % 2 == 0) Color.Red else Color.Blue,
                RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Text(
            text = "Box $index",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
