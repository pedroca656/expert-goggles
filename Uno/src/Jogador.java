import java.util.*;

public class Jogador {
	
	private int Id;
	private String Usuario;
	LinkedList<Carta> Mao;
	
	public Jogador(int id, String us) {
		Id = id;
		Usuario = us;
		Mao = new LinkedList<Carta>();
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	public LinkedList<Carta> getMao() {
		return Mao;
	}
	public void setMao(LinkedList<Carta> mao) {
		Mao = mao;
	}
	
	
}
