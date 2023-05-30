package Tracker.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Tracker.person.Person;
import Tracker.person.PersonService;
import Tracker.project.Project;
import Tracker.project.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    final private TaskService taskService;
    final private ProjectService projectService;
    final private PersonService personService;

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/tasks/index");
        List<Task> tasks = taskService.findAll();
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }

    @GetMapping("/new")
    ModelAndView newTask() {
        ModelAndView modelAndView = new ModelAndView("/tasks/new");

        Task task = new Task();
        modelAndView.addObject("task", task);

        List<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);

        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);


        modelAndView.addObject("taskTypes", TaskType.values());

        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveTask(@ModelAttribute @Valid Task task, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/tasks");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("tasks/new");
            modelAndView.addObject("task", task);
            return modelAndView;
        }

        task.setDateCreated(LocalDateTime.now());
        task.setTaskStatus(TaskStatus.BACKLOG);
        taskService.save(task);
        return modelAndView;
    }}
