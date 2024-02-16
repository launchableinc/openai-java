package com.launchableinc.openai.service;

import com.launchableinc.openai.model.Model;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ModelTest {


	static final private String token = System.getenv("OPENAI_TOKEN");

	static OpenAiService service;

	@BeforeAll
	static void setup() {
		Assumptions.assumeTrue(token != null && !token.isEmpty());
		service = new OpenAiService(token);
	}

	@Test
	void listModels() {
		List<Model> models = service.listModels();

		assertFalse(models.isEmpty());
	}

	@Test
	void getModel() {
		Model model = service.getModel("babbage-002");

		assertEquals("babbage-002", model.id);
		assertEquals("system", model.ownedBy);
	}
}
