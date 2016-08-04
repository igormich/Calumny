package actions.player;

public class PlayCardWithTargetAction extends PlayCardAction {

	private static final long serialVersionUID = -2848677383710738181L;
	
	private final String target;

	public PlayCardWithTargetAction(int card,String target) {
		super(card);
		this.target = target;	
	}

	public String getTarget() {
		return target;
	}

}
