package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	// possíveis movimentos da torre
	public boolean[][] movimentosPossiveis() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// diagonal superior esquerda (noroeste)
		p.setValues(posicao.getLinhas() - 1, posicao.getColunas()-1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValues(p.getLinhas()-1, p.getColunas()-1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// diagonal superior direita (nordeste)
		p.setValues(posicao.getLinhas()-1, posicao.getColunas() +1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValues(p.getLinhas()-1, p.getColunas()+1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// diagonal inferior direita (sudeste)
		p.setValues(posicao.getLinhas()+1, posicao.getColunas() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValues(p.getLinhas()+1,p.getColunas() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// diagonal inferior esquerda
		p.setValues(posicao.getLinhas() + 1, posicao.getColunas()-1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setValues(p.getLinhas() + 1, p.getColunas()-1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		return mat;
	}
}