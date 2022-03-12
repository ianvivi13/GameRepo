package Models;

import java.util.*;


public class TurnOrder {
	private ArrayList<String> TurnList;
	private int pointer;
	private int adder;
	
	public TurnOrder() {
		TurnList = new ArrayList<String>();
		pointer = 0;
		adder = 1;
	}
	
	private void AdvancePointer() {
		pointer += adder;
		if (pointer < 0) {
			pointer = TurnLength() - 1;
		} else if (pointer >= TurnLength()) {
			pointer = 0;
		}
	}
	
	private int TurnLength() {
		return TurnList.size();
	}
	
	public void AddPlayer(String player) {
		TurnList.add(player);
	}
	
	public void Reverse() {
		adder *= -1;
	}

	public void NextTurn() {
		if (TurnList.indexOf(CurrentPlayer()) != TurnList.lastIndexOf(CurrentPlayer())) {
			TurnList.remove(pointer);
			if (adder < 0) {
				AdvancePointer();
			}
		} else {
			AdvancePointer();
		}
	}

	public String CurrentPlayer() {
		String i = TurnList.isEmpty() ? null : TurnList.get(pointer);
		return i;
	}
	
	public void RemovePlayer(String player) {
		int q = GetTurns(player);
		for (int i = 0 ; i < q ; i++) {
			int pos = TurnList.indexOf(player);
			TurnList.remove(player);
			if (pointer >= pos) {
				if (adder > 0) {
					Reverse();
					AdvancePointer();
					Reverse();
				} else {
					AdvancePointer();
				}
			}
		}
	}
	
	public int GetTurns(String player) {
		int q = 0;
		for (int i = 0 ; i < TurnList.size() ; i ++) {
			if (TurnList.get(i) == player) {
				q ++;
			}
		}
		return q;
	}
	
	public void AddTurn(String player, int quantity) {
		int pos = TurnList.indexOf(player);
		for (int i = 0; i < quantity ; i++) {
			TurnList.add(pos, player);
			if ((pointer >= pos) && (adder < 0)) {
				Reverse();
				AdvancePointer();
				Reverse();
			} else if ((pointer > pos) && (adder > 0)) {
				AdvancePointer();
			}
		}
	}
	
	public void SetTurn(String player) {
		pointer = adder > 0 ? TurnList.indexOf(player) : TurnList.lastIndexOf(player);
	}
	
	public void RemoveTurns(String player, int quantity) {
		for (int i = 0 ; i < quantity ; i++) {
			int pos = TurnList.indexOf(player);
			TurnList.remove(pos);
			if (pointer > pos) {
				if (adder > 0) {
					Reverse();
					AdvancePointer();
					Reverse();
				} else {
					AdvancePointer();
				}
			}
		}
	}
	
	public int RemoveAllTurns(String player) {
		int q = GetTurns(player) - 1;
		for (int i = 0 ; i < q ; i++) {
			int pos = TurnList.indexOf(player);
			TurnList.remove(player);
			if (pointer > pos) {
				if (adder > 0) {
					Reverse();
					AdvancePointer();
					Reverse();
				} else {
					AdvancePointer();
				}
			}
		}
		return q;
	}
	
	public void show() {
		System.out.println("pointer: " + pointer);
		System.out.println("adder: " + adder);
		System.out.println("turn list: " + TurnList);
		System.out.println("------------------------------");
	}
	
}