package fhnw.ws6c.taskplanner

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.ws6c.EmobaApp
import fhnw.ws6c.taskplanner.model.TaskPlannerModel
import fhnw.ws6c.taskplanner.ui.TaskPlannerUI


object TaskPlanner : EmobaApp {
    private lateinit var model : TaskPlannerModel

    override fun initialize(activity: ComponentActivity) {
        model = TaskPlannerModel()
        model.loadRepository()
    }

    @Composable
    override fun CreateUI() {
        TaskPlannerUI(model)
    }
}
