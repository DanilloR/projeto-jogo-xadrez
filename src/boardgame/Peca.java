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
		//e subclasses de peças
	}
	
	//método genérico para movimentos das peças
	public abstract boolean [][] movimentosPossiveis ();
	
	//verifica se o movimento é possivel
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
