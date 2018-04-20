import java.util.Stack;

public class Jogo {
	private static Stack<Carta> descartes; //pilha de descartes
	private static int countCompras2; //contador de quantos +2 em sequencia foram jogados
	private static int countCompras4; //contador de quantos +4 em sequencia foram jogados
	private static char[] corAtual; //variavel que armazena cor do topo da pilha de descartes
	private static char[] numeroAtual; //variavel que armazena numero no topo da pilha de desc.

	public static void main (String[] args) {
		Baralho baralho = new Baralho();
		baralho.embaralhar(1, 2);
		descartes = new Stack<Carta>();
		countCompras2 = 0;
		countCompras4 = 0;
		corAtual = new char[2];
		numeroAtual = new char[2];
		
		Carta c;
		/*while((c = baralho.compraCarta()) != null) {
			System.out.println(c.toString());
		}*/
		c = new Carta(new char[] {'2'}, new char[] {'V', 'm'}, 5);
		
		corAtual[0] = 'V';
		corAtual[1] = 'm';
		numeroAtual = new char[] {'2'};
		
		System.out.println(verificaCartaJogavel(c));
	}
	
	private static boolean verificaCartaJogavel(Carta c) {
		//verifica se nao tem +2 em sequencia
		if(countCompras2 > 0) {
			if(c.getNumeracao()[0] == '+') {
				countCompras2++;
				return true;
			}
			else {
				return false;
			}
		}
		//verifica se nao tem +4 em sequencia
		if(countCompras4 > 0) {
			if(c.getNumeracao()[1] == '4') {
				countCompras4++;
				return true;
			}
			else {
				return false;
			}
		}
		
		//verifica se a carta eh da mesma cor
		if(c.getCor()[0] == corAtual[0] && c.getCor()[1] == corAtual[1]) {
			return true;
		}
		
		//verifica se carta eh do mesmo numero ou eh coringa
		
		
		return false;
	}
}
