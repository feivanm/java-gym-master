package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    //Добавлен список
    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        timetable.computeIfAbsent(day, k -> new TreeMap<>())
                .computeIfAbsent(time, k -> new ArrayList<>())
                .add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> sessions : dayTimetable.values()) {
            result.addAll(sessions);
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> sessions = dayTimetable.get(timeOfDay);
        if (sessions == null) {
            return Collections.emptyList();
        }
        return sessions;
    }

    public Collection<Coach> getCountByCoaches() {
        Map<Coach, Integer> countByCoaches = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable : timetable.values()) {
            for (List<TrainingSession> sessions : dayTimetable.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    countByCoaches.merge(coach, 1, Integer::sum);
                }
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
