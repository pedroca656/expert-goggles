import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class UnoClient {
	public static void main (String[] args) {

		if	(args.length != 1)  {
			System.out.println("Uso: java NotasClient <maquina>");
			System.exit(1);
		}
		try {
			JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//"+args[0]+"/Uno");
			while(true) {
				int aux = 0;
				int id = -1;
				boolean temPartida = false;
				
				BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Digite o nome de Usu�rio");				
				String usuario = scanner.readLine();
				id = jogador.registraJogador(usuario);
				if(id == -1) {
					System.out.println("\nUsu�rio j� cadastrado!");
					break;
				}
				else if(id == -2) {
					System.out.println("\nN�mero m�ximo de jogadores j� atingido!");
					break;
				}
				else System.out.println("\nSeu ID de usu�rio �: " + id);
				
				System.out.println("Buscando partida...");
				
				while(jogador.temPartida(id) == 0) {
					
				}
				
				aux = jogador.temPartida(id);
				if(aux == -2) {
					System.out.println("Tempo de espera esgotado!");
					break;
				}
				else if(aux == -1) {
					System.out.println("Ouve algum erro, tente novamente...");
					break;
				}
				else if(aux == 1) {
					System.out.println("Partida encontrada!\nVoc� come�a jogando...");
					temPartida = true;
				}
				else if(aux == 2) {
					System.out.println("Partida encontrada!\nAdvers�rio come�a jogando...");
					temPartida = true;
				}
				else {
					System.out.println("Erro inesperado");
					break;
				}
				
				System.out.println("Voc� est� jogando contra " + jogador.obtemOponente(id));
				
				//enquanto a partida existir, fica dentro desse loop
				while(temPartida) {
					//vai verificando se � a vez do jogador
					aux = jogador.ehMinhaVez(id);
					
					if(aux == -2) continue;
					else if(aux == -1) {
						System.out.println("\nErro inesperado, entrando em contato com o servidor...");
					}
					else if(aux == 1) { //VEZ DO JOGADOR!!
						System.out.println("� sua vez!\n");
						printaMesa(jogador.obtemNumCartasBaralho(id), jogador.obtemNumCartasOponente(id), jogador.obtemCartaMesa(id), jogador.obtemCorAtiva(id), jogador.mostraMao(id));
						
						//variavel auxiliar para verificar se o jogador j� terminou a jogada
						boolean jogando = true;
						
						//mantem dentro do loop enquanto o jogador n�o terminar a jogada
						//a jogada termina com o descarte de uma carta ou ao pular a sua vez
						while(jogando) {
							System.out.println("\nDigite a posi��o da carta que deseja jogar ou C para comprar uma carta.");
							//le a entrada

							String s = scanner.readLine();
							int posCarta = -1; //variavel auxiliar
							char c = 'x'; //variavel auxiliar
							if(isInteger(s)) {
								//se a entrada s pode ser convertido pra integer, converte
								posCarta = Integer.parseInt(s);
							}
							else {
								if(!s.isEmpty()) c = s.charAt(0); //se n�o, coloca em c o primeiro caracter da string
							}
							//System.out.println(posCarta);
							//System.out.println(c);
							//caso o jogador use algum caracter que n�o esteja especificado, repete at� ele inserir algo especificado
							while(true) {
								if(c == 'C') break;
								if(posCarta >= 1 && posCarta <= jogador.obtemNumCartas(id)) break;
								System.out.println("Entrada incorreta!");
								printaMesa(jogador.obtemNumCartasBaralho(id), jogador.obtemNumCartasOponente(id), jogador.obtemCartaMesa(id), jogador.obtemCorAtiva(id), jogador.mostraMao(id));
								System.out.println("\nDigite a posi��o da carta que deseja jogar ou C para comprar uma carta.");
								
								s = scanner.readLine();
								if(isInteger(s)) {
									//se a entrada s pode ser convertido pra integer, converte
									posCarta = Integer.parseInt(s);
								}
								else {
									if(!s.isEmpty()) c = s.charAt(0);
								} //se n�o, coloca em c o primeiro caracter da string
							}
						
							if(c == 'C') {
								jogador.compraCarta(id);
								printaMesa(jogador.obtemNumCartasBaralho(id), jogador.obtemNumCartasOponente(id), jogador.obtemCartaMesa(id), jogador.obtemCorAtiva(id), jogador.mostraMao(id));
															
								System.out.println("\nDigite D para descartar a nova carta ou P para pular sua vez.");
								s = scanner.readLine();
								if(!s.isEmpty()){
									c = s.charAt(0);
								}
								while(true) {
									if(c == 'P') break;
									if(c == 'D') break;
									System.out.println("Entrada incorreta!");
									System.out.println("\nDigite D para descartar a nova carta ou P para pular sua vez.");
									s = scanner.readLine();
									if(!s.isEmpty()){
										c = s.charAt(0);
									}
								}
								if(c == 'P') {
									//pulando, o compra carta pula a jogada caso o jogador j� tenha comprado
									jogador.compraCarta(id);
									System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
									//jogada terminou
									jogando = false;
								}
								else if(c == 'D') {
									System.out.println("Se a carta for um coringa, digite a cor que deseja:");
									System.out.println("0 - Azul;   1 - Amarelo;   2 - Verde;   3 - Vermelho;");
									System.out.println("Se n�o for, digite qualquer coisa.");
									char cor = 'x';
									s = scanner.readLine();
									if(!s.isEmpty()){
										cor = s.charAt(0);
									}
									int ok = jogador.jogaCarta(id, jogador.obtemNumCartas(id)-1, cor);
									if(ok == 1) {
										System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
										//jogada terminou.
										jogando = false;
									}
									if(ok == 0) {
										System.out.println("Carta inv�lida, pulando sua vez.");
										jogador.compraCarta(id);
										jogando = false;
									}
									if(ok == -3) {
										System.out.println("Jogada inv�lida, pulando sua vez.");
										jogador.compraCarta(id);
										jogando = false;
									}
								}
							}
							else if(posCarta >= 1 && posCarta <= jogador.obtemNumCartas(id) ) {
								System.out.println("Se a carta for um coringa, digite a cor que deseja:");
								System.out.println("0 - Azul;   1 - Amarelo;   2 - Verde;   3 - Vermelho;");
								System.out.println("Se n�o for, digite qualquer coisa.");
								char cor = 'x';
								s = scanner.readLine();
								if(!s.isEmpty()){
									cor = s.charAt(0);
								}
								int ok = jogador.jogaCarta(id, posCarta-1, cor-'0');
								if(ok == 1) {
									System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
									//jogada terminou.
									jogando = false;
								}
								if(ok == 0) {
									System.out.println("Jogada inv�lida, confirme a carta selecionada!");
								}
								if(ok == -3) {
									System.out.println("Jogada inv�lida, verifique se informou um n�mero correto da m�o e um n�mero correto de cor!");
								}
							}
						}
						
						//vez do adversario
						
					}
					else if(aux == 0 ) continue;
					else {
						//partida acabou
						System.out.println("A partida acabou!");
						if(aux == 2) System.out.println("Parab�ns, voc� � o vencedor!");
						else if(aux == 3) System.out.println("O advers�rio venceu essa partida...");
						else if(aux == 4) System.out.println("Ocorreu um empate!");
						else if(aux == 5) System.out.println("Voc� venceu por WO!");
						else if(aux == 6) System.out.println("O advers�rio venceu por WO...");
						if(aux == 2 || aux == 3 || aux == 4) {
							System.out.println("Sua pontua��o foi: " + jogador.obtemPontos(id));
							System.out.println("Seu oponente pontuou: " + jogador.obtemPontosOponente(id));
						}
						temPartida = false;
					}
				}
			
			scanner.close();
			break;
			}
		} catch (Exception e) {
			System.out.println ("UnoClient failed.");
			e.printStackTrace();
		}		
	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static void printaMesa(int numCartasBaralho, int numCartasOponente, String cartaMesa, int corAtiva, String mao) {
		System.out.println("N�mero de cartas no baralho: " + numCartasBaralho);
		System.out.println("N�mero de cartas na m�o do oponente: " + numCartasOponente);
		System.out.println("\nCarta na mesa: " + cartaMesa);
		switch(corAtiva) {
			case 0:
				System.out.println("Cor ativa na mesa: Azul");
				break;
				 
			case 1:
				System.out.println("Cor ativa na mesa: Amarelo");
				break;
				
			case 2:
				System.out.println("Cor ativa na mesa: Verde");
				break;
				
			case 3:
				System.out.println("Cor ativa na mesa: Vermelho");
				break;
		}
		System.out.println("\nSua m�o:");
		System.out.println(mao);
	}
}
