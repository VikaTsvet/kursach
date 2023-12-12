package com.example.kursach.controllers;

import com.example.kursach.models.Book;
import com.example.kursach.models.Event;
import com.example.kursach.models.User;
import com.example.kursach.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/events")
    public String allEvents(@AuthenticationPrincipal User user, Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("user", user);
        return "events";
    }

    @PostMapping("/events")
    public String searchAndSortEvents(@AuthenticationPrincipal User user, @RequestParam String searchBy,
                                      @RequestParam String value, @RequestParam String sortBy, Model model) {
        if (!value.isEmpty()) {
            List<Event> events = eventService.searchEventBy(searchBy, value);
            model.addAttribute("events", events);
        } else {
            List<Event> allEvents = eventService.findAll();
            List<Event> events = eventService.sortEventBy(sortBy, allEvents);
            model.addAttribute("events", events);
        }
        model.addAttribute("user", user);
        return "events";
    }

    @GetMapping("/events/add")
    public String addEvent(Model model) {
        return "events-add";
    }

    @PostMapping("/events/add")
    public String addEventPost(@RequestParam String name, @RequestParam LocalDate date, @RequestParam LocalTime time,
                               @RequestParam String place, @RequestParam String language,
                               @RequestParam String description, Model model) {
        Event event = new Event(name, date, time, place, language, description);
        eventService.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{id}")
    public String eventInfo(@AuthenticationPrincipal User user, @PathVariable(value = "id") int id, Model model) {
        if (!eventService.existsById(id)) {
            return "redirect:/events";
        }
        List<Event> result = eventService.findById(id);
        model.addAttribute("event", result);
        model.addAttribute("user", user);
        return "event-info";
    }

    @GetMapping("/events/{id}/edit")
    public String editEvent(@PathVariable(value = "id") int id, Model model) {
        if (!eventService.existsById(id)) {
            return "redirect:/events";
        }
        List<Event> result = eventService.findById(id);
        model.addAttribute("event", result);
        return "event-edit";
    }

    @PostMapping("/events/{id}/edit")
    public String editEventPost(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam LocalDate date,
                                @RequestParam LocalTime time, @RequestParam String place, @RequestParam String language,
                                @RequestParam String description, Model model) {
        eventService.editEvent(id, name, date, time, place, language, description);
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/delete")
    public String deleteEventPost(@PathVariable(value = "id") int id, Model model) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}
