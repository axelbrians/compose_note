package com.machina.compose_notes.ui.view.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ItemNote(
    note: Note,
    onDeleteNote: (Note) -> Unit
) {
    if (note.id < 0) {
        return Spacer(modifier = Modifier.height(75.dp))
    }

    val iconSize = (-68).dp
    val swipeableState = rememberSwipeableState(0)
    val iconPx = with(LocalDensity.current) { iconSize.toPx() }
    val anchors = mapOf(0f to 0, iconPx to 1)

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(Color(0xFFDA5D5D))
    ) {
//        Log.d("Note", "offset ${swipeableState.offset.value.roundToInt()}")
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    coroutineScope.launch {
                        onDeleteNote(note)
                    }
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete this note",
                    tint = Color.White
                )
            }
        }

        AnimatedVisibility(
            visible = note.isVisible,
//            enter = slideInHorizontally(
//                initialOffsetX = { it },
//                animationSpec = TweenSpec(200, 200, FastOutLinearInEasing)
//            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = TweenSpec(200, 0, FastOutLinearInEasing)
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { }
            ) {
                val (titleText, contentText, divider) = createRefs()

                Text(
                    modifier = Modifier.constrainAs(titleText) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(parent.start, margin = 18.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                        width = Dimension.fillToConstraints
                    },
                    text = note.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier.constrainAs(contentText) {
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                        start.linkTo(parent.start, margin = 18.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                        width = Dimension.fillToConstraints
                    },
                    text = note.content,
                    textAlign = TextAlign.Start
                )

                Divider(
                    modifier = Modifier.constrainAs(divider) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
            }
        }
    }
}