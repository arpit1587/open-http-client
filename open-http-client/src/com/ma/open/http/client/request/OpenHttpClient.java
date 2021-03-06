package com.ma.open.http.client.request;

import static com.ma.open.http.client.request.invoker.IHttpRequestInvoker.requestInvoker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.ma.open.http.client.request.invoker.IHttpRequestInvoker;
import com.ma.open.http.client.request.invoker.IRetryPolicy;
import com.ma.open.http.client.request.sender.IHttpRequestSender;

public final class OpenHttpClient {

	public static InvocationBuilder newGetRequest(String url, IHttpRequestSender requestSender) {
		return new InvocationBuilder(AbstractHttpRequestBuilder.aGetRequest(url, requestSender));
	}

	public static InvocationBuilder newPostRequest(String url, IHttpRequestSender requestSender, Object requestBody) {
		return new InvocationBuilder(AbstractHttpRequestBuilder.aPostRequest(url, requestSender, requestBody));
	}

	public final static class InvocationBuilder {
		private AbstractHttpRequestBuilder wrappedBuilder;
		private IHttpRequestInvoker invoker = requestInvoker;

		public InvocationBuilder(AbstractHttpRequestBuilder builder) {
			this.wrappedBuilder = builder;
		}

		public HttpResponse send() {
			try {
				return invoker.invoke(wrappedBuilder.build());
			} finally {
				wrappedBuilder = null;
			}
		}

		public Future<HttpResponse> sendAsync() {
			try {
				return invoker.invokeAsync(wrappedBuilder.build());
			} finally {
				wrappedBuilder = null;
			}
		}

		public InvocationBuilder retry(IRetryPolicy retryPolicy) {
			invoker = IHttpRequestInvoker.retriableInvoker(retryPolicy);
			return this;
		}

		public InvocationBuilder header(String name, String... values) {
			wrappedBuilder = wrappedBuilder.header(name, values);
			return this;
		}

		public InvocationBuilder headers(Map<String, List<Object>> headers) {
			wrappedBuilder = wrappedBuilder.headers(headers);
			return this;
		}

		public InvocationBuilder finalHeaders(Map<String, List<Object>> finalHeaders) {
			wrappedBuilder = wrappedBuilder.finalHeaders(finalHeaders);
			return this;
		}

		public InvocationBuilder param(String name, String... values) {
			wrappedBuilder = wrappedBuilder.param(name, values);
			return this;
		}

		public InvocationBuilder contentType(String contentType) {
			wrappedBuilder = wrappedBuilder.contentType(contentType);
			return this;
		}

		public InvocationBuilder accept(String... acceptTypes) {
			wrappedBuilder = wrappedBuilder.accept(acceptTypes);
			return this;
		}

		public InvocationBuilder requestConfig(String key, String value) {
			wrappedBuilder = wrappedBuilder.requestConfig(key, value);
			return this;
		}

		public InvocationBuilder requestConfigs(Map<String, Object> requestConfigs) {
			wrappedBuilder = wrappedBuilder.requestConfigs(requestConfigs);
			return this;
		}

		public InvocationBuilder content(Object requestBody) {
			wrappedBuilder = wrappedBuilder.content(requestBody);
			return this;
		}

		public InvocationBuilder secure(SSLConfig sslConfig) {
			wrappedBuilder = wrappedBuilder.secure(sslConfig);
			return this;
		}

	}

}
