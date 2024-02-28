package com.launchableinc.openai.service;

import com.launchableinc.openai.OpenAiHttpException;
import com.launchableinc.openai.edit.EditRequest;
import com.launchableinc.openai.edit.EditResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EditTest {

	static final private String token = System.getenv("OPENAI_TOKEN");

	static OpenAiService service;

	@BeforeAll
	static void setup() {
		Assumptions.assumeTrue(token != null && !token.isEmpty());
		service = new OpenAiService(token);
	}

	@Test
	void edit() throws OpenAiHttpException {
		EditRequest request = EditRequest.builder()
				.model("text-davinci-edit-001")
				.input("What day of the wek is it?")
				.instruction("Fix the spelling mistakes")
				.build();

		EditResult result = service.createEdit(request);
		assertNotNull(result.getChoices().get(0).getText());
	}
}
