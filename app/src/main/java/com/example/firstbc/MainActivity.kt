package com.example.firstbc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import android.content.res.Configuration
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource

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
    val boxItems = rememberSaveable { mutableStateOf(listOf<BoxItemData>()) }
    val scrollState = rememberLazyGridState()
    val count : Int

    val configuration = LocalConfiguration.current
    val orientation = remember{ mutableIntStateOf(configuration.orientation) }
    count = when (orientation.intValue) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        Configuration.ORIENTATION_PORTRAIT -> 3
        else -> 3
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        items(
            count = boxItems.value.size,
            key = {index -> boxItems.value[index].id}
        ) { index ->
            BoxItem(boxItems.value[index].index)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { boxItems.value = boxItems.value + BoxItemData(
                id = boxItems.value.size,
                index = boxItems.value.size
            )
                      },
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
            )
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Text(
            text = stringResource(R.string.insideBox, index),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

data class BoxItemData(
    val id: Int,
    val index: Int
)
