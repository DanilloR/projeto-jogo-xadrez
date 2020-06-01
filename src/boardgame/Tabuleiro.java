package boardgame;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca [][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca [linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}


	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}


	public int getColunas() {
		return colunas;
	}


	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	
	public Peca peca (int linha, int coluna) {
		return pecas [linha][coluna];
		//retorna a matriz pe�a, dada a linha e a coluna
	}
	
	public Peca peca (Posicao posicao) {
		return pecas [posicao.getLinhas()][posicao.getColunas()];
	} //retorna a pe�a dada a sua posicao
	
	public void posicionaPeca(Peca peca, Posicao posicao) {
		pecas[posicao.getLinhas()][posicao.getColunas()] = peca;
		//recebe a pe�a como argumento e coloca na posicao 
		//tamb�m recebida como argumento
		peca.posicao = posicao;
		//atribui a pe�a � posicao, que n�o est� mais nula
	}
	
}
