package com.launchableinc.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.launchableinc.openai.audio.TranscriptionResult;
import com.launchableinc.openai.audio.TranslationResult;
import com.launchableinc.openai.completion.chat.ChatCompletionRequest;
import com.launchableinc.openai.completion.chat.ChatCompletionResult;
import com.launchableinc.openai.edit.EditRequest;
import com.launchableinc.openai.edit.EditResult;
import com.launchableinc.openai.embedding.EmbeddingRequest;
import com.launchableinc.openai.embedding.EmbeddingResult;
import com.launchableinc.openai.engine.Engine;
import com.launchableinc.openai.file.File;
import com.launchableinc.openai.fine_tuning.FineTuningEvent;
import com.launchableinc.openai.fine_tuning.FineTuningJob;
import com.launchableinc.openai.fine_tuning.FineTuningJobRequest;
import com.launchableinc.openai.finetune.FineTuneEvent;
import com.launchableinc.openai.finetune.FineTuneResult;
import com.launchableinc.openai.image.ImageResult;
import com.launchableinc.openai.messages.Message;
import com.launchableinc.openai.model.Model;
import com.launchableinc.openai.moderation.ModerationRequest;
import com.launchableinc.openai.moderation.ModerationResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

	@ParameterizedTest
	@ValueSource(classes = {
			ChatCompletionRequest.class,
			ChatCompletionResult.class,
			DeleteResult.class,
			EditRequest.class,
			EditResult.class,
			EmbeddingRequest.class,
			EmbeddingResult.class,
			Engine.class,
			File.class,
			FineTuneEvent.class,
			FineTuneResult.class,
			FineTuningEvent.class,
			FineTuningJob.class,
			FineTuningJobRequest.class,
			ImageResult.class,
			TranscriptionResult.class,
			TranslationResult.class,
			Message.class,
			Model.class,
			ModerationRequest.class,
			ModerationResult.class
	})
	void objectMatchesJson(Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		String path = "src/test/resources/fixtures/" + clazz.getSimpleName() + ".json";
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String json = new String(bytes);

		String actual = mapper.writeValueAsString(mapper.readValue(json, clazz));

		// Convert to JsonNodes to avoid any json formatting differences
		assertEquals(mapper.readTree(json), mapper.readTree(actual));
	}
}
