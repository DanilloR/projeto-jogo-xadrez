package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override	
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		// TODO Auto-generated method stub
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()]
				[getTabuleiro().getColunas()];
		return mat;
	}
}
