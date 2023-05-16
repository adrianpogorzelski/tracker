package Tracker.project;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    final private ProjectService projectService;
    
    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/projects/index");
        List<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);
        return modelAndView;
    }
}
