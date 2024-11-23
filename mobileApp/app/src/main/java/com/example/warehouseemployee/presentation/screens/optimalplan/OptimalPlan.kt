package com.example.warehouseemployee.presentation.screens.optimalplan

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Dimension
import coil3.size.Size
import com.example.warehouseemployee.R
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.InfoTaskLoadingDestination
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import kotlinx.coroutines.delay
import kotlinx.datetime.Month
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OptimalPlan(
    navController: NavHostController,
    worker: Worker,
    task: Task,
    themeUI: ThemeMode,
    viewModel: OptimalPlanViewModel = hiltViewModel()
) {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val sdfNew = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
    val date = sdf.parse(task.dateExecutionTask)
    val dateTask = sdfNew.format(date)
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val image by viewModel.imageUrl.collectAsState("")
    viewModel.getImg(task.id)

    LaunchedEffect(image) {
        delay(5000)
        viewModel.imageUrl.collect { newImageUrl ->
            newImageUrl?.let {
                imageUrl = Uri.parse(newImageUrl)
            }
        }
    }
    WarehouseEmployeeTheme(themeMode = themeUI) {
        val context = LocalContext.current
        val orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        DisposableEffect(orientation) {
            val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
            val originalOrientation = activity.requestedOrientation
            activity.requestedOrientation = orientation
            onDispose {
                // restore original orientation when view disappears
                activity.requestedOrientation = originalOrientation
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarehouseEmployeeTheme.colors.background)
                .padding(horizontal = 45.dp)

        ) {
            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarehouseEmployeeTheme.colors.background_important_element)
                ) {

                    IconButton(

                        onClick = {
                            navController.navigate(
                                "${InfoTaskLoadingDestination.route}/${
                                    Json.encodeToString(
                                        worker
                                    )
                                }/${Json.encodeToString(task)}/${themeUI.title}"
                            )
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(vertical = 5.dp),
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "",
                            tint = WarehouseEmployeeTheme.colors.color_icon
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "${task.idCategoryTask.nameCategory} от ${dateTask}",
                        style = WarehouseEmployeeTheme.typography.primaryTitle.copy(
                            fontSize = 15.sp
                        ),
                        color = WarehouseEmployeeTheme.colors.text_color_important_element,
                        textAlign = TextAlign.Center
                    )

                }

            }
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
                    .weight(3f)
            ) {
                if (imageUrl != null)
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()

                        )
                    }
            }
        }
    }
}





fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}