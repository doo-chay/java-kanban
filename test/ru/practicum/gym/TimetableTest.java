package ru.practicum.gym;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {
    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondaySessions.size());
        assertEquals(singleTrainingSession, mondaySessions.get(0));

        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(
                groupAdult,
                coach,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );
        TrainingSession thursdayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)
        );
        TrainingSession saturdayChildTrainingSession = new TrainingSession(
                groupChild,
                coach,
                DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0)
        );

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondaySessions.size());
        assertEquals(mondayChildTrainingSession, mondaySessions.get(0));

        assertEquals(2, thursdaySessions.size());
        assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));

        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)
        );

        assertEquals(1, sessionsAt13.size());
        assertEquals(singleTrainingSession, sessionsAt13.get(0));

        assertTrue(sessionsAt14.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Растяжка для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        assertEquals(2, sessions.size());
        assertEquals(session1, sessions.get(0));
        assertEquals(session2, sessions.get(1));
    }

    @Test
    void testGetTrainingSessionsForEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> mondayAtTen = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)
        );

        assertTrue(mondaySessions.isEmpty());
        assertTrue(mondayAtTen.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDaySortedByTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach = new Coach("Сидоров", "Алексей", "Петрович");

        TrainingSession session1 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(15, 0));
        TrainingSession session2 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession session3 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

        assertEquals(3, sessions.size());
        assertEquals(session2, sessions.get(0));
        assertEquals(session3, sessions.get(1));
        assertEquals(session1, sessions.get(2));
    }
}
