package response.game;

import response.server.ServerResponse;

public class GameResponse extends ServerResponse{

	private static final long serialVersionUID = 893100219366559649L;

	private final String name;

	public GameResponse(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
