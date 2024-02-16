package com.launchableinc.openai.service;

import com.launchableinc.openai.OpenAiHttpException;
import com.launchableinc.openai.edit.EditRequest;
import com.launchableinc.openai.edit.EditResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EditTest {

	String token = System.getenv("OPENAI_TOKEN");
	com.launchableinc.openai.service.OpenAiService service = new OpenAiService(token);

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
