package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import response.server.DisconnectResponse;
import response.server.GameRoomResponse;
import response.server.PlayerListResponse;
import response.server.ServerResponse;
import utils.Logger;
import actions.user.ChechAction;
import actions.user.FreeGameAction;

public class BotThread extends Thread {

	
	private final int roomID;
	private final String name;
	private final List<String> players = new ArrayList<>();
	
	public BotThread(int roomID, String name) {
		setDaemon(true);
		this.roomID = roomID;
		this.name = name;
	}

	@Override
	public void run(){
		while(true){
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try(Socket socket = new Socket("localhost", 666)) {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oos.flush();
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				oos.writeObject(new ChechAction(name, roomID));
				oos.flush();
				ServerResponse serverResponse=((ServerResponse) ois.readObject());
				while(! (serverResponse instanceof DisconnectResponse)){
					Logger.log(name.substring(0, name.lastIndexOf('#')) + ":" + serverResponse);
					serverResponse=((ServerResponse) ois.readObject());
					if(serverResponse instanceof PlayerListResponse)
						players.addAll(((PlayerListResponse)serverResponse).getPlayers());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static BotThread freeGame(String name) {
		Socket socket;
		try {
			socket = new Socket("localhost", 666);
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			oos.writeObject(new FreeGameAction(name));
			oos.flush();
			GameRoomResponse gameRoomResponse=((GameRoomResponse) ois.readObject());
			socket.close();
			return new BotThread(gameRoomResponse.getRoomID(), gameRoomResponse.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public int getRoomID() {
		return roomID;
	}
}
