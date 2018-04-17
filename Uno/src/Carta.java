
public class Carta {
	private char[] numeracao;
	private char[] cor;
	
	public Carta(char[] n, char[] c) {
		numeracao = new char[2];
		numeracao = n;
		
		cor = new char[2];
		cor = c;
	}

	public char[] getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(char[] numeracao) {
		this.numeracao = numeracao;
	}

	public char[] getCor() {
		return cor;
	}

	public void setCor(char[] cor) {
		this.cor = cor;
	}
	
}
