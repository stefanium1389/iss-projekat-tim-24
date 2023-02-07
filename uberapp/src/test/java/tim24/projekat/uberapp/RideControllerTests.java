package tim24.projekat.uberapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import tim24.projekat.uberapp.security.JwtTokenUtil;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RideControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Test
    public void postRide_withoutValidAuthorization_returnsForbidden() throws Exception {
     
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
				+ "      \"id\": 123,\r\n"
				+ "      \"email\": \"user@example.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"
				+ "  \"babyTransport\": true,\r\n"
				+ "  \"petTransport\": true,\r\n"
				+ "  \"scheduledTime\": \"2023-01-11T17:45:00Z\"\r\n"
				+ "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju bez tokena
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	@Test
    public void postRide_HappyFlow() throws Exception {
     
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
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));
		
	}
	@Test
    public void postRide_WhileHavsPendingRide_returnsBadRequest() throws Exception {
     
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
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci jos jednom kad imas vec
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}

}


