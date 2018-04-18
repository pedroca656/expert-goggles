
public class Carta {
	private char numeracao;
	private char[] cor;
	private int valor;
	
	public Carta(char n, char[] c, int v) {
		//numeracao = new char[2];
		numeracao = n;
		
		//cor = new char[2];
		cor = c;
		
		valor = v;
	}

	public char getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(char numeracao) {
		this.numeracao = numeracao;
	}

	public char[] getCor() {
		return cor;
	}

	public void setCor(char[] cor) {
		this.cor = cor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
}
