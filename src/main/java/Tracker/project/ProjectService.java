package Tracker.project;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    final private ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    
}
