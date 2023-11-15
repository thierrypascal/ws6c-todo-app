package fhnw.ws6c.taskplanner.data

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

object Repository {
    private val c1 = Module("ws6c", Color.Blue)
    private val c2 = Module("LoR", Color.Red)
    private val c3 = Module("Ips", Color.Green)
    private val d: LocalDate = LocalDate.now()

    var projects = listOf(
        Project( "App erstellen", LocalDate.of(2022, Month.FEBRUARY, 1), LocalDate.of(2022, Month.JUNE, 6), arrayListOf(
            Task("High-Fi",     "High-Fi Prototyp erstellen, testen, Feedback einholen und weiter iterieren.", d, LocalTime.of(10, 0), LocalTime.of(11, 15), c1, true, "App erstellen"),
            Task("Stand-Up 1",  "Eine zweite, unglaublich wichtige Aufgabe.",   d,                          LocalTime.of(12, 0), LocalTime.of(13, 15), c1, false, "App erstellen"),
            Task("Stand-Up 2",  "Nicht vergessen! Daily Stand-Up.",             d.minusDays(4), LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 3",  "Nicht vergessen! Daily Stand-Up.",             d.minusDays(3), LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 4",  "Nicht vergessen! Daily Stand-Up.",             d.minusDays(2), LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 5",  "Nicht vergessen! Daily Stand-Up.",             d.minusDays(1), LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 6",  "Nicht vergessen! Daily Stand-Up.",             d,                          LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 7",  "Nicht vergessen! Daily Stand-Up.",             d.plusDays(1),     LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 8",  "Nicht vergessen! Daily Stand-Up.",             d.plusDays(2),     LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
            Task("Stand-Up 9",  "Nicht vergessen! Daily Stand-Up.",             d.plusDays(3).plusMonths(1),     LocalTime.of(8, 0), LocalTime.of(8, 15), c1, false, "App erstellen"),
        ), c1, false),
        Project("Project Two", LocalDate.of(2022, Month.FEBRUARY, 5), LocalDate.of(2022, Month.JUNE, 7), arrayListOf(
            Task("Lorem 0", "Lorem ipsum und so weiter...",        d,  LocalTime.of(10, 0), LocalTime.of(11, 15), c2, false,"Project Two"),
            Task("Lorem 1", "Lorem ipsum und so weiter...",        d.plusDays(1), LocalTime.of(12, 0), LocalTime.of(13, 15), c2, true,"Project Two"),
            Task("Lorem 2", "Lorem ipsum und so weiter...",        d.plusDays(2), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, true,"Project Two"),
            Task("Lorem 3", "Lorem ipsum und so weiter...",        d.plusDays(2), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, true,"Project Two"),
            Task("Lorem 4", "Lorem ipsum und so weiter...",        d.plusDays(3), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, true,"Project Two"),
            Task("Lorem 5", "Lorem ipsum und so weiter...",        d, LocalTime.of(8,  0), LocalTime.of(8,  15), c2, false,"Project Two"),
            Task("Lorem 6", "Lorem ipsum und so weiter...",        d.plusDays(1), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, false,"Project Two"),
            Task("Lorem 7", "Lorem ipsum und so weiter...",        d.plusDays(2), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, false,"Project Two"),
            Task("Lorem 8", "Lorem ipsum und so weiter...",        d.plusDays(6).plusMonths(2), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, false,"Project Two"),
            Task("Lorem 9", "Lorem ipsum und so weiter...",        d.plusDays(7).plusMonths(3), LocalTime.of(8,  0), LocalTime.of(8,  15), c2, false,"Project Two"),
        ), c2, false),
        Project("Project Three", LocalDate.of(2022, Month.FEBRUARY, 5), LocalDate.of(2022, Month.JUNE, 9), arrayListOf(
            Task("Ipsum 0", "Lorem ipsum und so weiter...",        d.minusDays(2),  LocalTime.of(10, 0), LocalTime.of(11, 15), c3, true, "Project Three"),
            Task("Ipsum 1", "Lorem ipsum und so weiter...",        d.minusDays(1), LocalTime.of(12, 0), LocalTime.of(13, 15), c3, true, "Project Three"),
            Task("Ipsum 2", "Lorem ipsum und so weiter...",        d, LocalTime.of(8,  0), LocalTime.of(8,  15), c3, true, "Project Three"),
            Task("Ipsum 3", "Lorem ipsum und so weiter...",        d.plusDays(5), LocalTime.of(8,  0), LocalTime.of(8,  15), c3, true, "Project Three"),
            Task("Ipsum 4", "Lorem ipsum und so weiter...",        d.plusDays(6), LocalTime.of(15, 0), LocalTime.of(16, 15), c3, true, "Project Three"),
            Task("Ipsum 5", "Lorem ipsum und so weiter...",        d.plusDays(7), LocalTime.of(15, 0), LocalTime.of(17, 15), c3, true, "Project Three"),
            Task("Ipsum 6", "Lorem ipsum und so weiter...",        d, LocalTime.of(15, 0), LocalTime.of(16, 15), c3, false, "Project Three"),
            Task("Ipsum 7", "Lorem ipsum und so weiter...",        d.plusDays(3), LocalTime.of(15, 0), LocalTime.of(16, 15), c3, false, "Project Three"),
        ), c3, false),
    )
}