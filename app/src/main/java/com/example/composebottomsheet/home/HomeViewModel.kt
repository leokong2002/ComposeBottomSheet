package com.example.composebottomsheet.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.DismissBottomSheet -> _state.update { it.copy(isTileClickable = true, bottomSheetType = BottomSheetType.Hide) }
            is UiEvent.OpenBottomSheet -> _state.update { it.copy(isTileClickable = false, bottomSheetType = BottomSheetType.Normal) }
            is UiEvent.OpenFullHeightBottomSheet -> _state.update { it.copy(isTileClickable = false, bottomSheetType = BottomSheetType.FullHeight) }
            is UiEvent.OpenBottomSheetWithNoDragHandle -> _state.update { it.copy(isTileClickable = false, bottomSheetType = BottomSheetType.NoDragHandle) }
        }
    }

    enum class BottomSheetType { Normal, FullHeight, NoDragHandle, Hide }

    sealed interface UiEvent {
        data object DismissBottomSheet : UiEvent
        data object OpenBottomSheet : UiEvent
        data object OpenFullHeightBottomSheet : UiEvent
        data object OpenBottomSheetWithNoDragHandle : UiEvent
    }

    data class UiState(
        val isTileClickable: Boolean = true,
        val bottomSheetType: BottomSheetType = BottomSheetType.Hide,
        val bottomSheetText: String = "This is the normal bottom sheet. Swipe down to dismiss.",
        val fullHeightBottomSheetText: String = "This is the full height bottom sheet. Swipe up to open sheet. Swipe down to dismiss.",
        val noDragHandleBottomSheetText: String = "Click the bottom to dismiss",
    )
}
