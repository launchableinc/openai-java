package com.launchableinc.openai.completion.chat;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

/*
 * OpenAI API Document:https://platform.openai.com/docs/api-reference/chat/create#chat-create-response_format
 */
@Data
@Builder
public class ChatResponseFormat {

	private ResponseFormat type;

	public enum ResponseFormat {
		TEXT("text"), JSON("json_object");

		private final String value;

		ResponseFormat(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}
	}

}
