package actions.user;

public class CreateGameAction implements UserAction {

	private static final long serialVersionUID = -7034975776875211299L;
	
	private final String name;

	public CreateGameAction(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
