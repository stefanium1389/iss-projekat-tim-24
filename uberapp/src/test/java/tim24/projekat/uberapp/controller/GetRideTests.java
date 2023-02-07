package tim24.projekat.uberapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import tim24.projekat.uberapp.security.JwtTokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:testdata.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GetRideTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Test
    public void getRide_invalidAuthorization_returnsForbidden() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/123"))
	                .andExpect(MockMvcResultMatchers.status().isForbidden()); 
		
    }
	
	@Test 																	
    public void getRide_invalidId_returnsNotFound() throws Exception {
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/123")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
		
    }
	@Test
    public void getRide_HappyFlow() throws Exception {
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/1")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isOk()); 
		
    }

}
