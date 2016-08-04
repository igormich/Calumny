package response.game;

public class CompareResponse extends XchanceResponse {

	private static final long serialVersionUID = -7370054946526936804L;
	private int code;//1 win, -1 lose, 0 - no result
	public CompareResponse(String name, String target, int card, int targetCard,int code) {
		super(name, target, card, targetCard);
		this.code = code;
	}

}
