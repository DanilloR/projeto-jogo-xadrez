package chess;

import boardgame.Peca;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
		//a cor da peça não pode ser modificada,
		//apenas acessada.
	}
	
}
