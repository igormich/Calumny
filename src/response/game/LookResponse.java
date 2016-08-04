package response.game;

import actions.player.PlayCardAction;

public class LookResponse extends PlayResponse {

	private static final long serialVersionUID = -8392306447787264396L;

	public LookResponse(String name,String target,int card) {
		super(name, card);
	}

}
