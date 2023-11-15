package fhnw.ws6c.taskplanner.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

class ConstructorTest {
    var project: Project by mutableStateOf(Project())
    private val c = Module("Ips", Color.Green)

    @Before
    fun loadRepository() {
        project = Project("Test Project", LocalDate.of(2022, Month.FEBRUARY, 5),   LocalDate.of(2022, Month.JUNE, 9), arrayListOf(
            Task("T0", "D0",    LocalDate.of(2022, Month.FEBRUARY, 10),  LocalTime.of(8 , 0), LocalTime.of(8 , 15), c, true, "Test Project"),
            Task("T1", "D1",    LocalDate.of(2022, Month.FEBRUARY, 11),  LocalTime.of(9 , 0), LocalTime.of(9 , 15), c, true, "Test Project"),
            Task("T2", "D2",    LocalDate.of(2022, Month.MARCH   , 12),  LocalTime.of(10, 0), LocalTime.of(10, 15), c, false, "Test Project"),
            Task("T3", "D3",    LocalDate.of(2022, Month.JUNE    , 13),  LocalTime.of(11, 0), LocalTime.of(11, 15), c, false, "Test Project"),
            Task("T4", "D4",    LocalDate.of(2022, Month.JULY    , 14),  LocalTime.of(12, 0), LocalTime.of(12, 15), c, false, "Test Project"),
        ), c, false)
    }

    @Test
    fun testCategory() {
        //given
        //when
        val c1 = project.module
        //then
        assertEquals("Ips", c1.title)
        assertEquals(Color.Green, c1.color)
    }

    @Test
    fun testTask() {
        //given
        //when
        val t1 = project.tasks[0]
        //then
        assertEquals("T0", t1.title)
        assertEquals("D0", t1.description)
        assertEquals(LocalDate.of(2022, Month.FEBRUARY, 10), t1.dueDate)
        assertEquals(LocalTime.of(8 , 0), t1.startTime)
        assertEquals(LocalTime.of(8 , 15), t1.endTime)
        assertTrue(t1.isDone)
    }

    @Test
    fun testProcess() {
        //given
        //when
        //then
        assertEquals("Test Project", project.title)
        assertEquals(LocalDate.of(2022, Month.FEBRUARY, 5), project.startDate)
        assertEquals(LocalDate.of(2022, Month.JUNE, 9), project.endDate)
        assertEquals(5, project.tasks.size)
        assertEquals("T4", project.tasks[4].title)
        assertEquals("Ips", project.module.title)
        assertFalse(project.isDone)
    }
}