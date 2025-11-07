package com.example.firstbc

import android.annotation.SuppressLint
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import java.util.LinkedList

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

    val removedBoxItems = rememberSaveable { mutableStateOf(LinkedList<BoxItemData>()) }

    val scrollState = rememberLazyGridState()
    val count: Int

    val configuration = LocalConfiguration.current
    val orientation = remember { mutableIntStateOf(configuration.orientation) }
    count = when (orientation.intValue) {
        Configuration.ORIENTATION_LANDSCAPE -> IF_HORIZONTAL_ORIENTATION
        Configuration.ORIENTATION_PORTRAIT -> IF_VERTICAL_ORIENTATION
        else -> IF_DEFAULT_CASE
    }

    fun clickAtBox(index: Int) {
        val clickedItem = boxItems.value.find { it.index == index }
        clickedItem?.let { item ->
            removedBoxItems.value.add(item)

            boxItems.value = boxItems.value.filter { it.id != item.id }

            removedBoxItems.value = LinkedList(removedBoxItems.value)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count),
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(
                count = boxItems.value.size,
                key = { index -> boxItems.value[index].id }
            ) { index ->
                BoxItem(boxItems.value[index].index,
                    clickAtBox = {clickedIndex -> clickAtBox(clickedIndex)})
            }
        }
        Button(
            onClick = {
                if(removedBoxItems.value.isEmpty()){
                    boxItems.value =  boxItems.value + BoxItemData(
                        id = boxItems.value.size,
                        index = boxItems.value.size
                    )
                }
                else {
                    boxItems.value = boxItems.value.toMutableList().apply {
                        val localIndex = boxItems.value.indexOfFirst { itemData -> itemData.index > removedBoxItems.value.first().index }
                        if(localIndex == -1){
                            add(BoxItemData(id = removedBoxItems.value.first().id, index = removedBoxItems.value.first().index))
                        }
                        else{
                            add(localIndex, BoxItemData(
                                id = removedBoxItems.value.first().id,
                                index = removedBoxItems.value.first().index
                            ))
                        }
                    }
                    removedBoxItems.value.removeFirst()
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            Text(text = stringResource(R.string.addBoxButton))
        }
    }

}

@Composable
fun BoxItem(index: Int, clickAtBox: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                if (index % 2 == 0) Color.Red else Color.Blue,
            )
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable( onClick = {clickAtBox(index)})
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
