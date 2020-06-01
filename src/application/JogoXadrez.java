package application;

import chess.PartidaXadrez;

public class JogoXadrez {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PartidaXadrez partida = new PartidaXadrez();
		UI.printBoard(partida.getPecas());

	}

}
