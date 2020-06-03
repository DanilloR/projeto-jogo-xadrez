package chess;

import boardgame.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	
	public PosicaoXadrez(char coluna, int linha) {
		//lança a exceção caso as coordenadas estejam fora dos limites
		//do tabuleiro
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro ao instanciar a posicao. Valores validos: a1 a h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}


	public char getColuna() {
		return coluna;
	}


	public int getLinha() {
		return linha;
	}
	
	//converte da posicao matriz para a posicao do tabuleiro
	protected Posicao toPosicao() {
		return new Posicao (8-linha, coluna -'a');
	}
	
	//retorna a posição matriz dada a posição do tabuleiro
	protected static PosicaoXadrez fromPosicao (Posicao posicao) {
		return new PosicaoXadrez ((char)('a'+posicao.getColunas()), 8-posicao.getLinhas());
	}
	
	@Override
	public String toString() {
		return ""
				+ linha
				+ coluna;
	}
}
