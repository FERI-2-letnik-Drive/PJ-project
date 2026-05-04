package com.example.smartmailbox

import android.hardware.lights.Light
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartmailbox.ui.theme.DarkGreen
import com.example.smartmailbox.ui.theme.Emerald
import com.example.smartmailbox.ui.theme.ForestGreen
import com.example.smartmailbox.ui.theme.LightMint

data class NavigationItem(
    val title: String,
    val selectedIcon: Int
    //val unselectedIcon: Int
)

@Composable
fun AppFooter(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home_icon
        ),
        NavigationItem(
            title = "Scan",
            selectedIcon = R.drawable.qr_code_scanner
        ),
        NavigationItem(
            title = "Logs",
            selectedIcon = R.drawable.docs_icon
        )
    )

    var selectedItemIndex by remember { mutableStateOf(0) }
    /*
    Box(
        modifier = modifier
        .fillMaxWidth()
        .background(DarkGreen)
        .padding(20.dp)
        .height(48.dp),
        contentAlignment = Alignment.Center,

    ) {

    }
    */
    NavigationBar(
        containerColor = DarkGreen,
        contentColor = LightMint
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { selectedItemIndex = index },

                icon = {
                    Icon(
                        painter = painterResource(id = item.selectedIcon),
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(
                        item.title,
                        color = LocalContentColor.current
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightMint,
                    unselectedIconColor = LightMint.copy(alpha = 0.7f),
                    selectedTextColor = LightMint,
                    unselectedTextColor = LightMint.copy(alpha = 0.7f),
                    indicatorColor = ForestGreen.copy(alpha = 0.3f)
                )
            )
        }
    }
}