package tim24.projekat.uberapp.controller;

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
public class FavoriteRideTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String requestJson = "{\r\n"
			+ "  \"favoriteName\": \"Home - to - Work\",\r\n"
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
			+ "  \"petTransport\": false\r\n"
			+ "}";
	
	@Test
	public void postFavorite_HappyFlow() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	@Test
	public void postFavorite_Post10Favorites_returnsOk() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		for(int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
	        		.header("Authorization", "Bearer " + jwt)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestJson))
	                .andExpect(MockMvcResultMatchers.status().isOk());
		}
        
	}
	@Test
	public void postFavorite_Post11thFavorite_returnsBadRequest() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		for(int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
	        		.header("Authorization", "Bearer " + jwt)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestJson))
	                .andExpect(MockMvcResultMatchers.status().isOk());
		}
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
	}
	
	@Test
	public void postFavorite_noAuthorization_returnsForbidden() throws Exception {
				
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites")) //post omiljenu voznju
                .andExpect(MockMvcResultMatchers.status().isForbidden());        
	}

	@Test
	public void getFavorite_HappyFlow() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		for(int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
	        		.header("Authorization", "Bearer " + jwt)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestJson))
	                .andExpect(MockMvcResultMatchers.status().isOk());
		}
		mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/favorites") //get omiljenu voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(10));

        
	}
	
	@Test
	public void getFavorite_WhenNoFavorites_returnsOk() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/favorites") //get omiljenu voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk());

        
	}
	
	@Test
	public void getFavorite_noAuthorization_returnsForbidden() throws Exception {
				
		mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/favorites")) //get omiljenu voznju
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        
	}
	
	@Test
	public void deleteFavorite_HappyFlow() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		for(int i = 0; i < 10; i++) {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/favorites") //post omiljenu voznju
	        		.header("Authorization", "Bearer " + jwt)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestJson))
	                .andExpect(MockMvcResultMatchers.status().isOk());
		}
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/ride/favorites/1") //delete omiljenu voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        
	}
	
	@Test
	public void deleteFavorite_WhenNoFavorites_returnsNotFound() throws Exception {
		
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/ride/favorites/1") //get omiljenu voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        
	}
	
	@Test
	public void deleteFavorite_noAuthorization_returnsForbidden() throws Exception {
				
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/ride/favorites/1")) //get omiljenu voznju
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        
	}
	
	
}
