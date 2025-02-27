package com.example.ktsample.ui.compose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

class ComposeUI: ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
        }
    }

    @Composable
    fun GreenBoxWithStringIdExample() {
        val constraintSet = ConstraintSet {
            // 使用字符串 ID 创建引用
            val greenBox = createRefFor("greenBox")

            constrain(greenBox) {
                // 约束绿框在布局的中心
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }
        }

        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
            constraintSet = constraintSet
        ) {
            // 使用字符串 ID 布局绿框
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .size(100.dp)
                    .layoutId("greenBox")
            )
        }
    }

    @Preview
    @Composable
    fun GreenBoxWithStringIdExamplePreview() {
        GreenBoxWithStringIdExample()
    }
}