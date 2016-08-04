package response.game;

public class XchanceResponse extends PlayResponse {

	private static final long serialVersionUID = -2296218206181845791L;
	private String target;
	private int targetCard;

	public XchanceResponse(String name, String target, int card, int targetCard) {
		super(name,card);
		this.target = target;
		this.targetCard = targetCard;
	}

}
