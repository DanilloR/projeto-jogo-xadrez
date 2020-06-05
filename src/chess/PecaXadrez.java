package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	private int contaMovimentos;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	//método que verifica se há uma peça adversária
	//na casa de destino	
	protected boolean temPecaAdversaria (Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor()!=cor;
	}

	public Cor getCor() {
		return cor;
		//a cor da peça não pode ser modificada,
		//apenas acessada.
	}
	
	public void incrementaMovimentos() {
		contaMovimentos++;
	}
	
	public void decrementaMovimentos() {
		contaMovimentos--;
	}
	
	public int getContaMovimentos() {
		return contaMovimentos;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
}
