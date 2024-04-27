package cleancodecleanarchitecture16.course;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = CourseApplication.class)
public abstract class IntegrationTest {
}
