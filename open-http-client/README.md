# open-http-client
A reference implementation for a HTTP client wrapper which allows decoupling the consumer from the underlying HTTP client library and uses a standard language for sending HTTP requests and receiving HTTP response

## Problem discovery:
Working interchangeably with different HTTP clients is challenging.
Ideally, any change in the HTTP client being used, should be decoupled from the client using it.
Most of the HTTP clients does not follow a standard language for their clients to interact.

In my previous employment we were responsible for building REST APIs around a solid secure vault that was to store all the PKI material.
Now from the API layer the vault was to be reached out using Jersey Client, but the rest of the third party services were to be reached out using Apache HTTP Client. So from the API layer the Jersey Client and Apache HTTP client were both to be used for reaching out to different third party services.
A separate sub-system had to built for each type of HTTP client.

In an earlier to the above employment, we faced a requirement to shift the HTTP client from HTTPUrlConnection to Jersey-Client. Team identified this change to be risky and big because both the schemes adopt a different language to set up SSL configuration and accept request and parsing response.

Both the above experience led to the problem definition - "Using various HTTP Client libraries in such a way that the client is decoupled from concrete implementations of HTTP clients and hence is not impacted with any change in the HTTP client area"

With the limited knowledge of the design patterns and industry experience, this is a small and humble effort in solving the above stated problem.

## Class Diagram
![Class Diagram](uml/high-level-class-diagram.png)

## Design Patterns Used:
<ul>
	<li>Facade</li>
	<li>Command</li>
	<li>Decorator</li>
	<li>Builder</li>
	<li>Strategy</li>
</ul>

## Long story cut short
<p>
	Any local application service would call OpenHttpClient Facade to build and send a AbstractHttpRequest Command to the IHttpRequestInvoker Command-Invoker.
	Building an AbstractHttpRequest Command also involves choosing a concrete IHttpRequestSender Command-Receiver (also known as Request-Sender given the context)
	The IHttpRequestInvoker Command-Invoker invokes the AbstractHttpRequest Command with zero or more IHttpRequestInvoker Decorators
	The AbstractHttpRequest Command when invoked, executes the IHttpRequestSender Command-Receiver methods to send http requests and receive http responses
</p>