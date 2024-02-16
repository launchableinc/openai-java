package com.launchableinc.openai.client;

import com.launchableinc.openai.DeleteResult;
import com.launchableinc.openai.OpenAiResponse;
import com.launchableinc.openai.assistants.*;
import com.launchableinc.openai.audio.CreateSpeechRequest;
import com.launchableinc.openai.audio.TranscriptionResult;
import com.launchableinc.openai.audio.TranslationResult;
import com.launchableinc.openai.billing.BillingUsage;
import com.launchableinc.openai.billing.Subscription;
import com.launchableinc.openai.completion.CompletionRequest;
import com.launchableinc.openai.completion.CompletionResult;
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
import com.launchableinc.openai.finetune.FineTuneRequest;
import com.launchableinc.openai.finetune.FineTuneResult;
import com.launchableinc.openai.image.CreateImageRequest;
import com.launchableinc.openai.image.ImageResult;
import com.launchableinc.openai.messages.Message;
import com.launchableinc.openai.messages.MessageFile;
import com.launchableinc.openai.messages.MessageRequest;
import com.launchableinc.openai.messages.ModifyMessageRequest;
import com.launchableinc.openai.model.Model;
import com.launchableinc.openai.moderation.ModerationRequest;
import com.launchableinc.openai.moderation.ModerationResult;
import com.launchableinc.openai.runs.CreateThreadAndRunRequest;
import com.launchableinc.openai.runs.Run;
import com.launchableinc.openai.runs.RunCreateRequest;
import com.launchableinc.openai.runs.RunStep;
import com.launchableinc.openai.runs.SubmitToolOutputsRequest;
import com.launchableinc.openai.threads.Thread;
import com.launchableinc.openai.threads.ThreadRequest;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDate;
import java.util.Map;

public interface OpenAiApi {

	@GET("v1/models")
	Single<OpenAiResponse<Model>> listModels();

	@GET("/v1/models/{model_id}")
	Single<Model> getModel(@Path("model_id") String modelId);

	@POST("/v1/completions")
	Single<CompletionResult> createCompletion(@Body CompletionRequest request);

	@Streaming
	@POST("/v1/completions")
	Call<ResponseBody> createCompletionStream(@Body CompletionRequest request);

	@POST("/v1/chat/completions")
	Single<ChatCompletionResult> createChatCompletion(@Body ChatCompletionRequest request);

	@Streaming
	@POST("/v1/chat/completions")
	Call<ResponseBody> createChatCompletionStream(@Body ChatCompletionRequest request);

	@Deprecated
	@POST("/v1/engines/{engine_id}/completions")
	Single<CompletionResult> createCompletion(@Path("engine_id") String engineId,
			@Body CompletionRequest request);

	@POST("/v1/edits")
	Single<EditResult> createEdit(@Body EditRequest request);

	@Deprecated
	@POST("/v1/engines/{engine_id}/edits")
	Single<EditResult> createEdit(@Path("engine_id") String engineId, @Body EditRequest request);

	@POST("/v1/embeddings")
	Single<EmbeddingResult> createEmbeddings(@Body EmbeddingRequest request);

	@Deprecated
	@POST("/v1/engines/{engine_id}/embeddings")
	Single<EmbeddingResult> createEmbeddings(@Path("engine_id") String engineId,
			@Body EmbeddingRequest request);

	@GET("/v1/files")
	Single<OpenAiResponse<File>> listFiles();

	@Multipart
	@POST("/v1/files")
	Single<File> uploadFile(@Part("purpose") RequestBody purpose, @Part MultipartBody.Part file);

	@DELETE("/v1/files/{file_id}")
	Single<DeleteResult> deleteFile(@Path("file_id") String fileId);

	@GET("/v1/files/{file_id}")
	Single<File> retrieveFile(@Path("file_id") String fileId);

	@Streaming
	@GET("/v1/files/{file_id}/content")
	Single<ResponseBody> retrieveFileContent(@Path("file_id") String fileId);

	@POST("/v1/fine_tuning/jobs")
	Single<FineTuningJob> createFineTuningJob(@Body FineTuningJobRequest request);

	@GET("/v1/fine_tuning/jobs")
	Single<OpenAiResponse<FineTuningJob>> listFineTuningJobs();

