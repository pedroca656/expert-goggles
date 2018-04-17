
public class Baralho {
	private Carta[] cartas;
	
	public Baralho() {
		cartas = new Carta[108];
		
		int valor = 0;
		char[] cor = new char[2];
		for(int i = 0; i < 4; i++) {
			if(i == 0) {
				cor[0] = 'z';
				cor[1] = 'A';
			}
			else if(i == 1) {
				cor[0] = 'm';
				cor[1] = 'A';
			}
			else if(i == 2) {
				cor[0] = 'd';
				cor[1] = 'V';
			}
			else if(i == 3) {
				cor[0] = 'm';
				cor[1] = 'V';
			}
			for(int j = 0; j < 14; j++) {
				
			}
		}
		
	}
}
