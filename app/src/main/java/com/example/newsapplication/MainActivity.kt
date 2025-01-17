package com.example.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.newsapplication.data.Article
import com.example.newsapplication.viewModel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(topBar = { AppBar() }, bottomBar = { NavigationBar() }) { innerPadding ->
        MainContent(innerPadding);
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    Surface(
        shape = RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp), // Circular bottom corners
        color = Color(0xFF1A3DF6), // App bar background color
        contentColor = MaterialTheme.colorScheme.primary // Title color
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent, // Use Surface color
                titleContentColor = Color.White,
            ), title = {
                // Custom Padding in App Bar is I guess 16.dp (maybe)
                Row(
                    verticalAlignment = Alignment.CenterVertically // Align image and text vertically
                ) {
                    Icon(
                        Icons.Filled.Menu, contentDescription = "Localized description",
                        Modifier.padding(end = 10.dp)
                    )

                    Text(
                        text = "News",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White // Ensure the text color is white
                    )
                }
            }, scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        )
    }
}


// How to add Image in this item

@Composable
fun NavigationBar() {
    Surface() {
        BottomAppBar(content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { /* do something */ }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home Button")
                }

                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Localized description",
                    )
                }

                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Localized description",
                    )
                }
            }
        })
    }
}

@Composable
fun MainContent(innerPadding: PaddingValues) {
    Column(modifier = Modifier.padding(innerPadding)) {
        // List Title
        ListTitle()

        // News Fetch
        NewsFetch()
    }
}

@Composable
fun ListTitle() {
    Text(
        text = "Latest News",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun NewsFetch(newsViewModel: NewsViewModel = viewModel()) {
    val newsList = newsViewModel.newsList.value
    val isLoading = newsViewModel.isLoading.value
    val errorMessage = newsViewModel.errorMessage.value

    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(200.dp)
        )
    } else if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(newsList) { article ->
                LazyList(items = article)
            }
        }
    }
}


@Composable
fun LazyList(items: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp) // Spacing between each item and each item padding
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Row {
            Column {
                Text(
                    text = items.title,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = items.description ?: "No description Available",
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            AsyncImage(
                model = items.image_url,
                contentDescription = "Image from URL",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MyApp()
}
