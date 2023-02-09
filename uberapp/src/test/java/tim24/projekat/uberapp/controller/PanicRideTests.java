package tim24.projekat.uberapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class PanicRideTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String requestJson = "{\r\n"
			+ "  \"locations\": [\r\n"
			+ "    {\r\n"
			+ "      \"departure\": {\r\n"
			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
			+ "        \"latitude\": 45.267136,\r\n"
			+ "        \"longitude\": 19.833549\r\n"
			+ "      },\r\n"
			+ "      \"destination\": {\r\n"
			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
			+ "        \"latitude\": 45.267136,\r\n"
			+ "        \"longitude\": 19.833549\r\n"
			+ "      }\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"passengers\": [\r\n"
			+ "    {\r\n"
			+ "      \"id\": 2,\r\n"
			+ "      \"email\": \"stefanium@mail.com\"\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"vehicleType\": \"STANDARD\",\r\n"
			+ "  \"babyTransport\": false,\r\n"
			+ "  \"petTransport\": false,\r\n"
			+ "  \"scheduledTime\": null\r\n"
			+ "}";
	
	@BeforeEach
    public void setUpActiveRide() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/start") //start voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("STARTED"));
		
	}
	
	@Test
	public void panicRide_HappyFlow() throws Exception {
		
		String requestJson = "{\r\n"
				+ "  \"reason\": \"Driver is constantly listeting to bad music.\"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
                
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/panic") //panik voznju
        		.header("Authorization", "Bearer " + jwt)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("Driver is constantly listeting to bad music."));

	}
	
	@Test
	public void panicRide_AdminAuthroized_returnsForbidden() throws Exception {
		
		String requestJson = "{\r\n"
				+ "  \"reason\": \"Driver is constantly listeting to bad music.\"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("admin");
               
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/panic") //panik voznju
        		.header("Authorization", "Bearer " + jwt)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

	}
	
	@Test
	public void panicRide_withoutValidAuthorization_returnsForbidden() throws Exception {
		
		String requestJson = "{\r\n"
				+ "  \"reason\": \"Driver is constantly listeting to bad music.\"\r\n"
				+ "}";
		               
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/panic") //panik voznju
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

	}
	
	@Test
	public void panicRide_notStateStarted_returnsBadRequest() throws Exception {
		
		String requestJson = "{\r\n"
				+ "  \"reason\": \"Driver is constantly listeting to bad music.\"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/end") //zavrsi voznju da nije started
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk());
               
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/panic") //panik voznju
        		.header("Authorization", "Bearer " + jwt)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	@Test
	public void panicRide_invalidRideId_returnsNotFound() throws Exception {
		
		String requestJson = "{\r\n"
				+ "  \"reason\": \"Driver is constantly listeting to bad music.\"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
                
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/123/panic") //panik voznju
        		.header("Authorization", "Bearer " + jwt)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

	}

}
