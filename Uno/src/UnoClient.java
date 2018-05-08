import java.rmi.Naming;

public class UnoClient {
	public static void main (String[] args) {
		int n;

		if	(args.length != 2)  {
			System.out.println("Uso: java NotasClient <maquina> <nome>");
			System.exit(1);
		}
		try {
			JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//"+args[0]+"/Jogador");
			n = jogador.registraJogador(args[1]);
			System.out.println ("Nome de Usuário: "+args[1]);
			if	(n == -1)
				System.out.println ("Erro: Usuário já cadastrado.\n");
			else if (n == -2)
				System.out.println ("Erro: Número máximo de jogadores já alcançado.");
			else
				System.out.println("Jogador " + args[1] + " cadastrado!");
		} catch (Exception e) {
			System.out.println ("UnoClient failed.");
			e.printStackTrace();
		}
	}
}
