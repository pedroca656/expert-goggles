
public class Carta {
	private char[] numeracao;
	private char[] cor;
	private int valor;
	
	public Carta(char[] n, char[] c, int v) {
		numeracao = n;
		
		cor = c;
		
		valor = v;
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

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		String s = "Valor: " + valor + "\n";
		s = s + "Carta: ";
		for(int i = 0; i < cor.length; i++) {
			s = s + cor[i];
		}
		s = s + "/";
		for(int i = 0; i < numeracao.length; i++) {
			s = s + numeracao[i];
		}
		return s;
	}
}
