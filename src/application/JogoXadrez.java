package application;

import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;

public class JogoXadrez {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PartidaXadrez partida = new PartidaXadrez();
		Scanner sc = new Scanner (System.in);
		//instancia uma nova partida
		
		while (true) {
			
			UI.printBoard(partida.getPecas());
			//imprime as peças da partida
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
			PecaXadrez pecaCapturada = partida.executaJogada(origem, destino);
		}
		
	}

}
