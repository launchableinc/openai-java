package com.launchableinc.openai.service;

import com.launchableinc.openai.moderation.Moderation;
import com.launchableinc.openai.moderation.ModerationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ModerationTest {

	String token = System.getenv("OPENAI_TOKEN");
	com.launchableinc.openai.service.OpenAiService service = new OpenAiService(token);

	@Test
	void createModeration() {
		ModerationRequest moderationRequest = ModerationRequest.builder()
				.input("I want to kill him")
				.model("text-moderation-latest")
				.build();

		Moderation moderationScore = service.createModeration(moderationRequest).getResults().get(0);

		assertTrue(moderationScore.isFlagged());
	}
}
