package Tracker.project;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
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

    @GetMapping("/new")
    ModelAndView newProject() {
        ModelAndView modelAndView = new ModelAndView("/projects/new");
        Project project = new Project();
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Project project, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/projects");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("projects/new");
            modelAndView.addObject("project", project);
            return modelAndView;
        }

        project.setDateCreated(LocalDateTime.now());
        projectService.save(project);
        return modelAndView;
    }
}
