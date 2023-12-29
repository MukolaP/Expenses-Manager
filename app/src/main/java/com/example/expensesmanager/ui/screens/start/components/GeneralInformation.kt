package com.example.expensesmanager.ui.screens.start.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensesmanager.R
import com.example.expensesmanager.domain.model.TwoDragAnchors
import com.example.expensesmanager.ui.screens.start.StartViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeneralInformation(viewModel: StartViewModel) {
    val alpha = 0.7f
    val percent = 72

    val configuration = LocalConfiguration.current
    val width =
        (configuration.screenWidthDp - 48 - 48 - (configuration.screenWidthDp / 100 * (100 - percent)))

    val budgetColor = if (percent > 75) {
        Color(0xff51c374)
    } else if (percent > 55) {
        Color(0xffc0eb34)
    } else if (percent > 40) {
        Color.Yellow
    } else if (percent > 30) {
        Color(0xffeb9c34)
    } else if (percent > 25) {
        Color(0xffeb7134)
    } else {
        Color.Red
    }

    var expanded by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            initialValue = TwoDragAnchors.Close,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 200.dp.toPx() } },
            animationSpec = tween(),
        ).apply {
            updateAnchors(

                DraggableAnchors {
                    TwoDragAnchors.Close at 0f
                    TwoDragAnchors.Open at 100f
                })
        }
    }

    if (state.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                expanded = when (state.currentValue) {
                    TwoDragAnchors.Open -> {
                        true
                    }

                    TwoDragAnchors.Close -> {
                        false
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(if (!expanded) 25 else 15))
            .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessMediumLow))
            .height(if (expanded) 460.dp else 200.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.Black.copy(alpha), MaterialTheme.colorScheme.background.copy(alpha)
                    )
                )
            )
            .anchoredDraggable(state, Orientation.Vertical), horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_minus),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "$ 11,200",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,

                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Monthly budget",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "$percent%",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        Divider(
            color = budgetColor,
            thickness = 4.dp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(CircleShape)
                .width(width.dp)
        )

        Spacer(modifier = Modifier.height(if (!expanded) 8.dp else 0.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Detail information",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.alpha(if (expanded) 0f else 1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(if (!expanded) 20.dp else 0.dp))

        Text(
            text = "Date: 15 ${viewModel.textDate}, 2023",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Beginning: 36,000 $",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 24.dp)

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Spent: 25,000 $",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 24.dp)

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Avg costs per day: 720 $",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ideal expenses per day: 560 $",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Detail information",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}