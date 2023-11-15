package fhnw.ws6c.taskplanner.ui

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel
import fhnw.ws6c.taskplanner.ui.screens.*
import fhnw.ws6c.taskplanner.ui.theme.AppTheme

@Composable
fun TaskPlannerUI(model : TaskPlannerModel){
    with(model) {
        AppTheme(darkTheme = darkTheme) {
            Crossfade(targetState = currentScreen) { screen ->
                when (screen) {
                    Screen.VIEW_PROJECTS -> {
                        ViewProcesses(model = model)
                    }
                    Screen.VIEW_WEEK -> {
                        ViewWeek(model = model)
                    }
                    Screen.DETAIL_VIEW_PROJECT -> {
                        DetailViewProcess(model = model)
                    }
                    Screen.DETAIL_VIEW_TASK -> {
                        DetailViewTask(model = model)
                    }
                    Screen.CREATE_TASK -> {
                        CreateTask(model = model)
                    }
                    Screen.CREATE_PROJECT -> {
                        CreateProject(model = model)
                    }
                    Screen.EDIT_TASK -> {
                        EditTask(model = model)
                    }
                    Screen.EDIT_PROJECT -> {
                        EditProject(model = model)
                    }
                }
            }
        }
    }
}
