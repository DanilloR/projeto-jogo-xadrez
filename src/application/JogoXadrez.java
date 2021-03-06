package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;
import chess.XadrezExcecao;

public class JogoXadrez {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PartidaXadrez partida = new PartidaXadrez();
		Scanner sc = new Scanner (System.in);
		List<PecaXadrez> capturadas = new ArrayList<>();
		//instancia uma nova partida
		
		while (!partida.getXequeMate()) {
			try {
				UI.limpaTela();
				UI.imprimePartida(partida, capturadas);
				//imprime as pe�as da partida
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean [][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limpaTela();
				UI.printBoard(partida.getPecas(), movimentosPossiveis);				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partida.executaJogada(origem, destino);
				
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if (partida.getPromocao()!=null) {
					System.out.print("Informe a peca para promocao "+
							"(B/C/T/Q): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("C")
							&& !tipo.equals("T") && !tipo.equals("Q")) {
						System.out.print("Valor Invalido. Informe a peca para promocao "+
								"(B/C/T/Q): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partida.trocaPecaPromovida(tipo);
				}
		}
			catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
	}
		UI.limpaTela();
		UI.imprimePartida(partida, capturadas);
  }

}
