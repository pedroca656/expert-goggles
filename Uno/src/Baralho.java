import java.util.Stack;

public class Baralho {
	private Stack<Carta> cartas;
	
	public Baralho() {
		cartas = new Stack<Carta>();
		
		int valor = 0;
		char[] cor = new char[2];
		Carta c;
		for(int i = 0; i < 4; i++) {
			if(i == 0) {
				cor[0] = 'A';
				cor[1] = 'z';
			}
			else if(i == 1) {
				cor[0] = 'A';
				cor[1] = 'm';
			}
			else if(i == 2) {
				cor[0] = 'V';
				cor[1] = 'd';
			}
			else if(i == 3) {
				cor[0] = 'V';
				cor[1] = 'm';
			}
			
			c = new Carta('0', cor, valor);
			
			for(int j = 1; j < 10; j++) {
				c = new Carta((char)j, cor, valor);
				valor++;
				cartas.push(c);
				c = new Carta((char)j, cor, valor);
				valor++;
				cartas.push(c);
			}
			
			c = new Carta('P', cor, valor);
		}
		
	}
}
