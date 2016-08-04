package response.game;

public class ProtectResponse extends GameResponse {

	private static final long serialVersionUID = 4885540294416565977L;
	
	private final boolean value;

	public ProtectResponse(String name, boolean value) {
		super(name);
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

}
