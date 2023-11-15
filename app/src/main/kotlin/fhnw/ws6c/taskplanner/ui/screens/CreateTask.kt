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
import java.time.LocalTime

@Composable
fun CreateTask(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                StdTopAppBar("Aufgabe Erstellen",
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
        CreateTaskForm(model)
    }, modifier = Modifier.padding(16.dp))
}

@Composable
private fun CreateTaskForm(model: TaskPlannerModel){
    with(model) {
        val focusManager = LocalFocusManager.current

        var title by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }

        var enableSaveButton by rememberSaveable { mutableStateOf(false) }
        val validateTitleError = "Bitte gib deiner Aufgabe einen Titel."

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

            val dueDate     = datePicker("Fällig am",  "Fällig am")
            val startTime   = timePicker("Beginnt am", "Beginnt am")
            val endTime     = timePicker("Endet am",   "Endet am")

            val selectedProject = chooseProjectDropDown(model)

            enableSaveButton = title.isBlank()
            if (!enableSaveButton){
                Button(
                    onClick = {
                            saveTask(
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
                        saveTask(
                            title,
                            description,
                            dueDate,
                            LocalTime.now(),
                            LocalTime.now(),
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
