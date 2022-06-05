package com.jeckonly.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    Surface {
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
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewList() {
    TestLazyList(listOf("dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfdf", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg", "dfsdf", "gsdgsg"))
}