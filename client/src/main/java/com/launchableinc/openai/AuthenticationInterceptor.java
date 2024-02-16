package com.launchableinc.openai;

/**
 * OkHttp Interceptor that adds an authorization token header
 *
 * @deprecated Use {@link com.launchableinc.openai.client.AuthenticationInterceptor}
 */
@Deprecated
public class AuthenticationInterceptor extends
		com.launchableinc.openai.client.AuthenticationInterceptor {

	AuthenticationInterceptor(String token) {
		super(token);
	}

}
