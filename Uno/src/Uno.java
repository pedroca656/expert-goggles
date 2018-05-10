import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Uno extends UnicastRemoteObject implements JogadorInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3090969950757986494L;
	private Map<Integer, Partida> Dict;
	private LinkedList<Jogador> JogadoresRegistrados;
	private Jogador JogadorEmEspera;
	
	private int id = 0;
	
	protected Uno() throws RemoteException {
		super();
		
		Dict = new HashMap<Integer, Partida>();
		JogadoresRegistrados = new LinkedList<>();
		JogadorEmEspera = null;
	}	
	
	public static void main (String[] args) {
		
	}

	@Override
	public int registraJogador(String Nome) throws RemoteException {
		if(JogadoresRegistrados.size() >= 1000) return -2; //maximo de jog. alcancados
		
		boolean existe = false;
		//percorre a lista verificando se ja existe jogador registrado com esse usuario
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
			System.out.println("Jogador " + j.getUsuario() + " registrado.");
			if(JogadorEmEspera == null) {
				JogadorEmEspera = j;
			}
			else {
				Partida p = new Partida(JogadorEmEspera, j);
				System.out.println("Partida criada entre jogadores " + JogadorEmEspera.getUsuario() + " e " + j.getUsuario());
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
		if(JogadorEmEspera != null) {
			if(JogadorEmEspera.getId() == Id) return -1;
		}
		
		//busca a partida do jogador e finaliza ela
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -1;
			p.finalizarPartida();
			return 0;
		}
		return -1;
	}

	@Override
	public int temPartida(int Id) throws RemoteException {
		if(JogadorEmEspera != null) { // se é o jogador em espera, retorna que não existe partida
			if(JogadorEmEspera.getId() == Id) return 0;
		}
		
		if(Dict.containsKey(Id)) {
			//busca partida do jogador
			Partida p = Dict.get(Id);
			
			if(p != null) {
				//se for o J1, que é o primeiro registrado, retorna 1, se nao, 2
				if(p.getJ1().getId() == Id) return 1;
				return 2;				
			}
		}
		
		
		return -1;
	}

	@Override
	public String obtemOponente(int Id) throws RemoteException {
		//busca a partida do jogador
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return "";
			//se ele for o jogador J1 retorna o usuario do J2, se nao, o usuario do J1
			if(p.getJ1().getId() == Id) return p.getJ2().getUsuario();
			return p.getJ1().getUsuario();
		}
		return "";
	}

	@Override
	public int ehMinhaVez(int Id) throws RemoteException {
		if(JogadorEmEspera != null) {
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			
			if(p == null) {
				return -1;
			}			
			
			//se existe um vencedor, verifica quem venceu
			if(p.getVencedor() != null) {
				if(p.getVencedor().getId() == Id) return 2;
				else return 3;
			}
			else if(p.isEmpate()) {
				return 4;
			}
			else {
				if(p.isVezJ1()) { // se é a vez do J1 e o jogador é J1, retorna 1, se não, 0
					if(p.getJ1().getId() == Id) return 1;
					else return 0;
				}
				else { //não é a vez do J1, se o jogador é o J1 vai retornar 0, se não, 1
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
		if(JogadorEmEspera != null) {
			if(JogadorEmEspera.getId() == Id) return -2;
		}
			
		if(Dict.containsKey(Id)) { //busca o baralho da partida e busca o numero de cartas que contem nele
			Partida p = Dict.get(Id);
			if(p == null) return -1;
			Baralho b = p.getBar();
			if(b == null) return -1;
			return b.getNumeroCartas();
		}
		
		return -1;
	}

	@Override
	public int obtemNumCartas(int Id) throws RemoteException {
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) { //busca a partida do jogador
			Partida p = Dict.get(Id);
			if(p == null) return -1;
			Jogador aux; //variavel auxiliar
			if(p.getJ1().getId() == Id) {
				aux = p.getJ1();
				return aux.getMao().size(); //retorna o numero de cartas na mao se ele for o j1
			}
			else {
				aux = p.getJ2();
				return aux.getMao().size(); //retorna o numero de cartas na mao se ele for o j2
			}
		}
		
		return -1;
	}

	@Override
	public int obtemNumCartasOponente(int Id) throws RemoteException {
		//essa função é igual a obtemNumCartas, mas retorna as cartas do oponente
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -1;
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
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return "";
		}
		
		if(Dict.containsKey(Id)) {
			LinkedList<Carta> mao; //variavel para a mão do jogador
			String aux = ""; //variavel auxiliar que vai ser o retorno
			Partida p = Dict.get(Id);
			if(p == null) return "";
			if(p.getJ1().getId() == Id) { //se o ID for do J1, é a mão dele que vamos mostrar, se não a do J2
				mao = p.getJ1().getMao();
			}
			else mao = p.getJ2().getMao();
			
			Carta c = null; //variavel auxiliar para a carta, para percorrer a mão do jogador
			
			//for que vai da primeira carta até a última na mão do jogador
			for(int i = 0; i < mao.size(); i++) {
				c = mao.get(i); //pega a carta em i
				if(c.equals(mao.getLast())) { //se for a ultima, entra nessa rotina que é igual a de baixo, mas sem o "|" no final.
					for(int j = 0; j < c.getNumeracao().length; j++) { //percorre a lista de caracteres da parte "numerica" da carta
						aux = aux + c.getNumeracao()[j];
					}
					aux = aux + '/';
					for(int j = 0; j < c.getCor().length; j++) { //percorre a lista de caracteres da parte da cor da carta
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
					aux = aux + '|'; //adiciona o "|" que serve como divisão entre as cartas
				}
			}
			
			return aux;
		}
		
		return "";
	}

	@Override
	public String obtemCartaMesa(int Id) throws RemoteException {
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return "";
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return "";
			
			if(p.getDescartes().size() == 0) return "";
			//obtem o size-1(ultima posicao) dos descartes, ou seja, a ultima carta
			Carta c = p.getDescartes().get(p.getDescartes().size()-1); 
			
			//variavel auxiliar pra armazenar as informações das cartas e retornar no final
			String aux = "";
			
			//pega a parte "numerica" da carta
			for(int j = 0; j < c.getNumeracao().length; j++) {
				aux = aux + c.getNumeracao()[j];
			}
			aux = aux + '/';
			//pega a parte da cor da carta
			for(int j = 0; j < c.getCor().length; j++) {
				aux = aux + c.getCor()[j];
			}
			return aux;
		}
		return "";
	}

	@Override
	public int obtemCorAtiva(int Id) throws RemoteException {
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -1;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -1;
			
			if(p.getCorAtual().length > 0) {
				//pega na variavel que armazena a cor atual da partida
				char[] cor = p.getCorAtual();
				if(cor.length == 2) {
					if(cor[0] == 'A') {
						//se começa com A, o segundo caracter só pode ser Z ou M
						if(cor[1] == 'z') return 0;
						if(cor[1] == 'm') return 1;
					}
					if(cor[0] == 'V') {
						//se começa com V, o segundo caracter só pode ser D ou M
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
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -1;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -1;
			//verifica se o ID corresponde ao do jogador 1
			if(p.getJ1().getId() == Id) {
				//verifica se é a vez do J1
				if(p.isVezJ1()) {
					//verifica se o jogador já não comprou nessa rodada
					if(!p.isJogadorJaComprou()) {
						//busca a carta no topo do baralho
						Carta aux = p.getBar().compraCarta();
						//se a carta é null, é porque acabou o baralho
						if(aux == null) { 
							//se o baralho acabou, termina a partida
							p.finalizarPartida();
						}
						if(aux != null) {
							//adiciona a carta na mão do jogador
							p.getJ1().Mao.add(aux);
							//marca que o jogador já comprou nessa rodada e não pode comprar mais
							p.setJogadorJaComprou(true);
							//marca a carta comprada para verificação posterior caso ele queira descarta-la
							p.setCartaComprada(aux);
							return 0;
						}
						return -1;
					}
					else {
						//se o jogador já comprou, pula a vez dele
						p.setVezJ1(!p.isVezJ1());
						p.setJogadorJaComprou(false);
					}
				}
			}
			else {//mesma logica, mas para o J2
				if(!p.isVezJ1()) {
					if(!p.isJogadorJaComprou()) {
						Carta aux = p.getBar().compraCarta();
						if(aux == null) {
							p.finalizarPartida();
						}
						if(aux != null) {
							p.getJ2().Mao.add(aux);
							p.setJogadorJaComprou(true);
							p.setCartaComprada(aux);
							return 0;
						}
						return -1;
					}
					else {
						//se o jogador já comprou, pula a vez dele
						p.setVezJ1(!p.isVezJ1());
						p.setJogadorJaComprou(false);
					}
				}
			}
			
		}
		
		
		return 0;
	}

	@Override
	public int jogaCarta(int Id, int Pos, int Cor) throws RemoteException {
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -2;
			if(p.getJ1().getId() == Id) { //verifica se o jogador é o J1 ou o J2
				if(p.isVezJ1()) { //verifica se é a vez do J1
					//verifica se a posição informada está de acordo com a mão do jogador
					if(Pos < 0 || Pos >= p.getJ1().getMao().size()) {
						return -3;
					}
					else {
						//verifica se a carta que o jogador quer descartar pode mesmo ser descartada
						boolean jogavel = p.verificaCartaJogavel(p.getJ1().getMao().get(Pos));
						if(!jogavel) return 0;
						else {
							Carta c = p.getJ1().getMao().get(Pos);
							//verifica se a carta é um coringa e se for, se o número da cor passado por parametro está ok
							if(c.getCor()[0] == '*' && (Cor > 4 || Cor < 0)) return -3;
							
							//verifica se a carta é um compra 4, se for, percorre a mão do jogador
							//verificando se não tem otura carta que pode ser jogada no lugar do compra 4
							if(c.getNumeracao().length > 1) {
								if(c.getNumeracao()[1] == '4') {
									for(int i = 0; i < p.getJ1().getMao().size(); i++) {
										Carta aux = p.getJ1().getMao().get(i);
										//se é um compra 4, ignora
										if(aux.getNumeracao().length > 1) {
											if(aux.getNumeracao()[1] == '4') continue;
										}
										if(p.verificaCartaJogavel(aux)) return 0;
									}
								}
							}
							
							//remove a carta da mão do jogador, adiciona nos descartes,
							//troca a vez do jogador, armazena a cor e a numeração atual para fins posteriores
							p.getJ1().getMao().remove(Pos);
							p.getDescartes().push(c);
							p.setCorAtual(c.getCor());
							p.setNumeroAtual(c.getNumeracao());
							p.setVezJ1(!p.isVezJ1());
							p.setJogadorJaComprou(false);
							
							//agora a verificacao de cartas especiais
							//coringa
							if(c.getCor()[0] == '*') {
								//+4
								if(c.getNumeracao()[1] == 4) {
									p.J2Compra4();
									p.setVezJ1(!p.isVezJ1());
								}
								switch(Cor) {
									case 0:
										p.setCorAtual(new char[] {'A', 'z'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
										
									case 1:
										p.setCorAtual(new char[] {'A', 'm'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
											
									case 2:
										p.setCorAtual(new char[] {'V', 'd'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
											
									case 3:
										p.setCorAtual(new char[] {'V', 'm'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
								}
								
							}
							//+2
							else if(c.getNumeracao()[0] == '+') {
								p.J2Compra2();
								p.setVezJ1(!p.isVezJ1());
							}
							//pula vez ou inverte
							else if(c.getNumeracao()[0] == 'P' || c.getNumeracao()[0] == 'I') {
								p.setVezJ1(!p.isVezJ1());
							}
							p.setPrimeiraCartaCoringa(false);
							if(p.getJ1().getMao().size() == 0) p.finalizarPartida();
							return 1;
						}
						
					}
				}
				else return -4;
			}
			else if(p.getJ2().getId() == Id) { //mesma logica, mas agora com o J2
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
							if(c.getNumeracao().length > 1) {
								if(c.getNumeracao()[1] == '4') {
									for(int i = 0; i < p.getJ2().getMao().size(); i++) {
										Carta aux = p.getJ2().getMao().get(i);
										if(aux.getNumeracao()[1] == '4') continue;
										if(p.verificaCartaJogavel(aux)) return 0;
									}
								}
							}
							p.getJ2().getMao().remove(Pos);
							p.getDescartes().push(c);
							p.setCorAtual(c.getCor());
							p.setNumeroAtual(c.getNumeracao());
							p.setVezJ1(!p.isVezJ1());
							p.setJogadorJaComprou(false);
							
							//agora a verificacao de cartas especiais
							//coringa
							if(c.getCor()[0] == '*') {
								//+4
								if(c.getNumeracao()[1] == 4) {
									p.J1Compra4();
									p.setVezJ1(!p.isVezJ1());
								}
								switch(Cor) {
									case 0:
										p.setCorAtual(new char[] {'A', 'z'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
										
									case 1:
										p.setCorAtual(new char[] {'A', 'm'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
											
									case 2:
										p.setCorAtual(new char[] {'V', 'd'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
											
									case 3:
										p.setCorAtual(new char[] {'V', 'm'});
										p.setPrimeiraCartaCoringa(false);
										return 1;
								}
								
							}
							//+2
							else if(c.getNumeracao()[0] == '+') {
								p.J1Compra2();
								p.setVezJ1(!p.isVezJ1());
							}
							//pula vez
							else if(c.getNumeracao()[0] == 'P' || c.getNumeracao()[0] == 'I') {
								p.setVezJ1(!p.isVezJ1());
							}
							p.setPrimeiraCartaCoringa(false);
							if(p.getJ1().getMao().size() == 0) p.finalizarPartida();
							return 1;
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
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -2;
			//verifica se o vencedor é null e se empate é false, a partida não acabou
			if(p.getVencedor() == null && p.isEmpate() == false) {
				return -3;
			}
			if(p.getJ1().getId() == Id) return p.getPontuacaoJ1();
			else return p.getPontuacaoJ2();
		}
		else {
			return -1;
		}
	}

	@Override
	public int obtemPontosOponente(int Id) throws RemoteException {
		if(JogadorEmEspera != null) {	
			if(JogadorEmEspera.getId() == Id) return -2;
		}
		
		if(Dict.containsKey(Id)) {
			Partida p = Dict.get(Id);
			if(p == null) return -2;
			//verifica se o vencedor é null e se empate é false, a partida não acabou
			if(p.getVencedor() == null && p.isEmpate() == false) {
				return -3;
			}
			if(p.getJ1().getId() == Id) return p.getPontuacaoJ2();
			else return p.getPontuacaoJ1();
		}
		else {
			return -1;
		}

	}
}
