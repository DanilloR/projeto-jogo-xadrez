package application;

import chess.PartidaXadrez;

public class JogoXadrez {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PartidaXadrez partida = new PartidaXadrez();
		//instancia uma nova partida
		
		UI.printBoard(partida.getPecas());
		//imprime as peças da partida

	}

}
