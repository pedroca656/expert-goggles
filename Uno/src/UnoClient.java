import java.io.Console;
import java.rmi.Naming;
import java.util.Scanner;

public class UnoClient {
	public static void main (String[] args) {
		int n;

		/*if	(args.length != 2)  {
			System.out.println("Uso: java NotasClient <maquina> <nome>");
			System.exit(1);
		}*/
		try {
			//JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//"+args[0]+"/Uno");
			JogadorInterface jogador = (JogadorInterface) Naming.lookup ("//localhost/Uno");
			/*n = jogador.registraJogador(args[1]);
			System.out.println ("Nome de Usu�rio: "+args[1]);
			if	(n == -1)
				System.out.println ("Erro: Usu�rio j� cadastrado.\n");
			else if (n == -2)
				System.out.println ("Erro: N�mero m�ximo de jogadores j� alcan�ado.");
			else
				System.out.println("Jogador " + args[1] + " cadastrado!");*/
			while(true) {
				int aux = 0;
				int id = -1;
				boolean temPartida = false;

				Scanner scanner = new Scanner(System.in);
				
				/*System.out.println("Lista de comandos:");
				System.out.println("1 - Registro               2 - Encerrar Partida");
				System.out.println("3 - Exist�ncia de Partida  4 - Verificar Oponente");
				System.out.println("5 - � sua Vez              6 - N�mero de Cartas do Baralho");
				System.out.println("7 - No. de Cartas na M�o   8 - No. de Cartas do Oponente");
				System.out.println("9 - Ver Cartas na M�o      10- Ver Carta Na Mesa");
				System.out.println("11- Ver Cor Ativa na Mesa  12- Comprar Uma Carta / Cartas de Cartas Especiais");
				System.out.println("13- Jogar Carta da M�o     14- Obter Seus Pontos ");
				System.out.println("14- Obter Pontos do Oponente");*/
				
				System.out.println("Digite o nome de Usu�rio");
				String usuario = scanner.nextLine();
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
					//System.out.println("Buscando partida...");
				}
				
				System.out.println("ALGO OCORREU!!");
				
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
					System.out.println("Partida encontrada!\n Voc� come�a jogando...");
					temPartida = true;
				}
				else if(aux == 2) {
					System.out.println("Partida encontrada!\n Advers�rio come�a jogando...");
					temPartida = true;
				}
				else {
					System.out.println("Erro inesperado");
					break;
				}
				
				System.out.println("Voc� est� jogando contra " + jogador.obtemOponente(id));
				
				while(temPartida) {
					aux = jogador.ehMinhaVez(id);
					
					if(aux == -2) continue;
					else if(aux == -1) {
						System.out.println("\nErro inesperado, entrando em contato com o servidor...");
					}
					else if(aux == 1) { //VEZ DO JOGADOR!!
						System.out.println("� sua vez!\n");
						System.out.println("N�mero de cartas no baralho: " + jogador.obtemNumCartasBaralho(id));
						System.out.println("N�mero de cartas na m�o do oponente: " + jogador.obtemNumCartasOponente(id));
						System.out.println("\nCarta na mesa: " + jogador.obtemCartaMesa(id));
						switch(jogador.obtemCorAtiva(id)) {
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
						System.out.println(jogador.mostraMao(id));
						
						boolean jogando = true;
						
						while(jogando) {
							System.out.println("\nDigite a posi��o da carta que deseja jogar ou C para comprar uma carta.");
							char c = scanner.next().charAt(0);
							System.out.println(c);
							//caso o jogador use algum caracter que n�o esteja especificado, repete at� ele inserir algo especificado
							while(true) {
								if(c == 'C') break;
								if(c-'0' >= 1 && c-'0' <= jogador.obtemNumCartas(id)) break;
								System.out.println("Entrada incorreta!");
								System.out.println("\nDigite a posi��o da carta que deseja jogar ou C para comprar uma carta.");
								c = scanner.next().charAt(0);
							}
						
							if(c == 'C') {
								jogador.compraCarta(id);
								System.out.println("\nSua m�o atual:");
								System.out.println(jogador.mostraMao(id));							
								System.out.println("\nDigite D para descartar a nova carta ou P para pular sua vez.");
								c = scanner.next().charAt(0);
								while(true) {
									if(c == 'P') break;
									if(c == 'D') break;
									System.out.println("Entrada incorreta!");
									System.out.println("\nDigite D para descartar a nova carta ou P para pular sua vez.");
									c = scanner.next().charAt(0);
								}
								if(c == 'P') {
									//pulando, o compra carta pula a jogada caso o jogador j� tenha compra
									jogador.compraCarta(id);
									System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
									jogando = false;
								}
								else if(c == 'D') {
									System.out.println("Se a carta for um coringa, digite a cor que deseja:");
									System.out.println("0 - Azul;   1 - Amarelo;   2 - Verde;   3 - Vermelho;");
									System.out.println("Se n�o for, digite 4");
									char cor = scanner.next().charAt(0);
									int ok = jogador.jogaCarta(id, jogador.obtemNumCartas(id)-1, cor);
									if(ok == 1) {
										System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
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
							else if(c-'0' >= 1 && c-'0' <= jogador.obtemNumCartas(id) ) {
								System.out.println("Se a carta for um coringa, digite a cor que deseja:");
								System.out.println("0 - Azul;   1 - Amarelo;   2 - Verde;   3 - Vermelho;");
								System.out.println("Se n�o for, digite 4");
								char cor = scanner.next().charAt(0);
								int ok = jogador.jogaCarta(id, c-'0'-1, cor-'0');
								if(ok == 1) {
									System.out.println("\nJogada efetuada com sucesso, aguarde sua vez!");
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
						//partida acabou, fazer tratamento
						System.out.println("Partida encerrada, TODO!!!");
					}
				}
			
			scanner.close();
			}
		} catch (Exception e) {
			System.out.println ("UnoClient failed.");
			e.printStackTrace();
		}		
	}
}
