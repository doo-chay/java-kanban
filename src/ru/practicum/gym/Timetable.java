package ru.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);

        if (!trainingsForDay.containsKey(timeOfDay)) {
            trainingsForDay.put(timeOfDay, new ArrayList<>());
        }

        List<TrainingSession> trainingsForTime = trainingsForDay.get(timeOfDay);
        trainingsForTime.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();

        if (!timetable.containsKey(dayOfWeek)) {
            return result;
        }

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);

        for (TimeOfDay timeOfDay : trainingsForDay.navigableKeySet()) {
            result.addAll(trainingsForDay.get(timeOfDay));
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);

        if (!trainingsForDay.containsKey(timeOfDay)) {
            return new ArrayList<>();
        }

        return trainingsForDay.get(timeOfDay);


    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay : timetable.values()) {
            for (List<TrainingSession> trainingsForTime : trainingsForDay.values()) {
                for (TrainingSession trainingSession : trainingsForTime) {
                    Coach coach = trainingSession.getCoach();

                    if (counts.containsKey(coach)) {
                        counts.put(coach, counts.get(coach) + 1);
                    } else {
                        counts.put(coach, 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : counts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort(new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings first, CounterOfTrainings second) {
                return second.getCount() - first.getCount();
            }
        });

        return result;
    }
}
