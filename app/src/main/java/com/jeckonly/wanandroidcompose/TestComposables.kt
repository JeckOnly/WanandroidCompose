package com.jeckonly.wanandroidcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TestLazyList(list: List<String>) {

    val listState = rememberLazyListState()

    // Don't
//    val showButton = listState.firstVisibleItemIndex > 0

    // Do
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }



    println("currentRecomposeScope2 $currentRecomposeScope")
    Surface {
        println("currentRecomposeScope3 $currentRecomposeScope")
        LazyColumn(state = listState) {
            items(count = list.size) { index ->
                Text(text = list[index], modifier = Modifier.padding(20.dp))
                Divider(color = Color.Blue)
            }
        }

        AnimatedVisibility(visible = showButton) {
            println("hello")
            Button(onClick = {

            }, modifier = Modifier
                .background(color = Color.Blue)
                .padding(10.dp)){
                Text("Button")
                println("currentRecomposeScope4 $currentRecomposeScope")
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewList() {
    TestLazyList(listOf("dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg"))

    println("currentRecomposeScope1 $currentRecomposeScope")
}