package Tracker.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Tracker.mail.Mail;
import Tracker.mail.MailService;
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
    final private MessageSource messageSource;
    final private MailService mailService;

    @GetMapping
    ModelAndView index(@ModelAttribute TaskFilter filter) {
        ModelAndView modelAndView = new ModelAndView("/tasks/index");
        
        List<Task> tasks = taskService.findAll(filter.buildSpecification());
        modelAndView.addObject("tasks", tasks);

        modelAndView.addObject("filter", filter);

        List<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);

        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        
        modelAndView.addObject("taskStatus", TaskStatus.values());

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
    
            List<Project> projects = projectService.findAll();
            modelAndView.addObject("projects", projects);
    
            List<Person> people = personService.findAll();
            modelAndView.addObject("people", people);
    
            modelAndView.addObject("taskTypes", TaskType.values());
    
            return modelAndView;
        }
    
        task.setDateCreated(LocalDateTime.now());
        task.setTaskStatus(TaskStatus.BACKLOG);

        Mail mail = new Mail();
        mail.setRecipient(task.getAssignee().getEmail());
        mail.setSubject("You have a new task");
        mail.setContent("New task: " + task.getName());
        mailService.sendMail(mail);
        
        taskService.save(task);

        return modelAndView;
    }
    

    @GetMapping("/{id}")
    ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/tasks/details");
        Optional<Task> taskOptional = taskService.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            modelAndView.addObject("task", task);
        } else {
            String errorMessage = messageSource.getMessage("error.invalidProjectId", null, LocaleContextHolder.getLocale());
            modelAndView.addObject("errorMessage", errorMessage);
        }
        
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/tasks/edit");
        Optional<Task> taskOptional = taskService.findById(id);
        
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            modelAndView.addObject("task", task);

            List<Person> people = personService.findAll();
            modelAndView.addObject("people", people);

            List<Project> projects = projectService.findAll();
            modelAndView.addObject("projects", projects);

            modelAndView.addObject("taskTypes", TaskType.values());

        } else {
            String errorMessage = messageSource.getMessage("error.invalidProjectId", null, LocaleContextHolder.getLocale());
            modelAndView.addObject("errorMessage", errorMessage);
        }
        
        return modelAndView;
    }
    
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute("task") Task updatedTask) {
        Optional<Task> taskOptional = taskService.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setAssignee(updatedTask.getAssignee());

            Project selectedProject = updatedTask.getProject();
            Optional<Project> projectOptional = projectService.findById(selectedProject.getId());
            Project project = projectOptional.get();
            task.setProject(project);
            
            taskService.save(task);


        }

        return "redirect:/tasks";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/change-status")
    public String changeStatus(@PathVariable Long id, @RequestParam("direction") String direction) {
        Task task = taskService.findById(id).orElse(null);

        if (task != null) {
            TaskStatus[] taskStatuses = TaskStatus.values();
            TaskStatus currentStatus = task.getTaskStatus();

            int currentIndex = currentStatus.ordinal();
            int newIndex;

            if (direction.equals("next")) {
                newIndex = (currentIndex + 1) % taskStatuses.length; 
            } else {
                newIndex = (currentIndex - 1 + taskStatuses.length) % taskStatuses.length;
            }

            TaskStatus newStatus = taskStatuses[newIndex];
            task.setTaskStatus(newStatus);

            taskService.save(task);
        }

        Long projectId = task.getProject().getId();
        return "redirect:/projects/" + projectId;
    }

    // Set the task to disabled - does NOT REMOVE related users and projects
    @GetMapping("/{id}/disable")
    public String disable(@PathVariable Long id, @ModelAttribute("project") Task updatedTask) {
        Optional<Task> optional = taskService.findById(id);

        if (optional.isPresent()) {
            Task task = optional.get();
            task.setEnabled(false);
            taskService.save(task);

            Mail mail = new Mail();
            mail.setRecipient(task.getAssignee().getEmail());
            mail.setSubject("Your task is closed");
            mail.setContent("Closed task: " + task.getName());
            mailService.sendMail(mail);
            
            taskService.save(task);
        }

        return "redirect:/tasks"; 
    }

}