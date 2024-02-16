package com.launchableinc.openai.service;

import com.launchableinc.openai.embedding.Embedding;
import com.launchableinc.openai.embedding.EmbeddingRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class EmbeddingTest {

	String token = System.getenv("OPENAI_TOKEN");
	com.launchableinc.openai.service.OpenAiService service = new OpenAiService(token);

	@Test
	void createEmbeddings() {
		EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
				.model("text-embedding-ada-002")
				.input(Collections.singletonList("The food was delicious and the waiter..."))
				.build();

		List<Embedding> embeddings = service.createEmbeddings(embeddingRequest).getData();

		assertFalse(embeddings.isEmpty());
		assertFalse(embeddings.get(0).getEmbedding().isEmpty());
	}
}
