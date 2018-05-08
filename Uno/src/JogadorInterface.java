import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JogadorInterface extends Remote {
	
	public int registraJogador(String Nome) throws RemoteException;
	
	public int encerraPartida(int Id) throws RemoteException;
	
	public int temPartida(int Id) throws RemoteException;
	
	public String obtemOponente(int Id) throws RemoteException;
	
	public int ehMinhaVez(int Id) throws RemoteException;
	
	public int obtemNumCartasBaralho(int Id) throws RemoteException;
	
	public int obtemNumCartas(int Id) throws RemoteException;
	
	public int obtemNumCartasOponente(int Id) throws RemoteException;
	
	public String mostraMao(int Id) throws RemoteException;
	
	public String obtemCartaMesa(int Id) throws RemoteException;
	
	public int obtemCorAtiva(int Id) throws RemoteException;
	
	public int compraCarta(int Id) throws RemoteException;
	
	public int jogaCarta(int Id, int Pos, int Cor) throws RemoteException;
	
	public int obtemPontos(int Id) throws RemoteException;
	
	public int obtemPontosOponente(int Id) throws RemoteException;
}
