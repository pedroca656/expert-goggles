
public class Jogo {

	public static void main (String[] args) {
		Baralho b = new Baralho();
		
		Carta c;
		while((c = b.CompraCarta()) != null) {
			System.out.println(c.toString());
		}
	}
}
