package fhnw.ws6c.taskplanner.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.ws6c.taskplanner.data.HelperFunctions
import fhnw.ws6c.taskplanner.data.Project
import fhnw.ws6c.taskplanner.data.Task
import fhnw.ws6c.taskplanner.model.Screen
import fhnw.ws6c.taskplanner.model.TaskPlannerModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun StdTopAppBar(text: String, navIcon: @Composable (() -> Unit)?, actionBtns: @Composable (RowScope.() -> Unit)) {
    TopAppBar(
        title = { Text(
            text = text.uppercase(),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        ) },
        navigationIcon = navIcon,
        actions = actionBtns,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background,
    )
}

@Composable
fun MenuBtn(scaffoldState: ScaffoldState, scope: CoroutineScope){
    IconButton(onClick = { scope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() } }) {
        Icon(Icons.Filled.Menu, "Seitennavigation")
    }
}

@Composable
fun BackBtn(nav: () -> Unit){
    IconButton(onClick = nav) {
        Icon(Icons.Filled.ArrowBackIos, "Zur\u00FCck")
    }
}

@Composable
fun DeleteBtn(deleteAction: () -> Unit){
    IconButton(onClick = deleteAction) {
        Icon(Icons.Filled.Delete, "Delete")
    }
}

@Composable
fun EditBtn(deleteAction: () -> Unit){
    IconButton(onClick = deleteAction) {
        Icon(Icons.Filled.Edit, "Edit")
    }
}

@Composable
fun CancelBtn(nav: () -> Unit){
    IconButton(onClick = nav) {
        Icon(Icons.Filled.Cancel, "Cancel")
    }
}

@Composable
fun Drawer(model: TaskPlannerModel, scaffoldState: ScaffoldState, scope: CoroutineScope) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() } }) {
                Icon(Icons.Filled.ArrowBackIos, "Zur\u00FCck")
            }
        },
        actions = {
            Switch(
                checked = model.darkTheme,
                onCheckedChange = { model.toggleTheme() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.onSecondary,
                    uncheckedThumbColor = MaterialTheme.colors.onSecondary,
                ),
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    )
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 30.dp)) {
        TextButton(onClick = {
            model.currentScreen = Screen.VIEW_WEEK
            scope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() }
        }) {
            BigHeadingText("Wochenansicht")
        }
        TextButton(onClick = {
            model.currentScreen = Screen.VIEW_PROJECTS
            scope.launch { if (scaffoldState.drawerState.isClosed) scaffoldState.drawerState.open() else scaffoldState.drawerState.close() }
        }) {
            BigHeadingText("Projekt\u00DCbersicht")
        }
    }
}

@Composable
fun HeadingText(text: String) {
    Text(
        text = text.uppercase(Locale.getDefault()),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
        )
    )
}

@Composable
fun BigHeadingText(text: String) {
    Text(
        text = text.uppercase(Locale.getDefault()),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSecondary
        )
    )
}

@Composable
fun SubText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
        )
    )
}

@Composable
fun LabelTextUnderlined(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
        )
    )
}

@Composable
fun TaskCard(task: Task, model: TaskPlannerModel) {
    with(task) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (isDone) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    model.currentlySelectedTask = task
                    model.currentScreen = Screen.DETAIL_VIEW_TASK
                }
        ) {
            Column(content = {
                Spacer(modifier = Modifier.padding(top = 8.dp))
                HeadingText(title)
                SubText("Erledigen Bis: ${task.dueDate.dayOfMonth}.${task.dueDate.monthValue}.${task.dueDate.year} - ${HelperFunctions.getFormattedTime(task.endTime)} Uhr")
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun TaskCardWithDot(task: Task, model: TaskPlannerModel) {
    with(task) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (isDone) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    model.currentlySelectedTask = task
                    model.currentlySelectedProject = model.getParentProcess(task.parentProject)
                    model.lastScreen = Screen.VIEW_WEEK
                    model.currentScreen = Screen.DETAIL_VIEW_TASK
                }
        ) {
            Row(content = {
                Column(content = {
                    Box(modifier = Modifier.padding(start = 8.dp),
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(module.color)
                            )
                        })
                }, modifier = Modifier.padding(top = 22.dp))
                Column(content = {
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    HeadingText(title)
                    SubText("Erledigen Bis: ${task.dueDate.dayOfMonth}.${task.dueDate.monthValue}.${task.dueDate.year} - ${HelperFunctions.getFormattedTime(task.endTime)} Uhr")
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                }, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth())
            })
        }
    }
}

@Composable
fun CustomTextField(
    title: String,
    value: String,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    keyBoardOption: KeyboardOptions = KeyboardOptions.Default,
    keyBoardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String= "",
){
    Column( modifier = Modifier
        .padding(bottom = 5.dp)
    ) {
        Text(text = title)
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder)},
            isError = showError,
            keyboardOptions = keyBoardOption,
            keyboardActions = keyBoardActions,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            ),
        )

        if (showError){
           Text(
               text = errorMessage,
               style = MaterialTheme.typography.caption,
               modifier = Modifier
                   .padding(start = 8.dp)
                   .fillMaxWidth()
           )
        }
    }
}

