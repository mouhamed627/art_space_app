package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentArtwork by remember { mutableIntStateOf(1) }
    val configuration = LocalConfiguration.current
    val isWideScreen = configuration.screenWidthDp >= 600

    val onPrev = { currentArtwork = if (currentArtwork == 1) 3 else currentArtwork - 1 }
    val onNext = { currentArtwork = if (currentArtwork == 3) 1 else currentArtwork + 1 }

    if (isWideScreen) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ArtworkImage(currentArtwork, Modifier.weight(1f))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ArtworkDescription(currentArtwork)
                Spacer(Modifier.height(16.dp))
                DisplayController(onPrev, onNext)
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArtworkImage(currentArtwork, Modifier)
            Spacer(Modifier.height(16.dp))
            ArtworkDescription(currentArtwork)
            Spacer(Modifier.height(16.dp))
            DisplayController(onPrev, onNext)
        }
    }
}

@Composable
fun ArtworkImage(artworkId: Int, modifier: Modifier = Modifier) {
    val imageRes = when (artworkId) {
        1 -> R.drawable.artwork1
        2 -> R.drawable.artwork2
        else -> R.drawable.artwork3
    }
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier.fillMaxWidth().aspectRatio(1f)
    )
}

@Composable
fun ArtworkDescription(artworkId: Int) {
    val title = when (artworkId) { 1 -> "Title 1"; 2 -> "Title 2"; else -> "Title 3" }
    val artist = when (artworkId) { 1 -> "Artist 1"; 2 -> "Artist 2"; else -> "Artist 3" }
    val year = when (artworkId) { 1 -> "2020"; 2 -> "2021"; else -> "2022" }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Text(artist, style = MaterialTheme.typography.bodyMedium)
        Text(year, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = onPrevious) { Text("Previous") }
        Button(onClick = onNext) { Text("Next") }
    }
}