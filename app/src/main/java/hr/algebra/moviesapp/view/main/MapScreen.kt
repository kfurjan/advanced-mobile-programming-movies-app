package hr.algebra.moviesapp.view.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.google.maps.android.compose.*
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import hr.algebra.moviesapp.R
import hr.algebra.moviesapp.model.Point
import hr.algebra.moviesapp.util.bitmapDescriptorFromVector

@Composable
fun MapScreen(modifier: Modifier = Modifier, mapState: MapState) {

    if (mapState.loading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val points = mapState.points

        var pointSelected by remember {
            mutableStateOf(points.first())
        }

        val showDialog = remember {
            mutableStateOf(false)
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pointSelected.latLng(), 12f)
        }

        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
        }

        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.HYBRID, isTrafficEnabled = true))
        }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            properties = properties
        ) {
            points.forEach { point ->
                Marker(
                    icon = bitmapDescriptorFromVector(
                        LocalContext.current,
                        R.drawable.location
                    ),
                    position = point.latLng(),
                    onClick = {
                        pointSelected = point
                        showDialog.value = true
                        true
                    }
                )
            }
        }
        if (showDialog.value) {
            PointDialog(
                modifier = modifier,
                point = pointSelected,
                showDialog = showDialog
            )
        }
    }


}

@Composable
fun PointDialog(modifier: Modifier, point: Point, showDialog: MutableState<Boolean>) {

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            Button(onClick = { showDialog.value = false }) {
                Text(text = stringResource(R.string.close))
            }
        },
        title = {
            Text(text = point.title)
        },
        text = {
            Column {
                Text(text = point.address)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(point.image)
                        .crossfade(durationMillis = 1000)
                        .build(),
                    contentDescription = point.title,
                    placeholder = painterResource(id = R.drawable.reel),
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .padding(top = 20.dp)
                        .size(270.dp)
                        .clip(CircleShape)
                    )
            }
        }
    )

}
