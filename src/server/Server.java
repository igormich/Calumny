package server;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;

import actions.player.PlayerAction;
import actions.user.ChechAction;
import actions.user.CreateGameAction;
import actions.user.FreeGameAction;
import actions.user.GameTurnAction;
import actions.user.SeachGameAction;
import response.server.DisconnectResponse;
import response.server.GameBeginResponse;
import response.server.GameRoomResponse;
import response.server.PlayerListResponse;
import response.server.ServerResponse;



public class Server extends Thread{

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		server.setDaemon(true);//FOR DEBUG ONLY
		server.start();
		
		BotThread.freeGame("Portos").start();
		BotThread.freeGame("Atos").start();
		BotThread.freeGame("Aramis").start();
		BotThread.freeGame("D`Artanyan").start();
		while(true){
			
		}
	}
	@Override
	public void run(){
		try{
			ServerSocket serverSocket = new ServerSocket(666);
			while(true){
				try{			
					Socket socket = serverSocket.accept();
					process(socket);
				}
				catch (Exception e) {
				}
			} 
		}
		catch (Exception e) {
		}
	}

	private void process(Socket socket) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			Object action = ois.readObject();
			GameRoom gameRoom = null;
			String name = null;
			if(action instanceof ChechAction){
				name = ((ChechAction)action).getName();
				Integer gameNumber = ((ChechAction)action).getGameNumber();
				gameRoom=GameRoom.seach(gameNumber);
			} else if(action instanceof GameTurnAction){
				name = ((GameTurnAction)action).getName();
				Integer gameNumber = ((GameTurnAction)action).getGameNumber();
				PlayerAction playerAction = ((GameTurnAction)action).getPlayerAction();
				gameRoom=GameRoom.seach(gameNumber);
				if(gameRoom!=null){
					gameRoom.applyPlayerAction(playerAction);
				}
			} else if(action instanceof SeachGameAction){
				name = ((SeachGameAction)action).getName();
				Integer gameNumber = ((SeachGameAction)action).getGameNumber();
				gameRoom=GameRoom.seach(gameNumber);
				if(gameRoom!=null){
					name = gameRoom.addUser(name);
					oos.writeObject(new GameRoomResponse(name, gameRoom.getId()));
				}
			} else if(action instanceof FreeGameAction){
				name = ((FreeGameAction)action).getName();
				gameRoom=GameRoom.freeGame();
				name = gameRoom.addUser(name);
				oos.writeObject(new GameRoomResponse(name, gameRoom.getId()));
			} else if(action instanceof CreateGameAction){
				name = ((CreateGameAction)action).getName();
				gameRoom=GameRoom.create();
				name = gameRoom.addUser(name);
				oos.writeObject(new GameRoomResponse(name, gameRoom.getId()));
			}
			if(gameRoom != null){
				Queue<ServerResponse>responses=gameRoom.getResponse(name);
				if(responses!=null)
					while(!responses.isEmpty()){
						oos.writeObject(responses.poll());
				} else {
					oos.writeObject(new DisconnectResponse(403,"You have no accsess to this room"));
				}
			}else 
				oos.writeObject(new DisconnectResponse(404,"Room not found"));
			oos.writeObject(new DisconnectResponse(200,"OK"));
			oos.flush();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
