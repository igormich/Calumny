package actions.player;

public class PlayCardAction implements PlayerAction {

	private static final long serialVersionUID = 4876557189408290121L;
	
	private final int card;
	
	public PlayCardAction(int card) {
		this.card = card;
	}
	
	public int getCard() {
		return card;
	}
}
