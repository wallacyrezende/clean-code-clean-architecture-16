package cleancodecleanarchitecture16.ride;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = RideApplication.class)
public abstract class IntegrationTest {
}
