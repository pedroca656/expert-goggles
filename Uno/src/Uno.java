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
		JogadorEmEspera = null;
	}	
	
	public static void main (String[] args) {
		
	}

	@Override
	public int registraJogador(String Nome) throws RemoteException {
		if(JogadoresRegistrados.size() >= 1000) return -2; //maximo de jog. alcancados
		
		boolean existe = false;
		//percore a lista verificando se ja existe jogador registrado com esse usuario
		if(JogadoresRegistrados.size() > 0) {
			for(int i = 0; i < JogadoresRegistrados.size(); i++) {
				if(JogadoresRegistrados.get(i).getUsuario().equals(Nome)) {
					existe = true;
				}
			}
		}
		
		//se nao existe, cria o jogador, e se tem jogador em espera, comeca uma partida com ambos
		//se nao tem jogador em espera, coloca o jogador recem criado em espera
		if(!existe) {
			Jogador j = new Jogador(id++, Nome);
			if(JogadorEmEspera == null) {
				JogadorEmEspera = j;
			}
			else {
				Partida p = new Partida(JogadorEmEspera, j);
				Dict.put(JogadorEmEspera.getId(), p);
				Dict.put(j.getId(), p);
				JogadorEmEspera = null;
			}
			JogadoresRegistrados.add(j);
			return j.getId();
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
		if(JogadorEmEspera.getId() == Id) return 0;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			
			if(p.getJ1().getId() == Id) return 1;
			return 2;
		}
		
		
		return -1;
	}

	@Override
	public String obtemOponente(int Id) throws RemoteException {
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p.getJ1().getId() == Id) return p.getJ2().getUsuario();
			return p.getJ2().getUsuario();
		}
		return "";
	}

	@Override
	public int ehMinhaVez(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -2;
		
		if(Dict.containsKey(id)) {
			Partida p = Dict.get(Id);
			
			if(p.getVencedor() != null) {
				if(p.getVencedor().getId() == Id) return 2;
				else return 3;
			}
			else if(p.isEmpate()) {
				return 4;
			}
			else {
				if(p.isVezJ1()) {
					if(p.getJ1().getId() == Id) return 1;
					else return 0;
				}
				else {
					if(p.getJ1().getId() == Id) return 0;
					else return 1;
				}
			}
		}
		
		//TODO: implementar os WO.
		
		return -1;
	}

	@Override
	public int obtemNumCartasBaralho(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -2;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			Baralho b = p.getBar();
			return b.getNumeroCartas();
		}
		
		return -1;
	}

	@Override
	public int obtemNumCartas(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -2;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			Jogador aux;
			if(p.getJ1().getId() == Id) {
				aux = p.getJ1();
				return aux.getMao().size();
			}
			else {
				aux = p.getJ2();
				return aux.getMao().size();
			}
		}
		
		return -1;
	}

	@Override
	public int obtemNumCartasOponente(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -2;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			Jogador aux;
			if(p.getJ1().getId() == Id) {
				aux = p.getJ2();
				return aux.getMao().size();
			}
			else {
				aux = p.getJ1();
				return aux.getMao().size();
			}
		}
		
		return -1;
	}

	@Override
	public String mostraMao(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return "";
		
		if(Dict.containsKey(Id)) {
			LinkedList<Carta> mao;
			String aux = "";
			Partida p = Dict.get(Id);
			if(p.getJ1().getId() == Id) {
				mao = p.getJ1().getMao();
			}
			else mao = p.getJ2().getMao();
			
			Carta c = null;
			
			for(int i = 0; i < mao.size(); i++) {
				c = mao.get(i);
				if(c.equals(mao.getLast())) {
					for(int j = 0; j < c.getNumeracao().length; j++) {
						aux = aux + c.getNumeracao()[j];
					}
					aux = aux + '/';
					for(int j = 0; j < c.getCor().length; j++) {
						aux = aux + c.getCor()[j];
					}
				}
				else {
					for(int j = 0; j < c.getNumeracao().length; j++) {
						aux = aux + c.getNumeracao()[j];
					}
					aux = aux + '/';
					for(int j = 0; j < c.getCor().length; j++) {
						aux = aux + c.getCor()[j];
					}
					aux = aux + '|';
				}
			}
			
			return aux;
		}
		
		return "";
	}

	@Override
	public String obtemCartaMesa(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return "";
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			
			if(p.getDescartes().size() == 0) return "";
			Carta c = p.getDescartes().get(p.getDescartes().size()-1);
			
			String aux = "";
			
			for(int j = 0; j < c.getNumeracao().length; j++) {
				aux = aux + c.getNumeracao()[j];
			}
			aux = aux + '/';
			for(int j = 0; j < c.getCor().length; j++) {
				aux = aux + c.getCor()[j];
			}
		}
		return "";
	}

	@Override
	public int obtemCorAtiva(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -1;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			
			if(p.getCorAtual().length > 0) {
				char[] cor = p.getCorAtual();
				if(cor.length == 2) {
					if(cor[0] == 'A') {
						if(cor[1] == 'z') return 0;
						if(cor[1] == 'm') return 1;
					}
					if(cor[0] == 'V') {
						if(cor[1] == 'd') return 2;
						if(cor[1] == 'm') return 3;
					}
				}
			}
		}
		
		return -1;
	}

	@Override
	public int compraCarta(int Id) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -1;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p.getJ1().getId() == Id) {
				if(p.isVezJ1()) {
					//verifica se o jogador não tem que comprar cartas por causa de cartas especiais
					if(p.getCountCompras2() != 0) {
						int count = p.getCountCompras2();
						while(count != 0) {
							Carta aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							count--;
						}
						
						p.setCountCompras2(0);
						p.setVezJ1(!p.isVezJ1());
						return 0;
					}
					else if(p.getCountCompras4() != 0) {
						int count = p.getCountCompras4();
						while(count != 0) {
							Carta aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ1().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							count--;
						}
						
						p.setCountCompras4(0);
						p.setVezJ1(!p.isVezJ1());
						return 0;
					}
					else if(!p.isJogadorJaComprou()) {
						Carta aux = p.getBar().compraCarta();
						//TODO: reseta baralho se aux == null
						if(aux != null) {
							p.getJ1().Mao.add(aux);
							p.setJogadorJaComprou(true);
							p.setCartaComprada(aux);
							return 0;
						}
						return -1;
					}
				}
			}
			else {
				if(!p.isVezJ1()) {
					if(p.getCountCompras2() != 0) {
						int count = p.getCountCompras2();
						while(count != 0) {
							Carta aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							count--;
						}
						
						p.setCountCompras2(0);
						p.setVezJ1(!p.isVezJ1());
						return 0;
					}
					else if(p.getCountCompras4() != 0) {
						int count = p.getCountCompras4();
						while(count != 0) {
							Carta aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							aux = p.getBar().compraCarta();
							//TODO: reseta baralho se aux == null
							if(aux != null) {
								p.getJ2().Mao.add(aux);
								p.setCartaComprada(aux);
							}
							
							count--;
						}
						
						p.setCountCompras4(0);
						p.setVezJ1(!p.isVezJ1());
						return 0;
					}
					else if(!p.isJogadorJaComprou()) {
						Carta aux = p.getBar().compraCarta();
						if(aux != null) {
							p.getJ2().Mao.add(aux);
							p.setJogadorJaComprou(true);
							p.setCartaComprada(aux);
							return 0;
						}
						return -1;
					}
				}
			}
			
		}
		
		
		return 0;
	}

	@Override
	public int jogaCarta(int Id, int Pos, int Cor) throws RemoteException {
		if(JogadorEmEspera.getId() == Id) return -2;
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p.getJ1().getId() == Id) {
				if(p.isVezJ1()) {
					if(Pos < 0 || Pos >= p.getJ1().getMao().size()) {
						return -3;
					}
					else {
						boolean jogavel = p.verificaCartaJogavel(p.getJ1().getMao().get(Pos));
						if(!jogavel) return 0;
						else {
							Carta c = p.getJ1().getMao().get(Pos);
							if(c.getCor()[0] == '*' && (Cor > 4 || Cor < 0)) return -3;
							p.getJ1().getMao().remove(Pos);
							p.getDescartes().push(c);
							p.setCorAtual(c.getCor());
							p.setNumeroAtual(c.getNumeracao());
							p.setVezJ1(!p.isVezJ1());
							
							//agora a verificacao de cartas especiais
							//coringa
							if(c.getCor()[0] == '*') {
								//+4
								if(c.getNumeracao()[1] == 4) {
									p.setCountCompras4(p.getCountCompras4() + 1);
								}
								switch(Cor) {
									case 0:
										p.setCorAtual(new char[] {'A', 'z'});
										return 1;
										
									case 1:
										p.setCorAtual(new char[] {'A', 'm'});
										return 1;
											
									case 2:
										p.setCorAtual(new char[] {'V', 'd'});
										return 1;
											
									case 3:
										p.setCorAtual(new char[] {'V', 'm'});
										return 1;
								}
								
							}
							//+2
							else if(c.getNumeracao()[0] == '+') {
								p.setCountCompras2(p.getCountCompras2() + 1);
							}
							//pula vez
							else if(c.getNumeracao()[0] == 'P' || c.getNumeracao()[0] == 'I') {
								p.setVezJ1(!p.isVezJ1());
							}
							
						}
					}
				}
				else return -4;
			}
			else if(p.getJ2().getId() == Id) {
				if(!p.isVezJ1()) {
					if(Pos < 0 || Pos >= p.getJ2().getMao().size()) {
						return -3;
					}
					else {
						boolean jogavel = p.verificaCartaJogavel(p.getJ2().getMao().get(Pos));
						if(!jogavel) return 0;
						else {
							Carta c = p.getJ2().getMao().get(Pos);
							if(c.getCor()[0] == '*' && (Cor > 4 || Cor < 0)) return -3;
							p.getJ2().getMao().remove(Pos);
							p.getDescartes().push(c);
							p.setCorAtual(c.getCor());
							p.setNumeroAtual(c.getNumeracao());
							p.setVezJ1(!p.isVezJ1());
							
							//agora a verificacao de cartas especiais
							//coringa
							if(c.getCor()[0] == '*') {
								//+4
								if(c.getNumeracao()[1] == 4) {
									p.setCountCompras4(p.getCountCompras4() + 1);
								}
								switch(Cor) {
									case 0:
										p.setCorAtual(new char[] {'A', 'z'});
										return 1;
										
									case 1:
										p.setCorAtual(new char[] {'A', 'm'});
										return 1;
											
									case 2:
										p.setCorAtual(new char[] {'V', 'd'});
										return 1;
											
									case 3:
										p.setCorAtual(new char[] {'V', 'm'});
										return 1;
								}
								
							}
							//+2
							else if(c.getNumeracao()[0] == '+') {
								p.setCountCompras2(p.getCountCompras2() + 1);
							}
							//pula vez
							else if(c.getNumeracao()[0] == 'P' || c.getNumeracao()[0] == 'I') {
								p.setVezJ1(!p.isVezJ1());
							}
							
						}
					}
				}
				else return -4;
			}
		}
		else {
			return -1;
		}
		
		
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
