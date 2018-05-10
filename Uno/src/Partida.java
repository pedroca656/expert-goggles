import java.util.Stack;

public class Partida {
	
	private Stack<Carta> descartes; //pilha de descartes
	private int countCompras2; //contador de quantos +2 em sequencia foram jogados
	private int countCompras4; //contador de quantos +4 em sequencia foram jogados
	private char[] corAtual; //variavel que armazena cor do topo da pilha de descartes
	private char[] numeroAtual; //variavel que armazena numero no topo da pilha de desc.
	
	public int getPontuacaoJ1() {
		return pontuacaoJ1;
	}

	public void setPontuacaoJ1(int pontuacaoJ1) {
		this.pontuacaoJ1 = pontuacaoJ1;
	}

	public int getPontuacaoJ2() {
		return pontuacaoJ2;
	}

	public void setPontuacaoJ2(int pontuacaoJ2) {
		this.pontuacaoJ2 = pontuacaoJ2;
	}

	private Jogador J1;
	private int pontuacaoJ1;
	private Jogador J2;
	private int pontuacaoJ2;
	private Jogador vencedor;

	private Baralho Bar;
	
	private boolean primeiraCartaCoringa;
	private boolean vezJ1;
	private boolean empate;
	private boolean jogadorJaComprou;
	
	private Carta cartaComprada;

	public Partida(Jogador p1, Jogador p2) {
		J1 = p1;
		J2 = p2;
		
		pontuacaoJ1 = pontuacaoJ2 = 0;
		
		vezJ1 = true;
		empate = false;
		jogadorJaComprou = false;
		
		vencedor = null;
		cartaComprada = null;
		primeiraCartaCoringa = false;
		
		Bar = new Baralho();
		Bar.embaralhar(J1.getId(), J2.getId());
		descartes = new Stack<Carta>();
		corAtual = new char[2];
		numeroAtual = new char[2];
		
		for(int i = 0; i < 7; i++) {
			J1.getMao().add(Bar.compraCarta());
			J2.getMao().add(Bar.compraCarta());
		}
		
		
		
		Carta aux = Bar.compraCarta();
		if(aux.getNumeracao().length > 1) {
			if(aux.getNumeracao()[1] == '4') {
				descartes.push(aux);
				while(true) {
					aux = Bar.compraCarta();
					if(aux.getNumeracao()[1] == '4') {
						descartes.push(aux);
					}
					else {
						break;
					}
				}
			}
		}
		if(aux.getNumeracao()[0] == 'P' || aux.getNumeracao()[0] == 'I') {
			vezJ1 = !vezJ1;
		}
		if(aux.getNumeracao()[0] == '+') {
			J1Compra2();
			vezJ1 = !vezJ1;
		}
		if(aux.getNumeracao()[0] == 'C') {
			primeiraCartaCoringa = true;
		}
		
		descartes.push(aux);
		corAtual = aux.getCor();
		numeroAtual = aux.getNumeracao();
	}
	
	public void J1Compra4() {
		for(int i = 0; i < 4; i++) {		
			if(Bar.getNumeroCartas() != 0) {
				J1.getMao().add(Bar.compraCarta());
			}
			else {
				finalizarPartida();
				J1.getMao().add(Bar.compraCarta());
			}
		}
	}
	
	public void J2Compra4() {
		for(int i = 0; i < 4; i++) {		
			if(Bar.getNumeroCartas() != 0) {
				J2.getMao().add(Bar.compraCarta());
			}
			else {
				finalizarPartida();;
				J2.getMao().add(Bar.compraCarta());
			}
		}
	}
	
	public void J1Compra2() {
		for(int i = 0; i < 2; i++) {		
			if(Bar.getNumeroCartas() != 0) {
				J1.getMao().add(Bar.compraCarta());
			}
			else {
				finalizarPartida();
				J1.getMao().add(Bar.compraCarta());
			}
		}
	}
	
	public void J2Compra2() {
		for(int i = 0; i < 2; i++) {		
			if(Bar.getNumeroCartas() != 0) {
				J2.getMao().add(Bar.compraCarta());
			}
			else {
				finalizarPartida();
				J2.getMao().add(Bar.compraCarta());
			}
		}
	}
	
