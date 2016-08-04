package actions.player;

public class GuessAction extends PlayCardWithTargetAction {

	private static final long serialVersionUID = 1003969053981785819L;
	
	private final int guessedCard;

	public GuessAction(int card, String target,int guessedCard) {
		super(card, target);
		this.guessedCard = guessedCard;
	}

	public int getGuessedCard() {
		return guessedCard;
	}

}
