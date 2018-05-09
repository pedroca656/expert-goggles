import java.rmi.Naming;
import java.util.Scanner;

public class UnoClient {
	public static void main (String[] args) {
		int n;

		if	(args.length != 2)  {
			System.out.println("Uso: java NotasClient <maquina> <nome>");
			System.exit(1);
		}
		try {
			//JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//"+args[0]+"/Uno");
			JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//localhost/Uno");
			/*n = jogador.registraJogador(args[1]);
			System.out.println ("Nome de Usuário: "+args[1]);
			if	(n == -1)
				System.out.println ("Erro: Usuário já cadastrado.\n");
			else if (n == -2)
				System.out.println ("Erro: Número máximo de jogadores já alcançado.");
			else
				System.out.println("Jogador " + args[1] + " cadastrado!");*/
			while(true) {
				int op = 0;
				int id = -1;

				Scanner scanner = new Scanner(System.in);
				
				System.out.println("Lista de comandos:");
				System.out.println("1 - Registro               2 - Encerrar Partida");
				System.out.println("3 - Existência de Partida  4 - Verificar Oponente");
				System.out.println("5 - É sua Vez              6 - Número de Cartas do Baralho");
				System.out.println("7 - No. de Cartas na Mão   8 - No. de Cartas do Oponente");
				System.out.println("9 - Ver Cartas na Mão      10- Ver Carta Na Mesa");
				System.out.println("11- Ver Cor Ativa na Mesa  12- Comprar Uma Carta / Cartas de Cartas Especiais");
				System.out.println("13- Jogar Carta da Mão     14- Obter Seus Pontos ");
				System.out.println("14- Obter Pontos do Oponente");
				
				op = System.in.read();
				switch(op) {
					case 1:
						System.out.println("\nDigite o nome de Usuário");
						String usuario = scanner.nextLine();
						id = jogador.registraJogador(usuario);
						if(id == -1) {
							System.out.println("\nUsuário já cadastrado!");
							break;
						}
						else if(id == -2) {
							System.out.println("\nNúmero máximo de jogadores já atingido!");
							break;
						}
						else System.out.println("\nSeu ID de usuário é: " + id);
						break;
				}
			}
		} catch (Exception e) {
			System.out.println ("UnoClient failed.");
			e.printStackTrace();
		}		
	}
}
