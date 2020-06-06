package chess;

import java.security.InvalidParameterException;
//import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Peao;
import chess.pieces.Rainha;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {

	// Classe que possui as regras do jogo

	private int turno;
	private Cor jogadorAtual;
	public Tabuleiro tabuleiro;
	// a partida tem que ter um tabuleiro
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez enPassant;
	private PecaXadrez promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		iniciaPartida();
		// estabelece as dimensões do tabuleiro
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez getEnPassant() {
		return enPassant;
	}
	
	public PecaXadrez getPromocao () {
		return promocao;
	}

	public PecaXadrez[][] getPecas() { // método que retorna matriz de peças
		// o programa enxerga apenas a camada de xadrez, não a de tabuleiro
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		// variavel auxiliar, recebendo linhas e colunas do tabuleiro
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
				/*
				 * percorre a matriz de peças do tabuleiro, e pra cada peça é feito um
				 * downcasting pra preencher a matriz com as peças
				 */
			}
		}
		return mat;
	}

	// mostra as possíveis jogadas
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaXadrez executaJogada(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		Peca pecaCapturada = movePeca(origem, destino);
		
		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
		
		//Jogada especial de promoção	
		promocao = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor()==Cor.WHITE && destino.getLinhas()==0)
					|| pecaMovida.getCor()==Cor.BLACK && destino.getLinhas()==7) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = trocaPecaPromovida("Q");
			}
		}

		if (testaXeque(jogadorAtual)) {
			desfazMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Voce nao pode se colocar em xeque");
		}

		
		xeque = (testaXeque(oponente(jogadorAtual))) ? true : false;

		if (testaXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// en passant
		if (pecaMovida instanceof Peao
				&& (destino.getLinhas() == origem.getLinhas() - 2 
				|| destino.getLinhas() == origem.getLinhas() + 2)) {
			enPassant = pecaMovida;
		} else {
			enPassant = null;
		}
		
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez trocaPecaPromovida (String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida");
		}
		if (!tipo.equals("B") && !tipo.equals("C")
				&& !tipo.equals("T") && !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = pecaNova(tipo, promocao.getCor());
		tabuleiro.posicionaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;		
	}
	
	private PecaXadrez pecaNova (String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca movePeca(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.incrementaMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.posicionaPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// Movimento Especial Roque pequeno
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() + 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() + 3);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.posicionaPeca(torre, destinoT);
			torre.incrementaMovimentos();
		}

		// Movimento Especial Roque grande
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() - 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() - 4);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.posicionaPeca(torre, destinoT);
			torre.incrementaMovimentos();
		}

		// en passant
		if (p instanceof Peao) {
			if (origem.getColunas() != destino.getColunas() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.WHITE) {
					posicaoPeao = new Posicao(destino.getLinhas() + 1, destino.getColunas());
				} else {
					posicaoPeao = new Posicao(destino.getLinhas() - 1, destino.getColunas());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	// método que desfaz o movimento
	private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		p.decrementaMovimentos();
		tabuleiro.posicionaPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.posicionaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// Movimento Especial Roque pequeno
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() + 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() + 3);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.posicionaPeca(torre, origemT);
			torre.decrementaMovimentos();
		}

		// Movimento Especial Roque grande
		if (p instanceof Rei && destino.getColunas() == origem.getColunas() - 2) {
			Posicao origemT = new Posicao(origem.getLinhas(), origem.getColunas() - 4);
			Posicao destinoT = new Posicao(origem.getLinhas(), origem.getColunas() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.posicionaPeca(torre, origemT);
			torre.decrementaMovimentos();
		}

		// en passant
		if (p instanceof Peao) {
			if (origem.getColunas() != destino.getColunas() && pecaCapturada == enPassant) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.WHITE) {
					posicaoPeao = new Posicao(3, destino.getColunas());
				} else {
					posicaoPeao = new Posicao(4, destino.getColunas());
				}
				tabuleiro.posicionaPeca(peao, posicaoPeao);
			}
		}
	}

	// validação da posição de origem
	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Nao ha uma peca na posicao de origem!");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peca escolhida nao e sua!");
		}
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Nao existem movimentos " + "possiveis para a peca escolhida");
		}
	}

	// validação da posição de destino
	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peca escolhida nao pode ser" + " movida para a posicao de destino");
		}
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	// retorna o rei da cor
	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe um rei " + cor + " no tabuleiro");
	}

	// método que testa se rei está em xeque
	private boolean testaXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinhas()][posicaoRei.getColunas()]) {
				return true;
			}
		}
		return false;
	}

	// método que verifica se está em xeque mate
	private boolean testaXequeMate(Cor cor) {

		// se a cor não estiver em xeque, não está em xeque mate
		if (!testaXeque(cor)) {
			return false;
		}

		// captura as peças da cor e armazena em uma lista
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		// percorre as peças que estão na lista
		for (Peca p : list) {

			boolean[][] mat = p.movimentosPossiveis();
			// procura os possíveis movimentos para sair do xeque
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					// a posicao é um movimento possível? ela tira do xeque?
					if (mat[i][j]) {
						// peça a posicão atual da peça
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						// se houver movimento possível, faz o movimento para a posicao destino
						// definida pela matriz
						Peca pecaCapturada = movePeca(origem, destino);
						// faz o teste que verifica se o rei ainda está em xeque
						boolean testeXeque = testaXeque(cor);
						// desfaz o movimento de teste
						desfazMovimento(origem, destino, pecaCapturada);
						// se não está mais em xeque, retorna false
						if (!testeXeque) {
							return false;
						}

					}
				}
			}
		}

		return true;
	}

	// metodo que recebe as coordenadas do xadrez
	private void alocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.posicionaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	/*
	 * private void iniciaPartida() { //inicia a partida, colocando as peças no
	 * tabuleiro; tabuleiro.posicionaPeca(new Torre(tabuleiro, Cor.WHITE), new
	 * Posicao(2,1)); //ainda é uma posição comum de matriz
	 * tabuleiro.posicionaPeca(new Rei(tabuleiro,Cor.BLACK), new Posicao(0,4));
	 * tabuleiro.posicionaPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7,4)); }
	 */

	private void iniciaPartida() {

		// alocando peças de cor branca
		alocaNovaPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('d', 1, new Rainha(tabuleiro, Cor.WHITE));
		alocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
		alocaNovaPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
		alocaNovaPeca('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
		alocaNovaPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));

		alocaNovaPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
		alocaNovaPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
		alocaNovaPeca('d', 8, new Rainha(tabuleiro, Cor.BLACK));
		alocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
		alocaNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
		alocaNovaPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
		alocaNovaPeca('a', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('b', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('c', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('d', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('e', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('f', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('g', 7, new Peao(tabuleiro, Cor.BLACK, this));
		alocaNovaPeca('h', 7, new Peao(tabuleiro, Cor.BLACK, this));
	}

}
