package fhnw.ws6c.taskplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fhnw.ws6c.taskplanner.data.HelperFunctions
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel

@Composable
fun DetailViewTask(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                StdTopAppBar(currentlySelectedTask.title, {
                    BackBtn {
                        if (lastScreen == Screen.VIEW_WEEK){
                            currentScreen = Screen.VIEW_WEEK
                        }else {
                            currentScreen = Screen.DETAIL_VIEW_PROJECT
                        }
                    }
                }, {
                    EditBtn {
                        currentScreen = Screen.EDIT_TASK
                    }
                    DeleteBtn {
                        deleteTask()
                    }
                })
            },
            drawerContent = { Drawer(model, scaffoldState, scope) },
            content = { Body(model) },
        )
    }
}

@Composable
private fun Body(model: TaskPlannerModel) {
    with(model) {
        isDone = currentlySelectedTask.isDone

        Column(content = {
            Row(content = {
                Surface(contentColor = MaterialTheme.colors.onPrimary) {
                    Button(
                        onClick = {
                            currentlySelectedTask.isDone = !currentlySelectedTask.isDone
                            //needed to redraw
                            isDone = !isDone
                        },
                        contentPadding = PaddingValues(horizontal = 42.dp),
                        colors = if (isDone) ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFF36D100
                            )
                        ) else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        HeadingText("Erledigt")
                    }
                }
                Column(
                    content = {
                        SubText("F\u00E4llig am")
                        Text(HelperFunctions.getFormattedDate(currentlySelectedTask.dueDate))
                    },
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }, verticalAlignment = Alignment.CenterVertically)
            Spacer(modifier = Modifier.padding(top = 16.dp))
            SubText("Titel")
            Text(currentlySelectedTask.title)
            Divider(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            SubText("Beschreibung")
            Text(currentlySelectedTask.description)
            Divider(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
            SubText("Zugeh\u00F6riges Projekt")
            Text(currentlySelectedTask.parentProject)
            Divider(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }, modifier = Modifier.padding(16.dp))
    }
}
