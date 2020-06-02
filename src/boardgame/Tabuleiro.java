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
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pecas [linha][coluna];
		//retorna a matriz peça, dada a linha e a coluna
	}
	
	public Peca peca (Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pecas [posicao.getLinhas()][posicao.getColunas()];
	} //retorna a peça dada a sua posicao
	
	public void posicionaPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao)) {
			throw new BoardException("Já existe uma peça na posição "+posicao);
		}
		pecas[posicao.getLinhas()][posicao.getColunas()] = peca;
		//recebe a peça como argumento e coloca na posicao 
		//também recebida como argumento
		peca.posicao = posicao;
		//atribui a peça à posicao, que não está mais nula
	}
	
	private boolean existePosicao (int linha, int coluna) {
		//método auxiliar que verifica a existência da posição]
		return linha >=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean existePosicao(Posicao posicao) {
		//método que verifica se a posicao existe ou não
		return existePosicao (posicao.getLinhas(), posicao.getColunas());
	}
	
	public boolean temUmaPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return peca(posicao) != null;
		
	}
	
}
