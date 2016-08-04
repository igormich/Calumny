package response.game;

import java.util.HashMap;
import java.util.Queue;

import response.server.ServerResponse;
import utils.Logger;

public class ResponseWriter {

	HashMap<String, Queue<ServerResponse>> playerResponses;
	
	public ResponseWriter(HashMap<String, Queue<ServerResponse>> playerResponses){
		this.playerResponses = playerResponses;
	}
	
	public void play(String name, int card) {
		Logger.serverLog(name +" ������ ����� "+ Logger.cardById(card));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new PlayResponse(name,card));
	}

	public void loose(String name) {
		Logger.serverLog(name +" �����������");
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new LooseResponse(name));
	}

	public void protect(String name, boolean value) {
		Logger.serverLog(name + (value?" ��������":" ������") +" ������");
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new ProtectResponse(name,value));
	}

	public void xchance(String name, String target, int card, int targetCard) {
		Logger.serverLog(name +" � "+ target+ " �������� �������:");
		for(String player:playerResponses.keySet()){
			if(player.equals(name)||player.equals(target))
				playerResponses.get(player).add(new XchanceResponse(name,target,card,targetCard));
			else 
				playerResponses.get(player).add(new XchanceResponse(name,target,-1,-1));
		}
	}

	public void drop(String name, int card) {
		Logger.serverLog(name +" ���������� ����� "+ Logger.cardById(card));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new DropResponse(name,card));
	}

	public void draw(String name, int card) {
		Logger.serverLog(name +" ����� �����");
		for(String player:playerResponses.keySet()){
			if(player.equals(name))
				playerResponses.get(player).add(new DrawResponse(name,card));
			else 
				playerResponses.get(player).add(new DrawResponse(name,-1));
		}
	}

	public void compare(String name, String target, int card, int targetCard, int code) {
		Logger.serverLog(name +" � "+ target+ "���������� �����:");
		if(code == 0)
			Logger.serverLog("����� ������� �����������");
		for(String player:playerResponses.keySet()){
			if(player.equals(name)||player.equals(target))
				playerResponses.get(player).add(new CompareResponse(name,target,card,targetCard,code));
			else 
				playerResponses.get(player).add(new CompareResponse(name,target,-1,-1,code));
		}
	}

	public void look(String name, String target, int card) {
		Logger.serverLog(name +" ������� ����� ����� ������ � ���� "+ target);
		for(String player:playerResponses.keySet()){
			if(player.equals(name))
				playerResponses.get(player).add(new LookResponse(name,target,card));
			else 
				playerResponses.get(player).add(new LookResponse(name,target,-1));
		}
	}

	public void guess(String name, String target, int guessedCard, boolean guess) {
		Logger.serverLog(name +" ������������ ��� "+ target + "������ � ���� "
				+ Logger.cardById(guessedCard)+ (guess?" � ���������":" � ���������"));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new GuessResponse(name,target,guessedCard,guess));
	}

}
