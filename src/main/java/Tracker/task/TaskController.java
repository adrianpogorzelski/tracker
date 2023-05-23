package Tracker.task;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    final private TaskService taskService;

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/tasks/index");

        List<Task> tasks = taskService.findAll();
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }
}