@Composable
fun datePicker(title: String, placeholder: String, initDate: LocalDate = LocalDate.now()): LocalDate{
    val focusManager = LocalFocusManager.current
    val context      = LocalContext.current

    var year    by rememberSaveable { mutableStateOf(initDate.year) }
    var month   by rememberSaveable { mutableStateOf(initDate.month.value) }
    var day     by rememberSaveable { mutableStateOf(initDate.dayOfMonth) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, pickedYear: Int, pickedMonth: Int, pickedDay: Int ->
            year  = pickedYear
            month = pickedMonth + 1
            day   = pickedDay
        }, year, month - 1, day
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        datePickerDialog.show()
    }
    CustomTextField(
        title = title,
        value = "${if (day<10){"0"}else{""} }$day.${if (month<10){"0"}else{""} }${month}.$year",
        onValueChange = { datePickerDialog.show() },
        interactionSource = interactionSource,
        label = "",
        placeholder = placeholder,
        keyBoardOption = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyBoardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )

    return LocalDate.of(year, month, day)
}

@Composable
fun timePicker(title: String, placeholder: String, initTime: LocalTime = LocalTime.now()): LocalTime {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var hour      by rememberSaveable { mutableStateOf(initTime.hour) }
    var minute    by rememberSaveable { mutableStateOf(initTime.minute) }

    val startTimePickerDialog = TimePickerDialog(
        context,
        {_, pickedHour : Int, pickedMinute: Int ->
            hour = pickedHour
            minute = pickedMinute
        }, hour, minute, true
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        startTimePickerDialog.show()
    }
    CustomTextField(
        title = title,
        value = "${if (hour<10){"0"}else{""} }$hour:${if (minute<10){"0"}else{""} }$minute",
        onValueChange = { startTimePickerDialog.show() },
        interactionSource = interactionSource,
        label = "",
        placeholder = placeholder,
        keyBoardOption = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyBoardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )

    return LocalTime.of(hour, minute)
}

@Composable
fun colorPicker(initColor: Color = Color(0xFFfed330)): Color{
    var expanded by remember { mutableStateOf(false) }
    val colorPallet = listOf(
        Color(0xFFfc5c65), Color(0xFFfd9644), Color(0xFFfed330),
        Color(0xFF26de81), Color(0xFF2bcbba), Color(0xFF45aaf2),
        Color(0xFF4b7bec), Color(0xFFa55eea), Color(0xFF4b6584))
    var selectedColor by remember {mutableStateOf(initColor)}
    val i = listOf("")

    Column {
        Text(text = "Modul-Farbe", modifier = Modifier.padding(bottom = 16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(selectedColor, RoundedCornerShape(5.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
                .clickable(onClick = {
                    expanded = !expanded
                })
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            )
            {
                i.forEach { _ ->
                    DropdownMenuItem(onClick = {}, enabled = false, modifier = Modifier.padding(vertical = 7.dp)) {
                        Column (modifier = Modifier.size(100.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                for (j in 0..2) {
                                    ColorBox(color = colorPallet[j]) {
                                        selectedColor = colorPallet[j]
                                        expanded = !expanded
                                    }
                                }
                            }
                            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                for (j in 3..5) {
                                    ColorBox(color = colorPallet[j]) {
                                        selectedColor = colorPallet[j]
                                        expanded = !expanded
                                    }
                                }
                            }
                            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                for (j in 6..8) {
                                    ColorBox(color = colorPallet[j]) {
                                        selectedColor = colorPallet[j]
                                        expanded = !expanded
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return selectedColor
}

@Composable
private fun ColorBox(color: Color, onClick: () -> Unit) {
    Box(modifier = Modifier
        .size(30.dp)
        .background(color, RoundedCornerShape(5.dp))
        .clickable(onClick = onClick)
    )
}

@Composable
fun chooseProjectDropDown(model: TaskPlannerModel): Project {
    with(model) {
        var selectedProject by remember { mutableStateOf(currentlySelectedProject) }

        var expanded        by remember { mutableStateOf(false) }
        var selectedItem    by  remember {mutableStateOf(selectedProject.title)}

        val icon = if (expanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }

        Column {
            Text(text = "Zugehöriges Projekt", modifier = Modifier.padding(bottom = 16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
                    .clickable(onClick = {
                        expanded = !expanded
                    })
                    .padding(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = selectedItem)
                    Icon(icon, "", Modifier
                        .padding(start = 10.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(.9f)
                )
                {
                    projectList.forEach { process ->
                        DropdownMenuItem(onClick = {
                            selectedItem = process.title
                            selectedProject = process
                            expanded = false

                        }) {
                            Text(text = process.title)
                        }
                    }
                }
            }
        }
        return selectedProject
    }
}
