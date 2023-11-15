package fhnw.ws6c.taskplanner.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fhnw.ws6c.taskplanner.data.Module
import fhnw.ws6c.taskplanner.data.HelperFunctions
import fhnw.ws6c.taskplanner.data.Project
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel

@Composable
fun ViewProcesses(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { StdTopAppBar("Projekte", { MenuBtn(scaffoldState, scope) }, {}) },
        drawerContent = { Drawer(model, scaffoldState, scope) },
        content = { Body(model) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { model.currentScreen = Screen.CREATE_PROJECT },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(Icons.Filled.Add, "Neuen Prozess hinzuf\u00FCgen")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Body(model: TaskPlannerModel) {
    with(model) {
        if (projectList.isNotEmpty()) {
            LazyVerticalGrid(cells = GridCells.Adaptive(150.dp), modifier = Modifier.padding(8.dp)) {
                items(projectList) { ProcessCard(it, model) }
            }
        } else {
            Column(content = {
                HeadingText("Du hast keine Projekte!")
                SubText("Erstelle neue Projekte um sie hier zu sehen")
            }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ProcessCard(project: Project, model: TaskPlannerModel) {
    with(project) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(150.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    model.currentlySelectedProject = project
                    model.lastScreen = Screen.VIEW_PROJECTS
                    model.currentScreen = Screen.DETAIL_VIEW_PROJECT
                }
        ) {
            Column(content = {
                CategoryLabel(module)
                Spacer(modifier = Modifier.padding(top = 8.dp))
                HeadingText(title)
                SubText("${getOpenTasks()} Aufgaben offen")
                SubText("${tasks.size} Aufgaben total")
                SubText("bis am ${HelperFunctions.getFormattedDate(endDate)}")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                LinearProgressIndicator(
                    progress = getProgress(),
                    color = MaterialTheme.colors.primaryVariant,
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.clip(
                        CircleShape
                    )
                )
            }, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun CategoryLabel(module: Module) {
    with(module) {
        Row(
            content = {
                LabelTextUnderlined(module.title)
                Box(modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                    content = {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    })
            },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
    }
}