package UserControllerTest;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.repos.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContent;

    @Autowired
    public UserControllerTest(WebApplicationContext webContent) {
        this.webContent = webContent;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContent).build();
    }


    @Test
    public void test_getUserById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
    }

}
