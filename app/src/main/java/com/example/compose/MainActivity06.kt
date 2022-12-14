package com.example.compose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme
import androidx.compose.animation.animateColorAsState

/**
 * 动画
 */
class MainActivity06 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }

    @Composable
    fun MessageCard(msg: Message) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_photo),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember {
                mutableStateOf(false)
            }

            //动画
            val surfaceColor by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
            Column (modifier = Modifier.clickable { isExpanded = !isExpanded }){
                Text(text = msg.author, color = MaterialTheme.colors.secondaryVariant)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2,
                        maxLines = if(isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(list: List<Message>) {
        LazyColumn {
            items(list) { msg ->
                MessageCard(msg)
            }
        }
    }
}