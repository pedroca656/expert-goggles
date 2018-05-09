import java.util.Stack;

public class Partida {
	
	private Stack<Carta> descartes; //pilha de descartes
	private int countCompras2; //contador de quantos +2 em sequencia foram jogados
	private int countCompras4; //contador de quantos +4 em sequencia foram jogados
	private char[] corAtual; //variavel que armazena cor do topo da pilha de descartes
	private char[] numeroAtual; //variavel que armazena numero no topo da pilha de desc.
	
	private Jogador J1;	
	private Jogador J2;
	private Jogador Vencedor;

	private Baralho Bar;
	
	private boolean primeiraCartaCoringa;
	private boolean vezJ1;
	private boolean empate;
	private boolean jogadorJaComprou;
	
	private Carta cartaComprada;

	public Partida(Jogador p1, Jogador p2) {
		J1 = p1;
		J2 = p2;
		
		vezJ1 = true;
		empate = false;
		jogadorJaComprou = false;
		
		Vencedor = null;
		cartaComprada = null;
		primeiraCartaCoringa = false;
		
		Bar = new Baralho();
		Bar.embaralhar(J1.getId(), J2.getId());
		descartes = new Stack<Carta>();
		//countCompras2 = 0;
		//countCompras4 = 0;
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
				remontaBaralho();
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
				remontaBaralho();
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
				remontaBaralho();
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
				remontaBaralho();
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
		//verifica se nao tem +2 em sequencia
		/*if(countCompras2 > 0) {
			if(c.getNumeracao()[0] == '+') {
				countCompras2++;
				return true;
			}
			else {
				return false;
			}
		}*/
		//verifica se nao tem +4 em sequencia
		/*if(countCompras4 > 0) {
			if(c.getNumeracao()[1] == '4') {
				countCompras4++;
				return true;
			}
			else {
				return false;
			}
		}*/
		
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
		return Vencedor;
	}	

	public void setVencedor(Jogador vencedor) {
		Vencedor = vencedor;
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
	
	
}
