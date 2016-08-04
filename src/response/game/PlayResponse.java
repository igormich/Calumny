package response.game;



public class PlayResponse extends GameResponse{

	private static final long serialVersionUID = 6828040374748738565L;
	
	private final int card;

	public PlayResponse(String name, int card) {
		super(name);
		this.card = card;
	}

	public int getCard() {
		return card;
	}


}
