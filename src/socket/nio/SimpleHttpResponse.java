package socket.nio;


import java.io.Serializable;

public class SimpleHttpResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String httpVersion;
	
	private String statusCode;
	
	private String message;

	private byte[] body;
	
	public SimpleHttpResponse(String httpVersion, String statusCode,String message,byte[] body) {
		this.httpVersion = httpVersion;
		this.statusCode = statusCode;
		this.message = message;
		this.body = body;
	}
	
	
	
	public String getHttpVersion() {
		return httpVersion;
	}



	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}



	public String getStatusCode() {
		return statusCode;
	}



	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public byte[] getBody() {
		return body;
	}



	public void setBody(byte[] body) {
		this.body = body;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Response [statusCode: " + statusCode  + ", message: " + message + "]");
		return sb.toString();
	}
}