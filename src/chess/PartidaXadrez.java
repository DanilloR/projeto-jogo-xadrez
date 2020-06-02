package chess;

import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	
	//Classe que possui as regras do jogo
	
	
	public Tabuleiro tabuleiro;
	//a partida tem que ter um tabuleiro
	
	public PartidaXadrez () {
		tabuleiro = new Tabuleiro (8,8);
		iniciaPartida();
		//estabelece as dimensões do tabuleiro
	}
	
	public PecaXadrez [][] getPecas() { //método que retorna matriz de peças
		//o programa enxerga apenas a camada de xadrez, não a de tabuleiro
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()]
				[tabuleiro.getColunas()];
		//variavel auxiliar, recebendo linhas e colunas do tabuleiro
		for (int i=0;i<tabuleiro.getLinhas();i++) {
			for (int j=0;j<tabuleiro.getColunas();j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
				/* percorre a matriz de peças do tabuleiro, e pra cada peça
				 * é feito um downcasting pra preencher a matriz com as peças */
			}
		}
		return mat;
	}
	
	//metodo que recebe as coordenadas do xadrez
	private void alocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.posicionaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	/*private void iniciaPartida() {
		//inicia a partida, colocando as peças no tabuleiro;
		tabuleiro.posicionaPeca(new Torre(tabuleiro, Cor.WHITE), new Posicao(2,1));
		//ainda é uma posição comum de matriz
		tabuleiro.posicionaPeca(new Rei(tabuleiro,Cor.BLACK), new Posicao(0,4));
		tabuleiro.posicionaPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7,4));
	}*/
	
	private void iniciaPartida() {
		alocaNovaPeca('b', 6, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK));
		alocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE));
	}

}
