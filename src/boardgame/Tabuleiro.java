package boardgame;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca [][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Erro ao criar tabuleiro."
					+ "Deve haver pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca [linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca (int linha, int coluna) {
		if (!existePosicao(linha, coluna)) {
			throw new BoardException("Posi��o n�o existe no tabuleiro");
		}
		return pecas [linha][coluna];
		//retorna a matriz pe�a, dada a linha e a coluna
	}
	
	public Peca peca (Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new BoardException("Posi��o n�o existe no tabuleiro");
		}
		return pecas [posicao.getLinhas()][posicao.getColunas()];
	} //retorna a pe�a dada a sua posicao
	
	public void posicionaPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao)) {
			throw new BoardException("J� existe uma pe�a na posi��o "+posicao);
		}
		pecas[posicao.getLinhas()][posicao.getColunas()] = peca;
		//recebe a pe�a como argumento e coloca na posicao 
		//tamb�m recebida como argumento
		peca.posicao = posicao;
		//atribui a pe�a � posicao, que n�o est� mais nula
	}
	
	private boolean existePosicao (int linha, int coluna) {
		//m�todo auxiliar que verifica a exist�ncia da posi��o]
		return linha >=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean existePosicao(Posicao posicao) {
		//m�todo que verifica se a posicao existe ou n�o
		return existePosicao (posicao.getLinhas(), posicao.getColunas());
	}
	
	public boolean temUmaPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new BoardException("Posi��o n�o existe no tabuleiro");
		}
		return peca(posicao) != null;
		
	}
	
}
