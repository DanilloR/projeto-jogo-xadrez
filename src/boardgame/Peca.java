package boardgame;

public abstract class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}


	protected Tabuleiro getTabuleiro() {
		return tabuleiro; //acessado somente em Tabuleiro
		//e subclasses de pe�as
	}
	
	//m�todo gen�rico para movimentos das pe�as
	public abstract boolean [][] movimentosPossiveis ();
	
	//verifica se o movimento � possivel
	public boolean movimentoPossivel (Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinhas()]
				[posicao.getColunas()];
	}
	
	public boolean haAlgumMovimentoPossivel() {
		boolean [][] mat = movimentosPossiveis();
		for (int i=0;i<mat.length;i++) {
			for (int j=0; j<mat.length;j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		
		return false;
	}

}
