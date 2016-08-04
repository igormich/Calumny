package response.game;

public class GuessResponse extends LookResponse {

	private static final long serialVersionUID = 5839971064166264007L;
	private final boolean result;

	public GuessResponse(String name, String target, int card,boolean result) {
		super(name, target, card);
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

}
