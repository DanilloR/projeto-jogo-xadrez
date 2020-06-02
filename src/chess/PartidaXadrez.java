package chess;

import boardgame.Peca;
import boardgame.Posicao;
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
		//estabelece as dimens�es do tabuleiro
	}
	
	public PecaXadrez [][] getPecas() { //m�todo que retorna matriz de pe�as
		//o programa enxerga apenas a camada de xadrez, n�o a de tabuleiro
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()]
				[tabuleiro.getColunas()];
		//variavel auxiliar, recebendo linhas e colunas do tabuleiro
		for (int i=0;i<tabuleiro.getLinhas();i++) {
			for (int j=0;j<tabuleiro.getColunas();j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
				/* percorre a matriz de pe�as do tabuleiro, e pra cada pe�a
				 * � feito um downcasting pra preencher a matriz com as pe�as */
			}
		}
		return mat;
	}
	
	
	public PecaXadrez executaJogada (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosicaoOrigem(origem);
		Peca pecaCapturada = movePeca(origem, destino);
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movePeca (Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.posicionaPeca(p, destino);
		return pecaCapturada;
	}
	
	//valida��o da posi��o de origem
	private void validaPosicaoOrigem (Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Nao ha uma peca na posicao de origem!");
		}
	}
	
	//metodo que recebe as coordenadas do xadrez
	private void alocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.posicionaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	/*private void iniciaPartida() {
		//inicia a partida, colocando as pe�as no tabuleiro;
		tabuleiro.posicionaPeca(new Torre(tabuleiro, Cor.WHITE), new Posicao(2,1));
		//ainda � uma posi��o comum de matriz
		tabuleiro.posicionaPeca(new Rei(tabuleiro,Cor.BLACK), new Posicao(0,4));
		tabuleiro.posicionaPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7,4));
	}*/
	
	private void iniciaPartida() {
		
		//alocando pe�as de cor branca
		alocaNovaPeca('c', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('c', 2, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('d', 2, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 2, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.WHITE));

		alocaNovaPeca('c', 7, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('c', 8, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('d', 7, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('e', 7, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('e', 8, new Torre(tabuleiro, Cor.BLACK));
        alocaNovaPeca('d', 8, new Rei(tabuleiro, Cor.BLACK));
	}

}
