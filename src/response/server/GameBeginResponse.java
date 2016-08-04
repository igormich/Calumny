package response.server;

public class GameBeginResponse extends ServerResponse {

	private static final long serialVersionUID = 4143990904252820891L;
	private final String firstPlayer;

	public GameBeginResponse(String firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public String getFirstPlayer() {
		return firstPlayer;
	}

	@Override
	public String toString() {
		return "GameBeginResponse [firstPlayer=" + firstPlayer + "]";
	}

}
