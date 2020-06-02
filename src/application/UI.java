package application;

import chess.Cor;
import chess.PecaXadrez;

//UI = User Interface
public class UI {

	// cores para imprimir no console
	// cores do texto
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// classe que imprime o tabuleiro
	// método estático, realiza apenas essa função
	// método que imprime o tabuleiro completo, com todas as peças alocadas
	public static void printBoard(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j]);
			}
			System.out.println();
			// quebra de linha, para a próxima posicao do tabuleiro
		}
		System.out.println("  a b c d e f g h");
	}

	// método que imprime cada peça individualmente
	public static void imprimePeca(PecaXadrez peca) {		
		/*if (peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}*/
		if (peca == null) {
			System.out.print("-");
		}
		else {
			if (peca.getCor() == Cor.WHITE) {
				System.out.print(ANSI_WHITE+ peca 
						+ ANSI_RESET);
			} 
			else {
				System.out.print(ANSI_YELLOW + peca
						+ ANSI_RESET);
			}
				
		}
		System.out.print(" ");
	}

}
