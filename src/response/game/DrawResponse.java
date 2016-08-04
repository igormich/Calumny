package response.game;

public class DrawResponse extends PlayResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5216823861949167642L;

	public DrawResponse(String name, int card) {
		super(name, card);
	}

	@Override
	public String toString() {
		return "DrawResponse [getCard()=" + getCard() + ", getName()=" + getName() + "]";
	}

}
