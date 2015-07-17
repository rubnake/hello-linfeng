package socket.nio;

import java.io.Serializable;
import java.util.Map;
public class SimpleHttpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String method;
	private String url;
	private String httpVersion;
	
	private String headers;

	private byte[] htmlBody;
	
	public SimpleHttpRequest(String method, String url,String httpVersion,String headers,byte[] htmlBody) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.htmlBody = htmlBody;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Request [method: " + method  + ", url: " + url + ", httpVersion: Http" +httpVersion+ "]");
		return sb.toString();
	}
}