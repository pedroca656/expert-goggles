import java.util.Stack;

public class Partida {
	
	private Stack<Carta> descartes; //pilha de descartes
	private int countCompras2; //contador de quantos +2 em sequencia foram jogados
	private int countCompras4; //contador de quantos +4 em sequencia foram jogados
	private char[] corAtual; //variavel que armazena cor do topo da pilha de descartes
	private char[] numeroAtual; //variavel que armazena numero no topo da pilha de desc.
	
	private Jogador J1;	
	private Jogador J2;
	
	private Baralho Bar;
	
	public Partida(Jogador p1, Jogador p2) {
		J1 = p1;
		J2 = p2;
		
		Bar = new Baralho();
		Bar.embaralhar(J1.getId(), J2.getId());
		descartes = new Stack<Carta>();
		countCompras2 = 0;
		countCompras4 = 0;
		corAtual = new char[2];
		numeroAtual = new char[2];
		
		for(int i = 0; i < 7; i++) {
			J1.getMao().add(Bar.compraCarta());
			J2.getMao().add(Bar.compraCarta());
		}
	}
	
	private boolean verificaCartaJogavel(Carta c) {
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
