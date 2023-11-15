package fhnw.ws6c.taskplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel

@Composable
fun DetailViewProcess(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                StdTopAppBar(
                    currentlySelectedProject.title,
                    {
                        BackBtn {
                            currentScreen = Screen.VIEW_PROJECTS
                        }
                    },
                    {
                        EditBtn {
                            currentScreen = Screen.EDIT_PROJECT
                        }
                        DeleteBtn {
                            deleteProcess()
                        }
                    })
            },
            drawerContent = { Drawer(model, scaffoldState, scope) },
            content = { Body(model) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { currentScreen = Screen.CREATE_TASK },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ) {
                    Icon(Icons.Filled.Add, "Neuen Task hinzuf\u00FCgen")
                }
            }
        )
    }
}

@Composable
private fun Body(model: TaskPlannerModel) {
    with(model) {
        val tasksDone = currentlySelectedProject.tasks.filter { it.isDone }
        val tasksNotDone = currentlySelectedProject.tasks.filter { !it.isDone }

        if (tasksDone.isNotEmpty() || tasksNotDone.isNotEmpty()) {
            Column(content = {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    if (tasksNotDone.isNotEmpty()) {
                        items(tasksNotDone) { TaskCard(it, model) }
                    } else {
                        item { HeadingText("Du hast keine offenen Aufgaben!") }
                    }
                    if (tasksDone.isNotEmpty()) {
                        item {
                            Column(
                                content = {
                                    Divider()
                                    SubText("Erledigt:")
                                }, modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            )
                        }
                        items(tasksDone) { TaskCard(it, model) }
                    } else {
                        item { HeadingText("Du hast keine erledigten Aufgaben!") }
                    }
                }
            })
        } else {
            Column(
                content = {
                    HeadingText("Du hast keine Aufgaben in diesem Projekt!")
                    SubText("Erstelle neue Aufgaben um sie hier zu sehen")
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
