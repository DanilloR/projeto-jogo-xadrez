package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	
	//Classe que possui as regras do jogo
	
	private int turno;
	private Cor jogadorAtual;
	public Tabuleiro tabuleiro;
	//a partida tem que ter um tabuleiro
	private boolean xeque;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez () {
		tabuleiro = new Tabuleiro (8,8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		iniciaPartida();
		//estabelece as dimensões do tabuleiro
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
	
	//mostra as possíveis jogadas
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
		
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca movePeca (Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.posicionaPeca(p, destino);
		
		if (pecaCapturada!= null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	//método que desfaz o movimento
	private void desfazMovimento (Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.posicionaPeca(p, origem);
		
		if (pecaCapturada!=null) {
			tabuleiro.posicionaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	//validação da posição de origem
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
	
	//validação da posição de destino
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
	
	//método que testa se rei está em xeque
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
		//inicia a partida, colocando as peças no tabuleiro;
		tabuleiro.posicionaPeca(new Torre(tabuleiro, Cor.WHITE), new Posicao(2,1));
		//ainda é uma posição comum de matriz
		tabuleiro.posicionaPeca(new Rei(tabuleiro,Cor.BLACK), new Posicao(0,4));
		tabuleiro.posicionaPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7,4));
	}*/
	
	private void iniciaPartida() {
		
		//alocando peças de cor branca
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
