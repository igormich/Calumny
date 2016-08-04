package logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Logger;


public class Player {

	private String name;
	private int card;
	private List<Integer> dropCards=new ArrayList<>();
	private int winCount;
	private boolean protect;
	private boolean loose;
	public Player(String name) {
		this.name = name;
	}
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	public String getName() {
		return name;
	}
	public void win(){
		winCount++;
	}
	public int getWinCount() {
		return winCount;
	}
	public boolean isProtected() {
		return protect;
	}
	public void setProtect(boolean protect) {
		if(this.protect!=protect)
		Logger.log(name+(protect?"получает":"теряет")+" защиту");
		this.protect = protect;
	}
	public boolean isLoose() {
		return loose;
	}
	@Override
	public String toString() {
		if(loose)
			return name+" loose";
		if(protect)
			return name+" P hand:"+card +" dropCards"+dropCards;
		return name+" hand:"+card +" dropCards"+dropCards;
	}
	
	public void loose() {
		Logger.log(name+" проигрывает");
		dropCard(getCard());
		this.loose = true;
	}
	
	public void dropCard(int card){
		Logger.log(name+" сбрасывает карту \""+Logger.cardById(card)+"\"");
		dropCards.add(card);
	}
	
	public void playCard(Integer card) {
		Logger.log(name+" играет карту \""+Logger.cardById(card)+"\"");
		dropCards.add(card);
	}
	public List<Integer> getDropCards() {
		return Collections.unmodifiableList(dropCards);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
