package com.ma.open.http.client.request;

import com.ma.open.http.client.request.sender.IHttpRequestSender;

class PostRequest extends AbstractHttpRequest {

	private Object requestBody;

	private PostRequest(Builder builder) {
		super(builder);
		this.requestBody = builder.requestBody;
	}

	@Override
	public HttpResponse send() {
		System.out.println("PostRequest.send sending " + requestBody.toString() + "to "
				+ requestSender.getClass().getSimpleName());
		return requestSender.post(this);
	}

	static class Builder extends AbstractHttpRequestBuilder {

		public Builder(String url, IHttpRequestSender requestSender) {
			this.url = url;
			this.requestSender = requestSender;
		}

		@Override
		public AbstractHttpRequest build() {
			return new PostRequest(this);
		}

	}

}
