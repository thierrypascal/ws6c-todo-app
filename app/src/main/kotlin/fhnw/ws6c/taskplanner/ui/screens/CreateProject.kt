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
fun CreateProject(model: TaskPlannerModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    with(model) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                StdTopAppBar("Projekt Erstellen",
                    {},
                    {
                        CancelBtn { currentScreen = Screen.VIEW_PROJECTS }
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
        CreateProjectForm(model)
    }, modifier = Modifier.padding(16.dp))
}

@Composable
private fun CreateProjectForm(model: TaskPlannerModel){
    with(model) {
        val focusManager = LocalFocusManager.current

        var title by rememberSaveable { mutableStateOf("") }
        var module by rememberSaveable { mutableStateOf("") }

        var enableSaveButton by rememberSaveable { mutableStateOf(false) }
        val validateTitleError = "Bitte gib deinem Projekt einen Titel."

        Column(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                title = "Titel",
                value = title,
                onValueChange = {
                    validateTitle = false
                    title = it
                },
                label = "",
                placeholder = "Titel",
                showError = validateTitle,
                errorMessage = validateTitleError,
                keyBoardOption = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyBoardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            val startDate = datePicker("Beginnt am", "Beginnt am")
            val endDate   = datePicker("Endet am",   "Endet am")

            CustomTextField(
                title = "Zugehöriges Modul",
                value = module,
                onValueChange = { module = it },
                label = "",
                placeholder = "Zugehöriges Modul",
                keyBoardOption = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyBoardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            val pickedColor = colorPicker()

            enableSaveButton = title.isBlank()
            if (!enableSaveButton){
                Button(
                    onClick = {
                        saveProject(
                            title,
                            startDate,
                            endDate,
                            module,
                            pickedColor
                        )
                        currentScreen = Screen.VIEW_PROJECTS
                    },
                    modifier = Modifier.padding(top = 30.dp)) {
                    Text(text = "Speichern")
                }
            }else{
                Button(
                    onClick = {
                        saveProject(
                            title,
                            startDate,
                            endDate,
                            module,
                            pickedColor
                        )
                    },
                    modifier = Modifier.padding(top = 30.dp)) {
                    Text(text = "Speichern")
                }
            }
        }
    }
}