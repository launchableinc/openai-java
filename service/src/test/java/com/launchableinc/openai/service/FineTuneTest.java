package com.launchableinc.openai.service;

import com.launchableinc.openai.finetune.FineTuneEvent;
import com.launchableinc.openai.finetune.FineTuneRequest;
import com.launchableinc.openai.finetune.FineTuneResult;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Deprecated
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FineTuneTest {


	static final private String token = System.getenv("OPENAI_TOKEN");

	static OpenAiService service;

	static String fileId;
	static String fineTuneId;

	@BeforeAll
	static void setup() throws Exception {
		Assumptions.assumeTrue(token != null && !token.isEmpty());

		service = new OpenAiService(token);
		fileId = service.uploadFile("fine-tune", "src/test/resources/fine-tuning-data.jsonl").getId();

		// wait for file to be processed
		TimeUnit.SECONDS.sleep(10);
	}

	@AfterAll
	static void teardown() {
		Assumptions.assumeTrue(token != null && !token.isEmpty());
		service.deleteFile(fileId);
	}

	@Test
	@Order(1)
	void createFineTune() {
		FineTuneRequest request = FineTuneRequest.builder()
				.trainingFile(fileId)
				.model("ada")
				.nEpochs(4)
				.build();

		FineTuneResult fineTune = service.createFineTune(request);
		fineTuneId = fineTune.getId();

		assertEquals("pending", fineTune.getStatus());
	}

	@Test
	@Order(2)
	void listFineTunes() {
		List<FineTuneResult> fineTunes = service.listFineTunes();

		assertTrue(fineTunes.stream().anyMatch(fineTune -> fineTune.getId().equals(fineTuneId)));
	}

	@Test
	@Order(3)
	void listFineTuneEvents() {
		List<FineTuneEvent> events = service.listFineTuneEvents(fineTuneId);

		assertFalse(events.isEmpty());
	}

	@Test
	@Order(3)
	void retrieveFineTune() {
		FineTuneResult fineTune = service.retrieveFineTune(fineTuneId);

		assertEquals("ada", fineTune.getModel());
	}

	@Test
	@Order(4)
	void cancelFineTune() {
		FineTuneResult fineTune = service.cancelFineTune(fineTuneId);

		assertEquals("cancelled", fineTune.getStatus());
	}
}
