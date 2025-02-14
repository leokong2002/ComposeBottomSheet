package com.example.composebottomsheet.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composebottomsheet.ui.composable.Tile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onHandleEvent: (HomeViewModel.UiEvent) -> Unit = viewModel::handleEvent

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
            onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenBottomSheet) },
        )
        Tile(
            title = "Bottom Sheet 2",
            subtitle = "Full Height Bottom Sheet",
            onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenFullHeightBottomSheet) },
        )
        Tile(
            title = "Bottom Sheet 3",
            subtitle = "Bottom Sheet with No Drag Handle",
            onClick = { onHandleEvent(HomeViewModel.UiEvent.OpenBottomSheetWithNoDragHandle) },
        )
    }
    BottomSheet(
        bottomSheetType = state.bottomSheetType,
        sheetState = sheetState,
        bottomSheetText = state.bottomSheetText,
        fullHeightBottomSheetText = state.fullHeightBottomSheetText,
        noDragHandleBottomSheetText = state.noDragHandleBottomSheetText,
        onBottomSheetDismiss = { onHandleEvent(HomeViewModel.UiEvent.DismissBottomSheet) },
        onCloseButtonClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onHandleEvent(HomeViewModel.UiEvent.DismissBottomSheet)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    bottomSheetType: HomeViewModel.BottomSheetType,
    sheetState: SheetState,
    bottomSheetText: String,
    fullHeightBottomSheetText: String,
    noDragHandleBottomSheetText: String,
    onBottomSheetDismiss: () -> Unit,
    onCloseButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (bottomSheetType != HomeViewModel.BottomSheetType.Hide) {
        ModalBottomSheet(
            onDismissRequest = onBottomSheetDismiss,
            sheetState = sheetState,
            dragHandle = if (bottomSheetType == HomeViewModel.BottomSheetType.NoDragHandle) {
                null
            } else {
                { BottomSheetDefaults.DragHandle() }
            },
            modifier = if (bottomSheetType == HomeViewModel.BottomSheetType.FullHeight) modifier.fillMaxSize() else modifier
        ) {
            when (bottomSheetType) {
                HomeViewModel.BottomSheetType.Normal -> {
                    Text(text = bottomSheetText, textAlign = TextAlign.Center)
                }
                HomeViewModel.BottomSheetType.FullHeight -> {
                    Text(text = fullHeightBottomSheetText, textAlign = TextAlign.Center)
                }
                HomeViewModel.BottomSheetType.NoDragHandle -> {
                    Button(
                        onClick = onCloseButtonClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = noDragHandleBottomSheetText)
                    }
                }
                else -> {}
            }
        }
    }
}
