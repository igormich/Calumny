package response.server;

public class DisconnectResponse extends ServerResponse {

	private static final long serialVersionUID = 1164888380301481057L;
	private final int code;
	private final String message;

	public DisconnectResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "DisconnectResponse [code=" + code + ", message=" + message
				+ "]";
	}

}
