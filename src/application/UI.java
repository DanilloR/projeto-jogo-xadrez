package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

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

	//limpando a tela após as jogadas
	public static void limpaTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	//lê a posicao informada pelo jogador
	public static PosicaoXadrez lerPosicaoXadrez (Scanner sc) {
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new PosicaoXadrez(coluna, linha);
	  }
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posicao. "
					+ "Posicoes validas de a1 a h8");
		}
	}
	
	//imprime a partida
	public static void imprimePartida (PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas) {
		printBoard(partidaXadrez.getPecas());
		System.out.println();
		mostraPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: "+partidaXadrez.getTurno());
		System.out.println("Esperando o jogador "+partidaXadrez.getJogadorAtual());
		
	}
	
	// classe que imprime o tabuleiro
	// método estático, realiza apenas essa função
	// método que imprime o tabuleiro completo, com todas as peças alocadas
	public static void printBoard(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j], false);
			}
			System.out.println();
			// quebra de linha, para a próxima posicao do tabuleiro
		}
		System.out.println("  a b c d e f g h");
	}

	//imprimindo o tabuleiro colorindo as possíveis jogadas da peça
	public static void printBoard(PecaXadrez[][] pecas, boolean [][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
			// quebra de linha, para a próxima posicao do tabuleiro
		}
		System.out.println("  a b c d e f g h");
	}
	// método que imprime cada peça individualmente
	public static void imprimePeca(PecaXadrez peca, boolean fundoDaPeca) {		
		/*if (peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}*/
		if (fundoDaPeca) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-"+ ANSI_RESET);
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
	
	private static void mostraPecasCapturadas (List <PecaXadrez> capturadas) {
		List<PecaXadrez> brancas = capturadas.stream().filter(x->x.getCor()==Cor.WHITE).collect(Collectors.toList());
		List<PecaXadrez> pretas = capturadas.stream().filter(x->x.getCor()==Cor.BLACK).collect(Collectors.toList());
		System.out.println();
		System.out.println("Pecas capturadas: ");
		System.out.print("Branca: ");
		System.out.print(ANSI_WHITE);
		System.out.print(Arrays.toString(brancas.toArray()));
		System.out.println(ANSI_RESET);
		
		System.out.print("Preta: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.toString(pretas.toArray()));
		System.out.println(ANSI_RESET);
	}

}
