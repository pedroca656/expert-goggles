import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Uno extends UnicastRemoteObject implements JogadorInterface {
	private Map<Integer, Partida> Dict;
	private LinkedList<Jogador> JogadoresRegistrados;
	private Jogador JogadorEmEspera;
	
	private int id = 0;
	
	private static final long serialVersionUID = -513804057617910473L;
	
	protected Uno() throws RemoteException {
		super();
		
		Dict = new HashMap<Integer, Partida>(1000);
		JogadoresRegistrados = new LinkedList<>();
	}	
	
	public static void main (String[] args) {
		
	}

	@Override
	public int registraJogador(String Nome) throws RemoteException {
		if(JogadoresRegistrados.size() >= 1000) return -2;
		
		boolean existe = false;
		if(JogadoresRegistrados.size() > 0) {
			for(int i = 0; i < JogadoresRegistrados.size(); i++) {
				if(JogadoresRegistrados.get(i).getUsuario().equals(Nome)) {
					existe = true;
				}
			}
		}
		
		if(!existe) {
			Jogador j = new Jogador(id++, Nome);
			
		}
		 
		
		return -1;
	}

	@Override
	public int encerraPartida(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int temPartida(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemOponente(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ehMinhaVez(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemNumCartasBaralho(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemNumCartas(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemNumCartasOponente(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String mostraMao(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtemCartaMesa(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int obtemCorAtiva(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compraCarta(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int jogaCarta(int Id, int Pos, int Cor) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemPontos(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtemPontosOponente(int Id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
}
