package Tracker.dashboard;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Tracker.project.Project;
import Tracker.project.ProjectService;
import Tracker.task.Task;
import Tracker.task.TaskService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;
    private final TaskService taskService;
    
    @GetMapping
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        modelAndView.addObject("principal", principal);

        Iterable<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);

        Iterable<Task> tasks = taskService.findAll();
        modelAndView.addObject("tasks", tasks);

        return modelAndView;
    }
}