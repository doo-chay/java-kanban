package ru.practicum.gym;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Timetable timetable = new Timetable();

        Group childAcrobatics = new Group("Акробатика для детей", Age.CHILD, 60);
        Group adultStretching = new Group("Растяжка для взрослых", Age.ADULT, 90);
        Group childGymnastics = new Group("Гимнастика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        Coach coach3 = new Coach("Олегов", "Олег", "Олегович");

        TrainingSession session1 = new TrainingSession(
                childAcrobatics,
                coach1,
                DayOfWeek.MONDAY,
                new TimeOfDay(10, 30)
        );

        TrainingSession session2 = new TrainingSession(
                adultStretching,
                coach2,
                DayOfWeek.MONDAY,
                new TimeOfDay(12, 30)
        );

        TrainingSession session3 = new TrainingSession(
                childGymnastics,
                coach3,
                DayOfWeek.MONDAY,
                new TimeOfDay(12, 30)
        );

        TrainingSession session4 = new TrainingSession(
                childAcrobatics,
                coach1,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(14, 30)
        );

        TrainingSession session5 = new TrainingSession(
                adultStretching,
                coach2,
                DayOfWeek.FRIDAY,
                new TimeOfDay(18, 30)
        );

        TrainingSession session6 = new TrainingSession(
                childAcrobatics,
                coach1,
                DayOfWeek.FRIDAY,
                new TimeOfDay(10, 30)
        );

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);
        timetable.addNewTrainingSession(session5);
        timetable.addNewTrainingSession(session6);

        System.out.println("Тренировки за понедельник");
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        printTrainingSessions(mondaySessions);

        System.out.println();
        System.out.println("Тренировки за вторник");
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        printTrainingSessions(tuesdaySessions);

        System.out.println();
        System.out.println("Тренировки в понедельник в 12:30");
        List<TrainingSession> mondayAtTwelve = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(12, 30)
        );
        printTrainingSessions(mondayAtTwelve);

        System.out.println();
        System.out.println("Тренировки в понедельник в 15:30");
        List<TrainingSession> mondayAtFifteen = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(15, 30)
        );
        printTrainingSessions(mondayAtFifteen);

        System.out.println();
        System.out.println("Количество тренировок по тренерам");
        List<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        printCountByCoaches(countByCoaches);
    }

    private static void printTrainingSessions(List<TrainingSession> sessions) {
        if (sessions.isEmpty()) {
            System.out.println("Тренировок нет.");
            return;
        }

        for (TrainingSession session : sessions) {
            System.out.println(
                    formatTime(session.getTimeOfDay()) + " | "
                            + session.getGroup().getTitle() + " | "
                            + formatCoach(session.getCoach())
            );
        }
    }

    private static void printCountByCoaches(List<CounterOfTrainings> counters) {
        if (counters.isEmpty()) {
            System.out.println("Тренировок нет.");
            return;
        }

        for (CounterOfTrainings counter : counters) {
            System.out.println(
                    formatCoach(counter.getCoach()) + ": " + counter.getCount()
            );
        }
    }

    private static String formatTime(TimeOfDay timeOfDay) {
        return timeOfDay.getHours() + ":" + timeOfDay.getMinutes();
    }

    private static String formatCoach(Coach coach) {
        return coach.getSurname() + " "
                + coach.getName() + " "
                + coach.getMiddleName();
    }
}
