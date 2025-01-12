package com.bugbender.dogsbreeds.presentation.ui.dogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import com.bugbender.dogsbreeds.R
import com.bugbender.dogsbreeds.presentation.ui.theme.DogsBreedsTheme

@Composable
fun DogsScreen(
    viewModel: DogsViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val dogState by viewModel.dogState.observeAsState(DogsViewModel.DogState.FirstLoading)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.dogs),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (dogState) {
                is DogsViewModel.DogState.FirstLoading -> {
                    Text(
                        text = "Don’t be shy. Click Fetch button",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                is DogsViewModel.DogState.Loading -> {
                    LoadingDataProgressBar()
                }

                is DogsViewModel.DogState.Success -> {
                    val dog = (dogState as DogsViewModel.DogState.Success).dog

                    val model = ImageRequest.Builder(LocalContext.current)
                        .data(dog.imageUrl)
                        .size(Size.ORIGINAL)
                        .build()
                    val imageState by rememberAsyncImagePainter(model = model).state.collectAsState()

                    when (imageState) {
                        is AsyncImagePainter.State.Success -> {

                            val intrinsicSize = (imageState as AsyncImagePainter.State.Success).painter!!.intrinsicSize

                            Image(
                                painter = imageState.painter!!,
                                contentDescription = stringResource(R.string.dogs),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .weight(1f, fill = false)
                                    .aspectRatio(intrinsicSize.width / intrinsicSize.height)
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        }

                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.width(48.dp)
                            )
                        }

                        is AsyncImagePainter.State.Error -> {
                            Icon(
                                painter = painterResource(R.drawable.image_not_supported),
                                contentDescription = stringResource(R.string.image_not_supported),
                            )
                        }
                        else -> {}
                    }

                    Text(
                        text = "${dog.subBreed.orEmpty()} ${dog.breed}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is DogsViewModel.DogState.Error -> {
                    Text(
                        text = (dogState as DogsViewModel.DogState.Error).message,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        if (dogState !is DogsViewModel.DogState.Loading) {
            Button(
                onClick = viewModel::fetchDog,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(text = "Fetch")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DogsScreenPreview() {
    DogsBreedsTheme {
        DogsScreen(modifier = Modifier.systemBarsPadding())
    }
}

@Composable
fun LoadingDataProgressBar(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.width(48.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading_data),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDataProgressBarPreview() {
    DogsBreedsTheme {
        LoadingDataProgressBar()
    }
}