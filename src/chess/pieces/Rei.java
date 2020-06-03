package chess.pieces;

import boardgame.Posicao;
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

	// saber se o rei pode mover para aquela posicao
	private boolean podeMover(Posicao posicao) {
		// pega a peça na posicao informada
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		// testa se a peça não é nula e se é diferente da cor desejada
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);
		// acima
		p.setValues(posicao.getLinhas() - 1, posicao.getColunas());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// abaixo
		p.setValues(posicao.getLinhas() + 1, posicao.getColunas());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// esquerda
		p.setValues(posicao.getLinhas(), posicao.getColunas() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// direita
		p.setValues(posicao.getLinhas(), posicao.getColunas()+1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//diagonal esquerda superior
		p.setValues(posicao.getLinhas()-1, posicao.getColunas()-1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//diagonal esquerda inferior
		p.setValues(posicao.getLinhas()+1, posicao.getColunas()-1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//diagonal direita superior
		p.setValues(posicao.getLinhas()-1, posicao.getColunas()+1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		//diagonal direita inferior
		p.setValues(posicao.getLinhas()+1, posicao.getColunas()+1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
		return mat;
	}

}
