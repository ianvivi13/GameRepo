package Models;

import java.util.*;


public class TurnOrder {
	private ArrayList<Integer> TurnList;
	private int pointer;
	private int adder;
	
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	
	public void setAdder(int adder) {
		this.adder = adder;
	}
	
	public void setTurnList(ArrayList<Integer> TurnList) {
		this.TurnList = TurnList;
	}
	
	public int getPointer() {
		return pointer;
	}
	
	public int getAdder() {
		return adder;
	}
	
	public ArrayList<Integer> getTurnList() {
		return TurnList;
	}
	
	public TurnOrder() {
		TurnList = new ArrayList<Integer>();
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
	
	public void AddPlayer(Integer player) {
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

	public int CurrentPlayer() {
			int i = TurnList.isEmpty() ? null : TurnList.get(pointer);
			return i;
	}
	
	public void RemovePlayer(Integer player) {
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
	
	public int GetTurns(Integer player) {
		int q = 0;
		for (int i = 0 ; i < TurnList.size() ; i ++) {
			if (TurnList.get(i) == player) {
				q ++;
			}
		}
		return q;
	}
	
	public void AddTurn(Integer player, int quantity) {
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
	
	public void SetTurn(Integer player) {
		pointer = adder > 0 ? TurnList.indexOf(player) : TurnList.lastIndexOf(player);
	}
	
	public void RemoveTurns(Integer player, int quantity) {
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
	
	public int RemoveAllTurns(Integer player) {
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
	
	public boolean equals(TurnOrder turn) {

		if (this.pointer != turn.pointer) {
			return false;
		}
		
		if (this.adder != turn.adder) {
			System.out.println("same");
			return false;
		}
		
		if (!this.TurnList.equals(turn.TurnList)) {
			return false;
		}
		
		return true;
	}
}