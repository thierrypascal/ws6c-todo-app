package fhnw.ws6c.taskplanner.model

import android.icu.util.Calendar.MONDAY
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import fhnw.ws6c.taskplanner.data.Module
import fhnw.ws6c.taskplanner.data.Project
import fhnw.ws6c.taskplanner.data.Repository
import fhnw.ws6c.taskplanner.data.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.WeekFields
import java.util.*

class TaskPlannerModel {

    val title = "Task Planner"
    var currentScreen by mutableStateOf(Screen.VIEW_WEEK)
    var lastScreen by mutableStateOf(Screen.VIEW_WEEK)
    var darkTheme by mutableStateOf(false)
        private set

    private val date: LocalDate = LocalDate.now()
    private val calendarWeek = date.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear())
    var currentlySelectedDate by mutableStateOf(date)
    var currentlySelectedWeek by mutableStateOf(calendarWeek)
    var currentlySelectedDay  by mutableStateOf(date.dayOfWeek.value)

    var projectList: List<Project> by mutableStateOf(emptyList())

    var currentlySelectedProject: Project by mutableStateOf(Project())
    var currentlySelectedTask: Task by mutableStateOf(Task())

    var validateTitle by mutableStateOf(false)


    //used for redraw
    var isDone by mutableStateOf(currentlySelectedTask.isDone)

    //TODO: export this function to data > impl.Services for local loading
    fun loadRepository() {
        projectList = Repository.projects
        //sort processes by endDate
        projectList =
            projectList.sortedWith(compareBy<Project> { it.endDate.year }
                .thenBy { it.endDate.month }
                .thenBy { it.endDate.dayOfMonth })
        //sort tasks of each process by dueDate and endTime
        projectList.forEach { process -> process.tasks =
            process.tasks.sortedWith(compareBy<Task> { it.dueDate.year }
                .thenBy { it.dueDate.month }
                .thenBy { it.dueDate.dayOfMonth }
                .thenBy { it.endTime.hour }
                .thenBy { it.endTime.minute })
        }
    }

    fun getCurrentlySelectedWeek(): String {
        return "$currentlySelectedDate.dayOfWeek"

    }

    fun getAllOpenTasksOfSelectedDate(): List<Task> {
        val result = arrayListOf<Task>()

        projectList.forEach { process ->
            process.tasks.forEach { task ->
                if (task.dueDate.year == currentlySelectedDate.year && task.dueDate.month == currentlySelectedDate.month && task.dueDate.dayOfMonth == currentlySelectedDate.dayOfMonth && !task.isDone) {
                    result.add(task)
                }
            }
        }

        //return sorted list by endDate
        return result.sortedWith(compareBy<Task> { it.endTime.hour }
            .thenBy { it.endTime.minute })
    }

    fun getColorsOfAllOpenTasksOfDate(date: LocalDate): List<Color> {
        val result = arrayListOf<Color>()

        projectList.forEach { process ->
            process.tasks.forEach { task ->
                if (task.dueDate.year == date.year && task.dueDate.month == date.month && task.dueDate.dayOfMonth == date.dayOfMonth && !task.isDone) {
                    result.add(task.module.color)
                }
            }
        }

        return result
    }

    fun getParentProcess(parentProcess: String) : Project{
        return projectList.find { it.title == parentProcess }!!
    }

    fun deleteTask(){
        currentScreen = Screen.DETAIL_VIEW_PROJECT
        currentlySelectedProject.tasks -= currentlySelectedTask
        currentlySelectedTask = Task()
    }

    fun deleteProcess(){
        currentScreen = Screen.VIEW_PROJECTS
        projectList = projectList - currentlySelectedProject
        currentlySelectedProject = Project()
    }

    fun toggleTheme(){
        darkTheme = !darkTheme
    }

    fun updateWeek(weeks: Long){
        currentlySelectedDate = currentlySelectedDate.plusWeeks(weeks)
        currentlySelectedDay  = currentlySelectedDate.dayOfWeek.value
        currentlySelectedWeek = currentlySelectedDate.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear())
    }

    fun saveTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        project: Project
    ) {
        validateTitle = title.isBlank()

        if (!validateTitle) {
            val task = Task(
                title = title,
                description = description,
                dueDate = dueDate,
                startTime = startTime,
                endTime = endTime,
                module = project.module,
                isDone = false,
                parentProject = project.title
            )
            projectList.forEach {
                if (it.title == project.title) {
                    it.tasks = it.tasks + task
                }
            }
        } else {
            validateTitle = true
        }
    }

    fun updateTask(
        currentlySelected: Task,
        title: String,
        description: String,
        dueDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        project: Project
    ) {
        validateTitle = title.isBlank()

        if (!validateTitle) {
            currentlySelected.title         = title
            currentlySelected.description   = description
            currentlySelected.dueDate       = dueDate
            currentlySelected.startTime     = startTime
            currentlySelected.endTime       = endTime
            currentlySelected.parentProject = project.title
        } else {
            validateTitle = true
        }
    }

    fun saveProject(
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        module: String,
        color: Color,
    ) {
        validateTitle = title.isBlank()

        if (!validateTitle) {
            val project = Project(
                title = title,
                startDate = startDate,
                endDate = endDate,
                tasks = arrayListOf(),
                module = Module(module, color),
                isDone = false,
            )
            println(projectList.size)
            projectList = projectList + project
            println(projectList.size)
        } else {
            validateTitle = true
        }
    }

    fun updateProject(
        currentlySelected: Project,
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        module: String,
        color: Color,
        ) {
            validateTitle = title.isBlank()

            if (!validateTitle) {
                currentlySelected.title       = title
                currentlySelected.startDate   = startDate
                currentlySelected.endDate     = endDate
                currentlySelected.module    = Module(module, color)
            } else {
                validateTitle = true
            }
    }
}