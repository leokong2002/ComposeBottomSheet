package com.example.composebottomsheet.home

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composables.core.BottomSheet
import com.composables.core.SheetDetent
import com.composables.core.rememberBottomSheetState
import com.example.composebottomsheet.ui.composable.Tile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val modalBottomSheetState = rememberModalBottomSheetState()
    val bottomSheetState = rememberBottomSheetState(initialDetent = SheetDetent.Hidden)
    val onHandleEvent: (HomeViewModel.UiEvent) -> Unit = viewModel::handleEvent
    val canvasColour = BottomSheetDefaults.ScrimColor
    val canvasAlpha by animateFloatAsState(targetValue = if (state.bottomSheetType == HomeViewModel.BottomSheetType.NoDragHandle) 1f else 0f, animationSpec = TweenSpec())
    Box {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Tile(
                title = "Bottom Sheet 1",
                subtitle = "",
                isClickable = state.isTileClickable,
                onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenBottomSheet) },
            )
            Tile(
                title = "Bottom Sheet 2",
                subtitle = "Full Height Bottom Sheet",
                isClickable = state.isTileClickable,
                onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenFullHeightBottomSheet) },
            )
            Tile(
                title = "Bottom Sheet 3",
                subtitle = "Bottom Sheet with No Drag Handle",
                isClickable = state.isTileClickable,
                onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenBottomSheetWithNoDragHandle) },
            )
        }
        if (state.bottomSheetType == HomeViewModel.BottomSheetType.NoDragHandle) {
            Canvas(Modifier.fillMaxSize()) {
                drawRect(color = canvasColour, alpha = canvasAlpha.coerceIn(0f, 1f))
            }
        }
    }

    MyModalBottomSheet(
        bottomSheetType = state.bottomSheetType,
        sheetState = modalBottomSheetState,
        bottomSheetText = state.bottomSheetText,
        fullHeightBottomSheetText = state.fullHeightBottomSheetText,
        onBottomSheetDismiss = { onHandleEvent(HomeViewModel.UiEvent.DismissBottomSheet) },
    )

    BottomSheet(
        state = bottomSheetState,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color(0xFFFFFFFF))
            .padding(horizontal = 16.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onHandleEvent(HomeViewModel.UiEvent.DismissBottomSheet) }) {
                Text(text = state.noDragHandleBottomSheetText)
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    LaunchedEffect(key1 = state.bottomSheetType) {
        if (state.bottomSheetType == HomeViewModel.BottomSheetType.Hide) {
            bottomSheetState.animateTo(SheetDetent.Hidden)
        } else if (state.bottomSheetType == HomeViewModel.BottomSheetType.NoDragHandle) {
            bottomSheetState.animateTo(SheetDetent.FullyExpanded)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    bottomSheetType: HomeViewModel.BottomSheetType,
    sheetState: SheetState,
    bottomSheetText: String,
    fullHeightBottomSheetText: String,
    onBottomSheetDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (bottomSheetType == HomeViewModel.BottomSheetType.FullHeight || bottomSheetType == HomeViewModel.BottomSheetType.Normal) {
        ModalBottomSheet(
            onDismissRequest = onBottomSheetDismiss,
            sheetState = sheetState,
            modifier = if (bottomSheetType == HomeViewModel.BottomSheetType.FullHeight) modifier.fillMaxSize() else modifier
        ) {
            if (bottomSheetType == HomeViewModel.BottomSheetType.Normal) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = bottomSheetText, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(50.dp))
                }
            } else {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = fullHeightBottomSheetText, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}
