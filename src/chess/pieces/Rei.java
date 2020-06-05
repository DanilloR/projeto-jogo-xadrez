package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Rei extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
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
	
	private boolean testaTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre &&
				p.getCor() == getCor() &&
				p.getContaMovimentos() == 0;
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
		
		//Movimento especial de Roque
		if (getContaMovimentos() == 0 && !partidaXadrez.getXeque()) {
			// Roque do lado do rei (direita)
			Posicao posT1 = new Posicao (posicao.getLinhas(), posicao.getColunas()+3);
			if (testaTorreRoque(posT1)) {
				Posicao p1 = new Posicao (posicao.getLinhas(), posicao.getColunas()+1);
				Posicao p2 = new Posicao (posicao.getLinhas(), posicao.getColunas()+2);
				if (getTabuleiro().peca(p1)==null &&
						getTabuleiro().peca(p2)==null) {
					mat[posicao.getLinhas()][posicao.getColunas()+2] = true;
				}
			}
			
			//roque do lado da rainha (esquerda)
			Posicao posT2 = new Posicao (posicao.getLinhas(), posicao.getColunas()-4);
			if (testaTorreRoque(posT2)) {
				Posicao p1 = new Posicao (posicao.getLinhas(), posicao.getColunas()-1);
				Posicao p2 = new Posicao (posicao.getLinhas(), posicao.getColunas()-2);
				Posicao p3 = new Posicao (posicao.getLinhas(), posicao.getColunas()-3);
				if (getTabuleiro().peca(p1)==null &&
						getTabuleiro().peca(p2)==null && 
						getTabuleiro().peca(p3)==null) {
					mat[posicao.getLinhas()][posicao.getColunas()-2] = true;
				}
			}
		}
		
		return mat;
	}

}
