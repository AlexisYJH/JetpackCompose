package com.example.theming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theming.data.Post
import com.example.theming.data.PostRepo
import com.example.theming.ui.theme.JetnewsTheme
import java.util.*

/**
 * @author AlexisYin
 */
@Composable
fun Home() {
    val featured = remember {
        PostRepo.getFeaturedPost()
    }
    val posts = remember {
        PostRepo.getPosts()
    }
    JetnewsTheme {
        Scaffold(topBar = { AppBar() }) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item {
                    Header(stringResource(id = R.string.top))
                }
                item {
                    PostCardTop(
                        post = featured
                    )
                }
                item {
                    Header(stringResource(id = R.string.popular))
                }
                items(posts) { post ->
                    PostItem(post = post)
                    Divider(startIndent = 72.dp)
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(post: Post, modifier: Modifier = Modifier) {
    ListItem(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable(onClick = { })
                .padding(16.dp)
        ) {
            PostImage(post, Modifier.padding(end = 16.dp))
            Column(modifier = Modifier.weight(1f)) {
                PostTitle(post)
                AuthorAndReadTime(post)
            }
        }
    }
}

@Composable
fun PostImage(post: Post, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(post.imageThumbId),
        contentDescription = null, // decorative
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(post: Post) {
    Text(post.title, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun AuthorAndReadTime(
    post: Post,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val textStyle = MaterialTheme.typography.body2
            Text(
                text = post.metadata.author.name,
                style = textStyle
            )
            Text(
                text = " - ${post.metadata.readTimeMinutes} min read",
                style = textStyle
            )
        }
    }
}

@Composable
fun PostCardTop(post: Post, modifier: Modifier = Modifier) {
    // TUTORIAL CONTENT STARTS HERE
    val typography = MaterialTheme.typography
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        val imageModifier = Modifier
            .heightIn(min = 180.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
        Image(
            painter = painterResource(post.imageId),
            contentDescription = null, // decorative
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = post.title,
            style = typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = post.metadata.author.name,
            style = typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "${post.metadata.date} - ${post.metadata.readTimeMinutes} min read",
                style = typography.subtitle2
            )
        }
        postTag(post)
    }
}

@Composable
fun postTag(post: Post, modifier: Modifier = Modifier) {
    val tagDivider = "●"
    val text = buildAnnotatedString {
        withStyle(SpanStyle(Color.Red)) {
            append("Red text\n")
        }
        withStyle(SpanStyle(fontSize = 24.sp)) {
            append("Large text\n")
        }
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )
        post.tags.forEachIndexed { index, tag ->
            if(index != 0) {
                append(tagDivider)
            }
            withStyle(tagStyle) {
                append("${tag.uppercase(Locale.getDefault())}")
            }
        }
    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
        Text(text = text, modifier = modifier, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Palette,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.app_title))
        },
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary) {
        Text(
            text = text, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

