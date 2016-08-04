package logic;

import utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import actions.player.GuessAction;
import actions.player.PlayCardAction;
import actions.player.PlayCardWithTargetAction;
import response.game.GameResponse;
import response.game.ResponseWriter;

public class GameModel {
	private Player currentPlayer;
	private int currentCard;
	private List<Player> players =new ArrayList<>(4);
	private List<Integer> deck;
	private List<Integer> droppedCard;
	private int reservedCard;
	private Iterator<Player> playersIterator;
	private boolean firstTurn;
	private void shuffleDeck() {
		deck=new ArrayList<>();
		deck.add(8);
		deck.add(7);
		deck.add(6);
		deck.add(5);
		deck.add(5);
		deck.add(4);
		deck.add(4);
		deck.add(3);
		deck.add(3);
		deck.add(2);
		deck.add(2);
		deck.add(1);
		deck.add(1);
		deck.add(1);
		deck.add(1);
		deck.add(1);
		Collections.shuffle(deck);
	}
	
	public boolean hasMoreCards() {
		return !deck.isEmpty();
	}
	
	public Integer nextCard() {
		return deck.remove(0);
	}
	
	public Player nextTurn() {
		if (!playersIterator.hasNext()) {
			firstTurn=false;
			playersIterator=players.iterator();
		}
		Player p=playersIterator.next();
		while(p.isLoose()) {
			if (!playersIterator.hasNext()) {
				firstTurn=false;
				playersIterator=players.iterator();
			}	
			p=playersIterator.next();
		}
		p.setProtect(false);
		return p;	
	}
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public boolean firstTurn() {
		return firstTurn;
	}
	
	public void newGame(ResponseWriter respose) {
		shuffleDeck();
		firstTurn=true;
		playersIterator=players.iterator();
		reservedCard = nextCard();
		if (players.size() == 2) {
			Integer card = nextCard();
			respose.drop("", card);
			droppedCard.add(card);
			
			card = nextCard();
			respose.drop("", card);
			droppedCard.add(card);
			
			card = nextCard();
			respose.drop("", card);
			droppedCard.add(card);
		}
		for(Player player:players) {
			Integer card = nextCard();
			respose.draw(player.getName(),card);
			player.setCard(nextCard());
		}
	}
	
	public GameModel(List<String> playerNames) {
		for(String name:playerNames) {
			players.add(new Player(name));
		}
	}
	
	private Player getPlayerByName(String name) {
		for(Player player:players) {
			if (player.getName().equals(name))
				return player;
		}
		return null;
	}
	
	public void turn(PlayCardAction action, ResponseWriter respose) {
		if (!isValid(action)) {
			throw new IllegalStateException("ѕохоже что игрок "+currentPlayer.getName()+" жульничает");
		}
		int card = action.getCard();
		currentPlayer.playCard(card);
		droppedCard.add(card);
		respose.play(currentPlayer.getName(),card);
		//всем известно что сыграл игрок		
		if (currentPlayer.getCard() == card) {// replace with new if play old card 
			currentPlayer.setCard(currentCard);
		}
		if (card == 8) {
			//надо сообщать всем
			currentPlayer.loose();
			respose.loose(currentPlayer.getName());	
		} else if (card == 7) {
			//nothing
		} else if (card == 4) {
			currentPlayer.setProtect(true);
			respose.protect(currentPlayer.getName(), true);
		} else if (action instanceof PlayCardWithTargetAction) {
			Player target = getPlayerByName(((PlayCardWithTargetAction)action).getTarget());
			int targetCard = target.getCard();
			if (card == 6) {
				int currentPlayerCard = currentPlayer.getCard();
				target.setCard(currentPlayerCard);
				currentPlayer.setCard(targetCard);
				respose.xchance(currentPlayer.getName(),target.getName(),currentPlayerCard,targetCard);
			} else if (card == 5) {
				droppedCard.add(targetCard);
				target.dropCard(targetCard);
				respose.drop(target.getName(),card);
				if (targetCard == 8) {
					target.loose();
					respose.loose(target.getName());
				}else{
					int newCard = -1;
					if (hasMoreCards())
						newCard = nextCard();
					else
						newCard = reservedCard;
					respose.draw(target.getName(),newCard);
					target.setCard(newCard);
				}
			} else if (card == 3) {
				int currentPlayerCard = currentPlayer.getCard();
				if (targetCard>currentPlayerCard) {
					respose.compare(currentPlayer.getName(),target.getName(),currentPlayerCard,targetCard,1);
					droppedCard.add(currentPlayerCard);
					respose.drop(currentPlayer.getName(),currentPlayerCard);
					currentPlayer.loose();
					respose.loose(currentPlayer.getName());
				}else if (currentPlayerCard>targetCard){
					respose.compare(currentPlayer.getName(),target.getName(),currentPlayerCard,targetCard,-1);
					droppedCard.add(targetCard);
					respose.drop(target.getName(),targetCard);
					target.loose();
					respose.loose(target.getName());
				}
				else{
					respose.compare(currentPlayer.getName(),target.getName(),currentPlayerCard,targetCard,0);
				}
			} else if (card == 2) {
				respose.look(currentPlayer.getName(),target.getName(),target.getCard());
			} else if ((card == 1) && (action instanceof GuessAction)) {
				int guessedCard = ((GuessAction)action).getGuessedCard();
				if(targetCard == guessedCard) {
					respose.guess(currentPlayer.getName(),target.getName(),guessedCard,true);
					droppedCard.add(targetCard);
					respose.drop(target.getName(),targetCard);
					target.loose();	
					respose.loose(target.getName());
				} else {
					respose.guess(currentPlayer.getName(),target.getName(),guessedCard,false);
				}
			}
		}
	}

	private boolean isValid(PlayCardAction action) {
		if ((currentPlayer.getCard() != action.getCard())
				&& (currentCard != action.getCard()))
			return false;
		return true;
	}
}
