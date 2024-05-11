package cleancodecleanarchitecture16.account;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = AccountApplication.class)
public abstract class IntegrationTest {
}
