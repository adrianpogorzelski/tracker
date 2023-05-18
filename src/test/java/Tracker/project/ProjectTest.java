package Tracker.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectTest {
    
    @Test
    public void gettersAndSetters() {
        // Create empty project
        Project project = new Project();

        // Test values
        Long testId = 1L;
        String testName = "Test name";
        String testDescription = "Test description";

        // Check if can get values
        assertNull(project.getId());
        assertNull(project.getName());
        assertNull(project.getDescription());

        // Check if can get a non-existant value
        assertThrows(NoSuchMethodException.class, () -> {
            Method someFieldGetter = Project.class.getDeclaredMethod("getSomeField");
        });

        // Check if setting test values and getting them works
        project.setId(testId);
        project.setName(testName);
        project.setDescription(testDescription);

        assertEquals(project.getId(), testId);
        assertEquals(project.getName(), testName);
        assertEquals(project.getDescription(), testDescription);
    }

    @Test
    public void invalidDataTypesAssignment() {
        // Create empty project
        Project project = new Project();

        // Try to set id as String
        String testId = "one";
        assertThrows(NumberFormatException.class, () -> {
            project.setId(Long.valueOf(testId));
        });

        // Try to set name and description as Object
        Object testObject = new Object();
        assertThrows(ClassCastException.class, () -> {
            project.setName((String) testObject);
        });
        assertThrows(ClassCastException.class, () -> {
            project.setName((String) testObject);
        });
    }
}