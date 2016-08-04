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
		Logger.serverLog(name +" играет карту "+ Logger.cardById(card));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new PlayResponse(name,card));
	}

	public void loose(String name) {
		Logger.serverLog(name +" проигрывает");
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new LooseResponse(name));
	}

	public void protect(String name, boolean value) {
		Logger.serverLog(name + (value?" получает":" теряет") +" защиту");
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new ProtectResponse(name,value));
	}

	public void xchance(String name, String target, int card, int targetCard) {
		Logger.serverLog(name +" и "+ target+ " меняются картами:");
		for(String player:playerResponses.keySet()){
			if(player.equals(name)||player.equals(target))
				playerResponses.get(player).add(new XchanceResponse(name,target,card,targetCard));
			else 
				playerResponses.get(player).add(new XchanceResponse(name,target,-1,-1));
		}
	}

	public void drop(String name, int card) {
		Logger.serverLog(name +" сбрасывает карту "+ Logger.cardById(card));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new DropResponse(name,card));
	}

	public void draw(String name, int card) {
		Logger.serverLog(name +" берет карту");
		for(String player:playerResponses.keySet()){
			if(player.equals(name))
				playerResponses.get(player).add(new DrawResponse(name,card));
			else 
				playerResponses.get(player).add(new DrawResponse(name,-1));
		}
	}

	public void compare(String name, String target, int card, int targetCard, int code) {
		Logger.serverLog(name +" и "+ target+ "сравнивают карты:");
		if(code == 0)
			Logger.serverLog("карты равного достоинства");
		for(String player:playerResponses.keySet()){
			if(player.equals(name)||player.equals(target))
				playerResponses.get(player).add(new CompareResponse(name,target,card,targetCard,code));
			else 
				playerResponses.get(player).add(new CompareResponse(name,target,-1,-1,code));
		}
	}

	public void look(String name, String target, int card) {
		Logger.serverLog(name +" смотрит какую карту держит в руке "+ target);
		for(String player:playerResponses.keySet()){
			if(player.equals(name))
				playerResponses.get(player).add(new LookResponse(name,target,card));
			else 
				playerResponses.get(player).add(new LookResponse(name,target,-1));
		}
	}

	public void guess(String name, String target, int guessedCard, boolean guess) {
		Logger.serverLog(name +" предполагает что "+ target + "держит в руке "
				+ Logger.cardById(guessedCard)+ (guess?" и угадывает":" и ошибается"));
		for(Queue<ServerResponse> playerResponse:playerResponses.values())
			playerResponse.add(new GuessResponse(name,target,guessedCard,guess));
	}

}
