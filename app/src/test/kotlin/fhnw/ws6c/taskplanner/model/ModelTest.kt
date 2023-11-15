package fhnw.ws6c.taskplanner.model

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class ModelTest {
    var model = TaskPlannerModel()
    private val localDate: LocalDate = LocalDate.now()
    private val localTime: LocalTime = LocalTime.now()

    @Before
    fun loadRepository() {
        model.loadRepository()
    }

    @Test
    fun testSaveTask() {
        //given
        assertEquals(3, model.projectList.size)
        assertEquals(10, model.projectList[0].tasks.size)

        //when
        model.saveTask(
            title = "TEST",
            description = "desc",
            dueDate = localDate,
            startTime = localTime,
            endTime = localTime.plusHours(1),
            project = model.projectList[0]
        )

        //then
        assertEquals(3, model.projectList.size)
        assertEquals(11, model.projectList[0].tasks.size)
        assertEquals("TEST", model.projectList[0].tasks[10].title)
    }

    @Test
    fun testSaveProcess() {
        //given
        assertEquals(3, model.projectList.size)

        //when
        model.saveProject(
            title = "TEST",
            startDate = localDate,
            endDate = localDate.plusMonths(1),
            module = "NEWCAT",
            color = Color.Red
        )

        //then
        assertEquals(4, model.projectList.size)
        assertEquals("TEST", model.projectList[3].title)
    }
}