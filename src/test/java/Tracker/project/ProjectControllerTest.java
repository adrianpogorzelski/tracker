/* 
CAN'T GET TESTS TO WORK


package Tracker.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjectControllerTest {

    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectController projectController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    
    @Test
    @WithMockUser(roles = "USER")
    public void cantAddProjectWithEmptyName() throws Exception {
        Project project = new Project();
        mockMvc.perform(post("/projects/save")
            .flashAttr("project", project))
            .andExpect(status().isBadRequest());
    }
}
 */