package Tracker.task;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    final private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
