package application;

import chess.PecaXadrez;

//UI = User Interface
public class UI {
	
	//classe que imprime o tabuleiro
	//m�todo est�tico, realiza apenas essa fun��o
	//m�todo que imprime o tabuleiro completo, com todas as pe�as alocadas
	public static void printBoard (PecaXadrez[][] pecas) {
		for (int i=0;i<pecas.length;i++) {
			System.out.print((8-i)+" ");
			for (int j=0;j<pecas.length;j++) {
				imprimePeca(pecas[i][j]);
			}
			System.out.println();
			//quebra de linha, para a pr�xima posicao do tabuleiro
		}
		System.out.println("  a b c d e f g h");
	}
	
	//m�todo que imprime cada pe�a individualmente
	public static void imprimePeca(PecaXadrez peca) {
		if (peca == null) {
			//se n�o h� pe�a naquela posicao
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

}
