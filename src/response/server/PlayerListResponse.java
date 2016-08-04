package response.server;

import java.util.List;

public class PlayerListResponse extends ServerResponse {

	private static final long serialVersionUID = 2710223289964556585L;
	private final List<String> players;
	public PlayerListResponse(List<String> players) {
		this.players = players;
	}
	@Override
	public String toString() {
		return "PlayerListResponse [players=" + players + "]";
	}
	public List<String> getPlayers() {
		return players;
	}
	

}
