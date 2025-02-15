package com.example.composebottomsheet.ui.composable

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composebottomsheet.ui.theme.Theme

@Composable
fun Tile(
    title: String,
    subtitle: String,
    isClickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val modifierAddon = if (isClickable) Modifier.clickable(onClick = onClick) else Modifier
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = colorResource(R.color.darker_gray))
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .then(modifierAddon)

    ) {
        Text(text = title, fontSize = 32.sp)
        Text(text = subtitle, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TilePreview() {
    Theme {
        Tile(
            title = "Title",
            subtitle = "Hey, how are you doing",
            isClickable = true,
            onClick = {}
        )
    }
}