	@GET("/v1/fine_tuning/jobs/{fine_tuning_job_id}")
	Single<FineTuningJob> retrieveFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);

	@POST("/v1/fine_tuning/jobs/{fine_tuning_job_id}/cancel")
	Single<FineTuningJob> cancelFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);

	@GET("/v1/fine_tuning/jobs/{fine_tuning_job_id}/events")
	Single<OpenAiResponse<FineTuningEvent>> listFineTuningJobEvents(
			@Path("fine_tuning_job_id") String fineTuningJobId);

	@Deprecated
	@POST("/v1/fine-tunes")
	Single<FineTuneResult> createFineTune(@Body FineTuneRequest request);

	@POST("/v1/completions")
	Single<CompletionResult> createFineTuneCompletion(@Body CompletionRequest request);

	@Deprecated
	@GET("/v1/fine-tunes")
	Single<OpenAiResponse<FineTuneResult>> listFineTunes();

	@Deprecated
	@GET("/v1/fine-tunes/{fine_tune_id}")
	Single<FineTuneResult> retrieveFineTune(@Path("fine_tune_id") String fineTuneId);

	@Deprecated
	@POST("/v1/fine-tunes/{fine_tune_id}/cancel")
	Single<FineTuneResult> cancelFineTune(@Path("fine_tune_id") String fineTuneId);

	@Deprecated
	@GET("/v1/fine-tunes/{fine_tune_id}/events")
	Single<OpenAiResponse<FineTuneEvent>> listFineTuneEvents(@Path("fine_tune_id") String fineTuneId);

	@DELETE("/v1/models/{fine_tune_id}")
	Single<DeleteResult> deleteFineTune(@Path("fine_tune_id") String fineTuneId);

	@POST("/v1/images/generations")
	Single<ImageResult> createImage(@Body CreateImageRequest request);

	@POST("/v1/images/edits")
	Single<ImageResult> createImageEdit(@Body RequestBody requestBody);

	@POST("/v1/images/variations")
	Single<ImageResult> createImageVariation(@Body RequestBody requestBody);

	@POST("/v1/audio/transcriptions")
	Single<TranscriptionResult> createTranscription(@Body RequestBody requestBody);

	@POST("/v1/audio/translations")
	Single<TranslationResult> createTranslation(@Body RequestBody requestBody);

	@POST("/v1/audio/speech")
	Single<ResponseBody> createSpeech(@Body CreateSpeechRequest requestBody);

	@POST("/v1/moderations")
	Single<ModerationResult> createModeration(@Body ModerationRequest request);

	@Deprecated
	@GET("v1/engines")
	Single<OpenAiResponse<Engine>> getEngines();

	@Deprecated
	@GET("/v1/engines/{engine_id}")
	Single<Engine> getEngine(@Path("engine_id") String engineId);

	/**
	 * Account information inquiry: It contains total amount (in US dollars) and other information.
	 *
	 * @return
	 */
	@Deprecated
	@GET("v1/dashboard/billing/subscription")
	Single<Subscription> subscription();

	/**
	 * Account call interface consumption amount inquiry. totalUsage = Total amount used by the
	 * account (in US cents).
	 *
	 * @param starDate
	 * @param endDate
	 * @return Consumption amount information.
	 */
	@Deprecated
	@GET("v1/dashboard/billing/usage")
	Single<BillingUsage> billingUsage(@Query("start_date") LocalDate starDate,
			@Query("end_date") LocalDate endDate);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/assistants")
	Single<Assistant> createAssistant(@Body AssistantRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/assistants/{assistant_id}")
	Single<Assistant> retrieveAssistant(@Path("assistant_id") String assistantId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/assistants/{assistant_id}")
	Single<Assistant> modifyAssistant(@Path("assistant_id") String assistantId,
			@Body ModifyAssistantRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@DELETE("/v1/assistants/{assistant_id}")
	Single<DeleteResult> deleteAssistant(@Path("assistant_id") String assistantId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/assistants")
	Single<OpenAiResponse<Assistant>> listAssistants(@QueryMap Map<String, Object> filterRequest);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/assistants/{assistant_id}/files")
	Single<AssistantFile> createAssistantFile(@Path("assistant_id") String assistantId,
			@Body AssistantFileRequest fileRequest);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/assistants/{assistant_id}/files/{file_id}")
	Single<AssistantFile> retrieveAssistantFile(@Path("assistant_id") String assistantId,
			@Path("file_id") String fileId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@DELETE("/v1/assistants/{assistant_id}/files/{file_id}")
	Single<DeleteResult> deleteAssistantFile(@Path("assistant_id") String assistantId,
			@Path("file_id") String fileId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/assistants/{assistant_id}/files")
	Single<OpenAiResponse<AssistantFile>> listAssistantFiles(@Path("assistant_id") String assistantId,
			@QueryMap Map<String, Object> filterRequest);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/threads")
	Single<Thread> createThread(@Body ThreadRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}")
	Single<Thread> retrieveThread(@Path("thread_id") String threadId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/threads/{thread_id}")
	Single<Thread> modifyThread(@Path("thread_id") String threadId, @Body ThreadRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@DELETE("/v1/threads/{thread_id}")
	Single<DeleteResult> deleteThread(@Path("thread_id") String threadId);


	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/threads/{thread_id}/messages")
	Single<Message> createMessage(@Path("thread_id") String threadId, @Body MessageRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages/{message_id}")
	Single<Message> retrieveMessage(@Path("thread_id") String threadId,
			@Path("message_id") String messageId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@POST("/v1/threads/{thread_id}/messages/{message_id}")
	Single<Message> modifyMessage(@Path("thread_id") String threadId,
			@Path("message_id") String messageId, @Body ModifyMessageRequest request);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages")
	Single<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages")
	Single<OpenAiResponse<Message>> listMessages(@Path("thread_id") String threadId,
			@QueryMap Map<String, Object> filterRequest);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages/{message_id}/files/{file_id}")
	Single<MessageFile> retrieveMessageFile(@Path("thread_id") String threadId,
			@Path("message_id") String messageId, @Path("file_id") String fileId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages/{message_id}/files")
	Single<OpenAiResponse<MessageFile>> listMessageFiles(@Path("thread_id") String threadId,
			@Path("message_id") String messageId);

	@Headers({"OpenAI-Beta: assistants=v1"})
	@GET("/v1/threads/{thread_id}/messages/{message_id}/files")
	Single<OpenAiResponse<MessageFile>> listMessageFiles(@Path("thread_id") String threadId,
			@Path("message_id") String messageId, @QueryMap Map<String, Object> filterRequest);

	@Headers("OpenAI-Beta: assistants=v1")
	@POST("/v1/threads/{thread_id}/runs")
	Single<Run> createRun(@Path("thread_id") String threadId,
			@Body RunCreateRequest runCreateRequest);

	@Headers("OpenAI-Beta: assistants=v1")
	@GET("/v1/threads/{thread_id}/runs/{run_id}")
	Single<Run> retrieveRun(@Path("thread_id") String threadId, @Path("run_id") String runId);

	@Headers("OpenAI-Beta: assistants=v1")
	@POST("/v1/threads/{thread_id}/runs/{run_id}")
	Single<Run> modifyRun(@Path("thread_id") String threadId, @Path("run_id") String runId,
			@Body Map<String, String> metadata);

	@Headers("OpenAI-Beta: assistants=v1")
	@GET("/v1/threads/{thread_id}/runs")
	Single<OpenAiResponse<Run>> listRuns(@Path("thread_id") String threadId,
			@QueryMap Map<String, String> listSearchParameters);


	@Headers("OpenAI-Beta: assistants=v1")
	@POST("/v1/threads/{thread_id}/runs/{run_id}/submit_tool_outputs")
	Single<Run> submitToolOutputs(@Path("thread_id") String threadId, @Path("run_id") String runId,
			@Body SubmitToolOutputsRequest submitToolOutputsRequest);


	@Headers("OpenAI-Beta: assistants=v1")
	@POST("/v1/threads/{thread_id}/runs/{run_id}/cancel")
	Single<Run> cancelRun(@Path("thread_id") String threadId, @Path("run_id") String runId);

	@Headers("OpenAI-Beta: assistants=v1")
	@POST("/v1/threads/runs")
	Single<Run> createThreadAndRun(@Body CreateThreadAndRunRequest createThreadAndRunRequest);

	@Headers("OpenAI-Beta: assistants=v1")
	@GET("/v1/threads/{thread_id}/runs/{run_id}/steps/{step_id}")
	Single<RunStep> retrieveRunStep(@Path("thread_id") String threadId, @Path("run_id") String runId,
			@Path("step_id") String stepId);

	@Headers("OpenAI-Beta: assistants=v1")
	@GET("/v1/threads/{thread_id}/runs/{run_id}/steps")
	Single<OpenAiResponse<RunStep>> listRunSteps(@Path("thread_id") String threadId,
			@Path("run_id") String runId, @QueryMap Map<String, String> listSearchParameters);
}
