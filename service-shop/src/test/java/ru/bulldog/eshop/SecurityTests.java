package ru.bulldog.eshop;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void securityAccessAllowedTest() throws Exception {
		mockMvc.perform(get("/api/v1/products/all"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	public void securityAccessDeniedTest() throws Exception {
		mockMvc.perform(get("/api/v1/orders"))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void securityTokenTest() throws Exception {
		String jsonRequest = "{\n" +
				"\t\"username\": \"root\",\n" +
				"\t\"password\": \"qwerty\"\n" +
				"}";
		MvcResult result = mockMvc.perform(
						post("/login")
								.content(jsonRequest)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").exists())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		JsonObject contentJson = new Gson().fromJson(content, JsonObject.class);
		String token = contentJson.get("token").getAsString();

		mockMvc.perform(get("/api/v1/orders")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}
}
