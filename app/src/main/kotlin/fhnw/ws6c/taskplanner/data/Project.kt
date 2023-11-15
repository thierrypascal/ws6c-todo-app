package fhnw.ws6c.taskplanner.data

import java.time.LocalDate
import java.time.Month


class Project(
    var title: String,
    var startDate: LocalDate,
    var endDate: LocalDate,
    var tasks: List<Task>,
    var module: Module,
    var isDone: Boolean,
) {

    //empty constructor
    constructor() : this(
        title = "",
        startDate = LocalDate.of(2022, Month.JANUARY, 1),
        endDate = LocalDate.of(2022, Month.JANUARY, 1),
        tasks = arrayListOf<Task>(),
        module = Module(),
        isDone = false,
    )

    fun getProgress(): Float {
        return (getOpenTasks().toDouble() / tasks.size.toDouble()).toFloat()
    }

    fun getOpenTasks(): Int {
        var count = 0
        tasks.forEach { task -> if (!task.isDone) {count++} }
        return count
    }
}
