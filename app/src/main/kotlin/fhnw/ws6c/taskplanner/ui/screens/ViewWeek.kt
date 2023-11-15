package fhnw.ws6c.taskplanner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fhnw.ws6c.taskplanner.model.TaskPlannerModel

@Composable
fun ViewWeek(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { StdTopAppBar("${currentlySelectedDate.dayOfMonth}.${currentlySelectedDate.monthValue}.${currentlySelectedDate.year} - KW$currentlySelectedWeek", { MenuBtn(scaffoldState, scope) }, {
                IconButton(onClick = { updateWeek((-1)) }) {
                    Icon(Icons.Filled.ArrowBackIosNew, "Woche zur\u00FCck")
                }
                IconButton(onClick = { updateWeek(1) }) {
                    Icon(Icons.Filled.ArrowForwardIos, "Woche weiter")
                }
            }) },
            drawerContent = { Drawer(model, scaffoldState, scope) },
            content = { Body(model) },
        )
    }
}

@Composable
private fun Body(model: TaskPlannerModel) {
    with(model){
        val tasks = getAllOpenTasksOfSelectedDate()

        Column(content = {
            Row(content = {
                MultiToggleButton(model)
            }, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.padding(top = 10.dp))
            HeadingText("Aufgaben")

            if (tasks.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                ) {
                    items(tasks) { TaskCardWithDot(it, model) }
                }
            } else {
                Column(content = {
                    HeadingText("Du hast heute keine Aufgaben!")
                    SubText("Erstelle neue Aufgaben um sie hier zu sehen")
                }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize())
            }
        }, modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MultiToggleButton(model: TaskPlannerModel) {
    with(model) {
        val toggleStates = listOf(1, 2, 3, 4, 5, 6, 7)
        val selectedTint = MaterialTheme.colors.primary
        val unselectedTint = Color.Unspecified
        var oldToggleState = currentlySelectedDay

        var colors: List<Color>

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .border(BorderStroke(2.dp, MaterialTheme.colors.primary), RoundedCornerShape(10.dp))
        ) {
            toggleStates.forEachIndexed { index, toggleState ->
                val isSelected = currentlySelectedDay == toggleState
                val backgroundTint = if (isSelected) selectedTint else unselectedTint
                val textColor =
                    if (isSelected) MaterialTheme.colors.onPrimary else Color.Unspecified

                Column(
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(backgroundTint)
                        .toggleable(
                            value = isSelected,
                            enabled = true,
                            onValueChange = { selected ->
                                if (selected) {
                                    val diff = toggleState - oldToggleState
                                    currentlySelectedDay = toggleState
                                    oldToggleState = toggleState
                                    currentlySelectedDate =
                                        currentlySelectedDate.plusDays(diff.toLong())
                                }
                            })
                        .padding(vertical = 6.dp, horizontal = 8.dp)
                ) {
                    var text = ""
                    when (index + 1) {
                        1 -> text = "MO"
                        2 -> text = "DI"
                        3 -> text = "MI"
                        4 -> text = "DO"
                        5 -> text = "FR"
                        6 -> text = "SA"
                        7 -> text = "SO"
                    }
                    Text(text, color = textColor, modifier = Modifier.padding(4.dp))

                    val diff = (index + 1) - oldToggleState
                    val date = currentlySelectedDate.plusDays(diff.toLong())

                    colors = getColorsOfAllOpenTasksOfDate(date)

                    LazyVerticalGrid(cells = GridCells.Fixed(3), modifier = Modifier.size(39.dp)) {
                        items(colors) {
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(it)
                            )
                        }
                    }
                }
            }
        }
    }
}