package application;

import chess.PecaXadrez;

//UI = User Interface
public class UI {
	
	//classe que imprime o tabuleiro
	//método estático, realiza apenas essa função
	//método que imprime o tabuleiro completo, com todas as peças alocadas
	public static void printBoard (PecaXadrez[][] pecas) {
		for (int i=0;i<pecas.length;i++) {
			System.out.print((8-i)+" ");
			for (int j=0;j<pecas.length;j++) {
				imprimePeca(pecas[i][j]);
			}
			System.out.println();
			//quebra de linha, para a próxima posicao do tabuleiro
		}
		System.out.println("  a b c d e f g h");
	}
	
	//método que imprime cada peça individualmente
	public static void imprimePeca(PecaXadrez peca) {
		if (peca == null) {
			//se não há peça naquela posicao
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

}