	public void remontaBaralho() {
		while(descartes.size() > 0) {
			Bar.getCartas().push(descartes.pop());
		}
	}
	
	public Carta getCartaComprada() {
		return cartaComprada;
	}

	public void setCartaComprada(Carta cartaComprada) {
		this.cartaComprada = cartaComprada;
	}
	
	public boolean isJogadorJaComprou() {
		return jogadorJaComprou;
	}

	public void setJogadorJaComprou(boolean jogadorJaComprou) {
		this.jogadorJaComprou = jogadorJaComprou;
	}

	public boolean verificaCartaJogavel(Carta c) {
		if(primeiraCartaCoringa) return true;
		
		//verifica se a carta eh da mesma cor
		if(c.getCor()[0] == corAtual[0] && c.getCor()[1] == corAtual[1]) {
			return true;
		}
		
		//verifica se carta eh do mesmo numero ou eh coringa
		if(c.getNumeracao()[0] == numeroAtual[0] || c.getNumeracao()[0] == 'C') {
			return true;
		}
		
		return false;
	}
	
	public boolean isPrimeiraCartaCoringa() {
		return primeiraCartaCoringa;
	}

	public void setPrimeiraCartaCoringa(boolean primeiraCartaCoringa) {
		this.primeiraCartaCoringa = primeiraCartaCoringa;
	}

	public boolean isEmpate() {
		return empate;
	}

	public void setEmpate(boolean empate) {
		this.empate = empate;
	}
	
	public Jogador getVencedor() {
		return vencedor;
	}	

	public void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public Stack<Carta> getDescartes() {
		return descartes;
	}

	public void setDescartes(Stack<Carta> descartes) {
		this.descartes = descartes;
	}

	public int getCountCompras2() {
		return countCompras2;
	}

	public void setCountCompras2(int countCompras2) {
		this.countCompras2 = countCompras2;
	}

	public int getCountCompras4() {
		return countCompras4;
	}

	public void setCountCompras4(int countCompras4) {
		this.countCompras4 = countCompras4;
	}

	public char[] getCorAtual() {
		return corAtual;
	}

	public void setCorAtual(char[] corAtual) {
		this.corAtual = corAtual;
	}

	public char[] getNumeroAtual() {
		return numeroAtual;
	}

	public void setNumeroAtual(char[] numeroAtual) {
		this.numeroAtual = numeroAtual;
	}

	public Jogador getJ1() {
		return J1;
	}

	public void setJ1(Jogador j1) {
		J1 = j1;
	}

	public Jogador getJ2() {
		return J2;
	}

	public void setJ2(Jogador j2) {
		J2 = j2;
	}

	public Baralho getBar() {
		return Bar;
	}

	public void setBar(Baralho bar) {
		Bar = bar;
	}

	public boolean isVezJ1() {
		return vezJ1;
	}

	public void setVezJ1(boolean vezJ1) {
		this.vezJ1 = vezJ1;
	}
	
	public void finalizarPartida() {
		for(int i = 0; i < J1.getMao().size(); i++) {
			Carta aux = J1.getMao().get(i);
			if(aux != null) {
				if(aux.getNumeracao()[0] == 'C') {
					pontuacaoJ2 += 50;
				}
				else if(aux.getNumeracao()[0] == 'P' || aux.getNumeracao()[0] == 'I') {
					pontuacaoJ2 += 20;
				}
				else pontuacaoJ2 += aux.getNumeracao()[0] - '0';
			}
		}
		for(int i = 0; i < J2.getMao().size(); i++) {
			Carta aux = J2.getMao().get(i);
			if(aux != null) {
				if(aux.getNumeracao()[0] == 'C') {
					pontuacaoJ1 += 50;
				}
				else if(aux.getNumeracao()[0] == 'P' || aux.getNumeracao()[0] == 'I') {
					pontuacaoJ1 += 20;
				}
				else pontuacaoJ1 += aux.getNumeracao()[0] - '0';
			}
		}
		
		if(pontuacaoJ2 == pontuacaoJ1) empate = true;
		else if(pontuacaoJ1 > pontuacaoJ2) vencedor = J1;
		else vencedor = J2;
	}
}
