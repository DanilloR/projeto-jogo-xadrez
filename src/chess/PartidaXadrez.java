package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bispo;
import chess.pieces.Peao;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	
	//Classe que possui as regras do jogo
	
	private int turno;
	private Cor jogadorAtual;
	public Tabuleiro tabuleiro;
	//a partida tem que ter um tabuleiro
	private boolean xeque;
	private boolean xequeMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez () {
		tabuleiro = new Tabuleiro (8,8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		iniciaPartida();
		//estabelece as dimens�es do tabuleiro
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque () {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
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
	
	//mostra as poss�veis jogadas
	public boolean [][] movimentosPossiveis (PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	
	public PecaXadrez executaJogada (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		Peca pecaCapturada = movePeca(origem, destino);
		
		if (testaXeque(jogadorAtual)) {
			desfazMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Voce nao pode se colocar em xeque");
		}
		
		xeque = (testaXeque(oponente(jogadorAtual)))?true:false;
		
		if (testaXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
			proximoTurno();
		}
		
		
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movePeca (Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.incrementaMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.posicionaPeca(p, destino);
		
		if (pecaCapturada!= null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	//m�todo que desfaz o movimento
	private void desfazMovimento (Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.decrementaMovimentos();
		tabuleiro.posicionaPeca(p, origem);
		
		if (pecaCapturada!=null) {
			tabuleiro.posicionaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	//valida��o da posi��o de origem
	private void validaPosicaoOrigem (Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Nao ha uma peca na posicao de origem!");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peca escolhida nao e sua!");
		}
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Nao existem movimentos "
					+ "possiveis para a peca escolhida");
		}
	}
	
	//valida��o da posi��o de destino
	private void validaPosicaoDestino (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peca escolhida nao pode ser"
					+ " movida para a posicao de destino");
		}
	}
	
	
	private Cor oponente (Cor cor) {
		return (cor == Cor.WHITE)? Cor.BLACK: Cor.WHITE;
	}
	
	
	//retorna o rei da cor
	private PecaXadrez rei (Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor()==cor).collect(Collectors.toList());
		for (Peca p:lista) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe um rei "+cor+" no tabuleiro");
	}
	
	//m�todo que testa se rei est� em xeque
	private boolean testaXeque (Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor()==oponente(cor)).collect(Collectors.toList());
		for (Peca p:pecasOponentes) {
			boolean [][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinhas()][posicaoRei.getColunas()]) {
				return true;
			}
		}
		return false;
	}
	
	
	//m�todo que verifica se est� em xeque mate
	private boolean testaXequeMate(Cor cor) {
		
		//se a cor n�o estiver em xeque, n�o est� em xeque mate
		if (!testaXeque(cor)) {
			return false;
		}
		
		//captura as pe�as da cor e armazena em uma lista
		List<Peca> list = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor()==cor).collect(Collectors.toList());
		
		//percorre as pe�as que est�o na lista
		for (Peca p:list) {
			
			boolean [][] mat = p.movimentosPossiveis();
			//procura os poss�veis movimentos para sair do xeque
			for (int i=0;i<tabuleiro.getLinhas();i++) {
				for (int j=0;j<tabuleiro.getColunas();j++) {
					//a posicao � um movimento poss�vel? ela tira do xeque?
					if (mat[i][j]) {
						//pe�a a posic�o atual da pe�a
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao (i,j);
						//se houver movimento poss�vel, faz o movimento para a posicao destino
						//definida pela matriz
						Peca pecaCapturada = movePeca(origem, destino);
						//faz o teste que verifica se o rei ainda est� em xeque
						boolean testeXeque = testaXeque(cor);
						//desfaz o movimento de teste
						desfazMovimento(origem, destino, pecaCapturada);
						//se n�o est� mais em xeque, retorna false
						if (!testeXeque) {
							return false;
						}
						
					}
				}
			}
		}
		
		return true;
	}
	
	//metodo que recebe as coordenadas do xadrez
	private void alocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.posicionaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void proximoTurno () {
		turno ++;
		jogadorAtual = (jogadorAtual==Cor.WHITE) ?
				Cor.BLACK:Cor.WHITE;
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
		alocaNovaPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE));
		alocaNovaPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('a', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('b', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('c', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('d', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('f', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('g', 2, new Peao(tabuleiro, Cor.WHITE));
		alocaNovaPeca('h', 2, new Peao(tabuleiro, Cor.WHITE));
		
		

		alocaNovaPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
        alocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK));
        alocaNovaPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
        alocaNovaPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
        alocaNovaPeca('a', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('b', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('c', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('d', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('e', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('f', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('g', 7, new Peao(tabuleiro, Cor.BLACK));
        alocaNovaPeca('h', 7, new Peao(tabuleiro, Cor.BLACK));
	}

}
