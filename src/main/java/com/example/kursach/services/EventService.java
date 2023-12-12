package com.example.kursach.services;

import com.example.kursach.models.Book;
import com.example.kursach.models.Event;
import com.example.kursach.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        ArrayList<Event> result = new ArrayList<>();
        event.ifPresent(result::add);
        return result;
    }

    public void editEvent(int id, String name, LocalDate date, LocalTime time, String place, String language, String description) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setName(name);
        event.setDate(date);
        event.setTime(time);
        event.setPlace(place);
        event.setLanguage(language);
        event.setDescription(description);
        eventRepository.save(event);
    }

    public void deleteEvent(int id) {
        Event event = eventRepository.findById(id).orElseThrow();
        eventRepository.delete(event);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> searchEventBy(String searchBy, String value) {
        Iterable<Event> events = eventRepository.findAll();
        List<Event> result = new ArrayList<>();
        switch (searchBy) {
            case "По названию": {
                for (Event event : events) {
                    if (event.getName().toLowerCase().contains(value.toLowerCase())) {
                        result.add(event);
                    }
                }
                break;
            }
            case "По языку": {
                for (Event event : events) {
                    if (event.getLanguage().toLowerCase().contains(value.toLowerCase())) {
                        result.add(event);
                    }
                }
                break;
            }
        }
        return result;
    }

    public List<Event> sortEventBy(String sortValue, List<Event> events) {
        switch (sortValue) {
            case "По названию": {
                Collections.sort(events, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()) > 1 ? 1 : s1.getName().compareToIgnoreCase(s2.getName()) < 1 ? -1 : 0);
                break;
            }
            case "По дате": {
                Collections.sort(events, (s1, s2) -> s1.getDate().compareTo(s2.getDate()) > 1 ? 1 : s1.getDate().compareTo(s2.getDate()) < 1 ? -1 : 0);
                break;
            }
        }
        return events;
    }
}
