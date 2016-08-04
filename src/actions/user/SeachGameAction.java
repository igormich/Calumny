package actions.user;

public class SeachGameAction extends CreateGameAction{

	private static final long serialVersionUID = -8273808943304615246L;
	
	private final int gameNumber;

	public SeachGameAction(String name,int gameNumber) {
		super(name);
		this.gameNumber = gameNumber;
	}

	public int getGameNumber() {
		return gameNumber;
	}

}
