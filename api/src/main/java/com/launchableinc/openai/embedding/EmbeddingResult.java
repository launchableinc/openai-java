package com.launchableinc.openai.embedding;

import com.launchableinc.openai.Usage;
import lombok.Data;

import java.util.List;

/**
 * An object containing a response from the answer api
 * <p>
 * https://beta.openai.com/docs/api-reference/embeddings/create
 */
@Data
public class EmbeddingResult {

	/**
	 * The GPTmodel used for generating embeddings
	 */
	String model;

	/**
	 * The type of object returned, should be "list"
	 */
	String object;

	/**
	 * A list of the calculated embeddings
	 */
	List<Embedding> data;

	/**
	 * The API usage for this request
	 */
	Usage usage;
}
