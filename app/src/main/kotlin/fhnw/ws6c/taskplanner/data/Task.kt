package fhnw.ws6c.taskplanner.data

import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

class Task(
    var title: String,
    var description: String,
    var dueDate: LocalDate,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var module: Module,
    var isDone: Boolean,
    var parentProject: String,
) {

    //empty constructor
    constructor() : this(
        title = "",
        description = "",
        dueDate = LocalDate.of(2022, Month.JANUARY, 1),
        startTime = LocalTime.of(0, 0),
        endTime = LocalTime.of(0, 0),
        module = Module(),
        isDone = false,
        parentProject = "",
    )

    override fun toString(): String {
        return "Task (titel = '$title', beschreibung = '$description', dueDate = '$dueDate', startTime = '$startTime', endTime = '$endTime', parentProject = '$parentProject')"
    }
}