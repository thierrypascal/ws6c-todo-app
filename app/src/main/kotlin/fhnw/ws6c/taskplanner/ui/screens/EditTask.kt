package fhnw.ws6c.taskplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel

@Composable
fun EditTask(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                StdTopAppBar("Aufgabe Bearbeiten",
                    {},
                    {
                    CancelBtn { currentScreen = Screen.DETAIL_VIEW_PROJECT }
                })
            },
            drawerContent = { Drawer(model, scaffoldState, scope) },
            content = { Body(model) },
        )
    }
}

@Composable
private fun Body(model: TaskPlannerModel) {
    Column(content = {
        EditTaskForm(model)
    }, modifier = Modifier.padding(16.dp))
}

@Composable
private fun EditTaskForm(model: TaskPlannerModel){
    with(model) {
        val focusManager = LocalFocusManager.current

        var title            by rememberSaveable { mutableStateOf(currentlySelectedTask.title) }
        var description      by rememberSaveable { mutableStateOf(currentlySelectedTask.description) }
        var dueDate          by rememberSaveable { mutableStateOf(currentlySelectedTask.dueDate) }
        var startTime        by rememberSaveable { mutableStateOf(currentlySelectedTask.startTime) }
        var endTime          by rememberSaveable { mutableStateOf(currentlySelectedTask.endTime) }

        var enableSaveButton by rememberSaveable { mutableStateOf(false) }
        val validateTitleError = "Der Titel darf nicht leer sein."

        Column(modifier = Modifier.fillMaxSize()) {
            CustomTextField(
                title = "Titel",
                value = title,
                onValueChange = {
                    validateTitle = false
                    title = it },
                label = "",
                placeholder= "Titel",
                showError =  validateTitle,
                errorMessage = validateTitleError,
                keyBoardOption = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyBoardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            CustomTextField(
                title = "Beschreibung",
                value = description,
                onValueChange = { description = it },
                label = "",
                placeholder = "Beschreibung",
                keyBoardOption = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyBoardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            dueDate     = datePicker("Fällig am",  "Fällig am",  dueDate)
            startTime   = timePicker("Beginnt am", "Beginnt am", startTime)
            endTime     = timePicker("Endet am",   "Endet am",   endTime)

            val selectedProject = chooseProjectDropDown(model)

            enableSaveButton = title.isBlank()
            if (!enableSaveButton){
                Button(
                    onClick = {
                        updateTask(
                            currentlySelectedTask,
                            title,
                            description,
                            dueDate,
                            startTime,
                            endTime,
                            selectedProject
                        )
                        currentScreen = Screen.DETAIL_VIEW_PROJECT
                    },
                    modifier = Modifier.padding(top = 30.dp)) {
                    Text(text = "Speichern")
                }
            }else{
                Button(
                    onClick = {
                        updateTask(
                            currentlySelectedTask,
                            title,
                            description,
                            dueDate,
                            startTime,
                            endTime,
                            selectedProject
                        )
                    },
                    modifier = Modifier.padding(top = 30.dp)) {
                    Text(text = "Speichern")
                }
            }
        }
    }
}
