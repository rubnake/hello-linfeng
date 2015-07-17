package socket.mutlThread;

import java.io.Serializable;
import java.util.Map;
public class SimpleHttpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String method;
	private String url;
	private String httpVersion;
	
	private String headers;

	private byte[] inputStream;
	
	public SimpleHttpRequest(String method, String url,String httpVersion,String headers,byte[] inputStream) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.inputStream = inputStream;
	}
	
	
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}




	public String getHttpVersion() {
		return httpVersion;
	}




	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}




	public String getHeaders() {
		return headers;
	}




	public void setHeaders(String headers) {
		this.headers = headers;
	}




	public byte[] getInputStream() {
		return inputStream;
	}




	public void setInputStream(byte[] inputStream) {
		this.inputStream = inputStream;
	}




	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Request [method: " + method  + ", url: " + url + ", httpVersion: Http" +httpVersion+ "]");
		return sb.toString();
	}
}