package com.launchableinc.openai.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.launchableinc.openai.completion.chat.ChatCompletionRequest;

public abstract class ChatCompletionRequestMixIn {

	@JsonSerialize(using = ChatCompletionRequestSerializerAndDeserializer.Serializer.class)
	@JsonDeserialize(using = ChatCompletionRequestSerializerAndDeserializer.Deserializer.class)
	abstract ChatCompletionRequest.ChatCompletionRequestFunctionCall getFunctionCall();

}
