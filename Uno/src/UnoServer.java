import java.rmi.Naming;
import java.rmi.RemoteException;

public class UnoServer {
	private static int id = 0;
	
	public static void main (String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1);//estava "id++", verificar com o professor
			System.out.println("RMI registry ready.");			
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");			
		}
		try {
			Naming.rebind("Uno", new Uno());
			System.out.println ("UnoServer is ready.");
		} catch (Exception e) {
			System.out.println ("UnoServer failed:");
			e.printStackTrace();
		}
	}
}
