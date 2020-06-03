package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	//m�todo que verifica se h� uma pe�a advers�ria
	//na casa de destino	
	protected boolean temPecaAdversaria (Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor()!=cor;
	}

	public Cor getCor() {
		return cor;
		//a cor da pe�a n�o pode ser modificada,
		//apenas acessada.
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
}
