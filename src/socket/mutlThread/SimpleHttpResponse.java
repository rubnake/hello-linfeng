package socket.mutlThread;


import java.io.Serializable;

public class SimpleHttpResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String httpVersion;
	
	private String statusCode;
	
	private String message;

	private byte[] outputStream;
	
	public SimpleHttpResponse(String httpVersion, String statusCode,String message,byte[] outputStream) {
		this.httpVersion = httpVersion;
		this.statusCode = statusCode;
		this.message = message;
		this.outputStream = outputStream;
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



	public byte[] getOutputStream() {
		return outputStream;
	}



	public void setOutputStream(byte[] outputStream) {
		this.outputStream = outputStream;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Response [statusCode: " + statusCode  + ", message: " + message + "]");
		return sb.toString();
	}
}