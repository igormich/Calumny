package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import actions.player.PlayerAction;
import logic.GameModel;
import response.game.GameResponse;
import response.game.ResponseWriter;
import response.server.GameBeginResponse;
import response.server.PlayerListResponse;
import response.server.ServerResponse;
import utils.Logger;

public class GameRoom {

	private static final int MAX_QUEUE_SIZE = 128;
	private static Random random = new Random();
	private static Map<Integer,GameRoom> gameRooms=new HashMap<Integer,GameRoom>();
	private static GameRoom freeGameRoom;
	
	private List<String> users=new ArrayList<>(4);
	private HashMap<String, Queue<ServerResponse>> userResponses = new HashMap<>();
	private ResponseWriter respose;
	private GameModel gameModel;
	private int id;
	
	
	public GameRoom() {
		synchronized(gameRooms){
			do{
				id=Math.abs(random.nextInt());
			}while(gameRooms.containsKey(id));
			gameRooms.put(getId(), this);
		}
		Logger.serverLog("Created room " + id);
	}

	public String addUser(String name) {
		name=name+"#"+Integer.toString(random.nextInt());
		Logger.serverLog("User " + name + " enter to room "+id);
		users.add(name);
		userResponses.put(name, new ArrayBlockingQueue<ServerResponse>(MAX_QUEUE_SIZE));
		for(Queue<ServerResponse> queue:userResponses.values()){
			queue.add(new PlayerListResponse(users));
		}
		if(users.size()==4)
			startGame();
		return name;
	}

	private void startGame() {
		Logger.serverLog("Game begin");
		Collections.shuffle(users);
		for(Queue<ServerResponse> queue:userResponses.values()){
			queue.add(new GameBeginResponse(users.get(0)));
		}
		respose = new ResponseWriter(userResponses);
		gameModel = new GameModel(users);
		gameModel.newGame(respose);
	}

	public static GameRoom seach(Integer id) {
		return gameRooms.get(id);
	}

	public static GameRoom create() {
		GameRoom gameRoom = new GameRoom();
		return gameRoom;
	}

	public int getId() {
		return id;
	}

	public static GameRoom freeGame() {
		if((freeGameRoom == null)||(freeGameRoom.users.size() == 4))
			freeGameRoom = new GameRoom();
		return freeGameRoom;
	}

	public void applyPlayerAction(PlayerAction playerAction) {
		
		
	}

	public Queue<ServerResponse> getResponse(String name) {
		return userResponses.get(name);
	}

}
