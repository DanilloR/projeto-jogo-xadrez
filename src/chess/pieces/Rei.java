package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override	
	public String toString() {
		return "K";
	}

}
