package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.WHITE) {
			p.setValues(posicao.getLinhas()-1, posicao.getColunas());
			if (getTabuleiro().existePosicao(p) &&  !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()-2, posicao.getColunas());
			Posicao p2 = new Posicao (posicao.getLinhas()-1, posicao.getColunas());
			if (getTabuleiro().existePosicao(p) &&  !getTabuleiro().temUmaPeca(p) 
					&& getTabuleiro().existePosicao(p2) 
					&&  !getTabuleiro().temUmaPeca(p2)
					&& getContaMovimentos()==0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()-1, posicao.getColunas()-1);
			if (getTabuleiro().existePosicao(p) &&  temPecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()-1, posicao.getColunas()+1);
			if (getTabuleiro().existePosicao(p) &&  temPecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			//en passant peças brancas
			if (posicao.getLinhas()==3) {
				Posicao esquerda = new Posicao (posicao.getLinhas(), posicao.getColunas()-1); 
				if (getTabuleiro().existePosicao(esquerda) && temPecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()) {
					mat[esquerda.getLinhas()-1][esquerda.getColunas()] = true;
				}
				
				Posicao direita = new Posicao (posicao.getLinhas(), posicao.getColunas()+1); 
				if (getTabuleiro().existePosicao(direita) && temPecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()) {
					mat[direita.getLinhas()-1][direita.getColunas()] = true;
				}
			}
		}
		else {
			p.setValues(posicao.getLinhas()+1, posicao.getColunas());
			if (getTabuleiro().existePosicao(p) &&  !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()+2, posicao.getColunas());
			Posicao p2 = new Posicao (posicao.getLinhas()+1, posicao.getColunas());
			if (getTabuleiro().existePosicao(p) &&  !getTabuleiro().temUmaPeca(p) 
					&& getTabuleiro().existePosicao(p2) 
					&&  !getTabuleiro().temUmaPeca(p2)
					&& getContaMovimentos()==0) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()+1, posicao.getColunas()-1);
			if (getTabuleiro().existePosicao(p) &&  temPecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			p.setValues(posicao.getLinhas()+1, posicao.getColunas()+1);
			if (getTabuleiro().existePosicao(p) &&  temPecaAdversaria(p)) {
				mat[p.getLinhas()][p.getColunas()] = true;
			}
			
			
			//en passant peças pretas
			if (posicao.getLinhas()==4) {
				Posicao esquerda = new Posicao (posicao.getLinhas(), posicao.getColunas()-1); 
				if (getTabuleiro().existePosicao(esquerda) && temPecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassant()) {
					mat[esquerda.getLinhas()+1][esquerda.getColunas()] = true;
				}
				
				Posicao direita = new Posicao (posicao.getLinhas(), posicao.getColunas()+1); 
				if (getTabuleiro().existePosicao(direita) && temPecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassant()) {
					mat[direita.getLinhas()+1][direita.getColunas()] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}

}
