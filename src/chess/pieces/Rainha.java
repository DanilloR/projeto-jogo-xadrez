package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	// possíveis movimentos da torre
	public boolean[][] movimentosPossiveis() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// verifica acima
		p.setValues(posicao.getLinhas() - 1, posicao.getColunas());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setLinhas(p.getLinhas() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// verifica esquerda
		p.setValues(posicao.getLinhas(), posicao.getColunas() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setColunas(p.getColunas() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// verifica direita
		p.setValues(posicao.getLinhas(), posicao.getColunas() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setColunas(p.getColunas() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}

		// verifica abaixo
		p.setValues(posicao.getLinhas() + 1, posicao.getColunas());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
			p.setLinhas(p.getLinhas() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaAdversaria(p)) {
			mat[p.getLinhas()][p.getColunas()] = true;
		}
		
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