package com.launchableinc.openai.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;

import java.io.IOException;

public class ChatFunctionParametersSerializer extends JsonSerializer<Class<?>> {

	private final SchemaGenerator schemaGenerator = new SchemaGenerator(
			new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_7)
					.with(new JacksonModule(JacksonOption.RESPECT_JSONPROPERTY_REQUIRED))
					.build()
	);

	@Override
	public void serialize(Class<?> value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		if (value == null) {
			gen.writeNull();
		} else {
			try {
				JsonNode schema = schemaGenerator.generateSchema(value);
				gen.writeObject(schema);
			} catch (Exception e) {
				throw new RuntimeException("Failed to generate JSON Schema", e);
			}
		}
	}
}





