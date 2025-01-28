package com.chat.demo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.chat.demo.users.User;
import com.chat.demo.users.UserRepository;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Test
	@WithMockUser(roles = {"ADMIN"})
    public void testGetUsersEndpoint() throws Exception {

        String expectedJson = "[{\"id\":1,\"name\":\"hamza\",\"email\":\"hamimi@insea.ac.ma\",\"password\":\"h\",\"role\":\"ADMIN\"},{\"id\":2,\"name\":\"zakaria\",\"email\":\"zamimi@insea.ac.ma\",\"password\":\"z\",\"role\":\"USER\"},{\"id\":3,\"name\":\"ahmed\",\"email\":\"aamimi@insea.ac.ma\",\"password\":\"a\",\"role\":\"USER\"},{\"id\":4,\"name\":\"mohammed\",\"email\":\"mamimi@insea.ac.ma\",\"password\":\"m\",\"role\":\"ADMIN\"}]";
    
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(expectedJson));
    }

    @Test
@WithMockUser(roles = {"USER"})
public void testGetUsersEndpointWithUserRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
}

@Test
@WithMockUser

public void testIndexPage() throws Exception {

    String expectedMessage = "welcome";

    mockMvc.perform(MockMvcRequestBuilders.get("/index"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(expectedMessage));
}

@Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveUserEndpointWithAdminRole() throws Exception {

        String userJson = "{\"name\":\"John\",\"email\":\"john@example.com\",\"password\":\"password\",\"role\":\"USER\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/save-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    @WithMockUser(roles = {"USER"})
    public void testSaveUserEndpointWithUserRole() throws Exception {

        String userJson = "{\"name\":\"John\",\"email\":\"john@example.com\",\"password\":\"password\",\"role\":\"USER\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/save-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "hamimi@insea.ac.ma", roles ={ "USER","ADMIN"})
    public void testUserInfoEndpoint() throws Exception {

        User user = userRepository.findByEmail("hamimi@insea.ac.ma");

        mockMvc.perform(MockMvcRequestBuilders.get("/userinfo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(user.getRole()));
    }
}
