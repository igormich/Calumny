package actions.user;

import actions.player.PlayerAction;

public class GameTurnAction extends SeachGameAction {
	
	private static final long serialVersionUID = 4329072968898170655L;
	
	private final PlayerAction playerAction;

	public GameTurnAction(String name, int gameNumber,PlayerAction playerAction) {
		super(name, gameNumber);
		this.playerAction = playerAction;
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}
	
}
