package response.server;

public class GameRoomResponse extends ServerResponse{

	private static final long serialVersionUID = 7145819870031440870L;
	
	private final String name;
	private final int roomID;
	
	public GameRoomResponse(String name, int roomID) {
		this.name = name;
		this.roomID = roomID;
	}
	
	public String getName() {
		return name;
	}

	public int getRoomID() {
		return roomID;
	}
	
	@Override
	public String toString() {
		return "GameRoomResponse [name=" + name + ", roomID=" + roomID + "]";
	}
}
