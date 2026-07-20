package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        timetable.computeIfAbsent(day, k -> new TreeMap<>()).put(time, trainingSession);
    }

    public Collection<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, TrainingSession> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return Collections.emptyList();
        }
        return dayTimetable.values();
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, TrainingSession> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return null;
        }
        return dayTimetable.get(timeOfDay);
    }

    public Collection<Coach> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();
        for (TreeMap<TimeOfDay, TrainingSession> dayTimetable : timetable.values()) {
            for (TrainingSession session : dayTimetable.values()) {
                Coach coach = session.getCoach();
                countByCoaches.merge(coach, 1, Integer::sum);
            }
        }
        List<Map.Entry<Coach, Integer>> sorted = new ArrayList<>(countByCoaches.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());
        List<Coach> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : sorted) {
            result.add(entry.getKey());
        }
        return result;
    }
}
