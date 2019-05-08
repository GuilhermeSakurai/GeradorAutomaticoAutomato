package tcc;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Randomico {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 Scanner sc = new Scanner(System.in);
		 
		//String file;
		int QuantidadeTeste;
		int QuantidadeEstados;
		int QuantidadeTransicao;
		boolean selfloop;
		boolean deterministico;
		String labels;
	
		
		QuantidadeEstados = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Estados "));
		QuantidadeTransicao = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Transicao "));
		selfloop =  Boolean.parseBoolean(JOptionPane.showInputDialog("Selfloop? true/false:"));
		deterministico =  Boolean.parseBoolean(JOptionPane.showInputDialog("Deterministico? true/false:"));
		QuantidadeTeste = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Testes"));
		
		JOptionPane.showMessageDialog(null,"Quantidade Estados :"+ QuantidadeEstados+ "\n" + 
				"Quantidade de Transicao :" + QuantidadeTransicao + "\n" + 
				"selfloop:" + selfloop + "\n" + 
				"deterministico:" + deterministico + "\n" + 
				"QuantidadeTeste:" + QuantidadeTeste
				,"Entrada",JOptionPane.INFORMATION_MESSAGE);
		 		
		//System.out.println(QuantidadeTeste + " " + selfloop + " " + deterministico);
		 File myfile = new File("saida.txt").getAbsoluteFile();
		 FileWriter arq = new FileWriter(myfile);
		 //System.out.println(myfile.getPath());
		 PrintWriter gravarArq = new PrintWriter(arq); 
		 
		 labels = JOptionPane.showInputDialog("Rotulos das Transicoes separados por virgulas");
		 //System.out.println(labels);
		 String lista_rotulos[] = labels.split(",");
		
		 for(int i=0;i<QuantidadeTeste;i++) {
			gravarArq.println(i + ":");
			gerador(QuantidadeEstados,QuantidadeTransicao,selfloop,deterministico,lista_rotulos,gravarArq);
			gravarArq.println("-------------------------");
			gravarArq.println("-------------------------");
		}
		
		
		 JOptionPane.showMessageDialog(null,"Diretório:"+ myfile.getPath()+ "\n" + 
					"Arquivo Gerado com Sucesso!"
					,"Resultado",JOptionPane.INFORMATION_MESSAGE);
		 
		 
		arq.close();
		sc.close();
		
	}
	
		
	public static void gerador(int QuantidadeEstados, int QuantidadeTransicao, boolean selfloop,boolean deterministico, String[] rotulos,PrintWriter arquivo) {

			int n = QuantidadeEstados;
			int [][]A=new int[n][n]; 
			int quantidaderotulos = rotulos.length;
			
			if(deterministico) {
				A = criaMatriz_deterministico(A,QuantidadeEstados,QuantidadeTransicao,quantidaderotulos,selfloop);
				A = transicao_deterministico(A ,QuantidadeEstados,quantidaderotulos);
			}else {
				A = criaMatriz_n_deterministico(A,QuantidadeEstados,QuantidadeTransicao,selfloop);
				A = transicao_n_deterministico(A ,QuantidadeEstados,quantidaderotulos);
			}
			
			/*arquivo.println();
			for(int i = 0; i< n ; i++) {
				for(int j = 0; j< n ; j++) {
					arquivo.print(A[i][j]+" ");
				}
				arquivo.println();
			}
			arquivo.println();*/
			
			setRotulos(A,rotulos,QuantidadeEstados,arquivo);
			
						
		}

	
	public static int[][] criaMatriz_n_deterministico(int [][]A , int QuantidadeEstados,int QuantidadeTransicao,boolean selfloop){
		
		Random r = new Random();
		int n = QuantidadeEstados;
		int max = QuantidadeTransicao;
		int check = 0; 
		while(check < max) {
			for(int i = 0; i< n ; i++) {
				for(int j = 0; j< n ; j++) {
					int aux = r.nextInt(2);
					if(check < max) {
						if (aux == 1) {
							
							if(A[i][j] == 0) {
								A[i][j] = aux;
								check++;
							}
							
							if(i == j && selfloop == false) {
								A[i][j] = 0;
								check--;
							}
							
						}						
					}
					
				}
			}
		}
		
	return A;
		
	}
	
public static int[][] criaMatriz_deterministico(int [][]A , int QuantidadeEstados,int QuantidadeTransicao,int quantidaderotulos,boolean selfloop){
		
		Random r = new Random();
		int n = QuantidadeEstados;
		int max = QuantidadeTransicao;
		int check = 0;
		int det;
		while(check < max) {
			for(int i = 0; i< n ; i++) {
				det = 0;
				for(int j = 0; j< n ; j++) {
					int aux = r.nextInt(2);
					
					if(check < max) {
						if (aux == 1) {
	
							if(A[i][j] == 0) {
								A[i][j] = aux;
								check++;
							}
								
							if(i == j && selfloop == false) {
								A[i][j] = 0;
								check--;
							}
								
						}
							
												
					}
					
				}
			}
		}
		
		for(int i = 0; i< n ; i++) {
			det = 0;
			for(int j = 0; j< n ; j++) {
				if(A[i][j] == 1) {
					det++;
					if(det>quantidaderotulos) {
						A[i][j] = 0;
					}
				}
				//System.out.print(A[i][j] + " ");
			}
			//System.out.println();
		}
		
		
	return A;
		
	}
	
	
	public static int[][] transicao_n_deterministico(int [][]A , int QuantidadeEstados,int QuantidadeRotulos) {
		Random r = new Random();
		int n = QuantidadeEstados;
		
		for(int i = 0; i< n ; i++) {
			for(int j = 0; j< n ; j++) {
				if(A[i][j] == 1) {
					int aux = r.nextInt(QuantidadeRotulos) + 2;
					A[i][j] = aux;
				}
				//System.out.print(A[i][j] + " ");
			}
			//System.out.println();
		}
		
		return A;
	}
	
	public static int[][] transicao_deterministico(int [][]A , int QuantidadeEstados,int QuantidadeRotulos) {
		Random r = new Random();
		int n = QuantidadeEstados;
		int vetor[] = new int[n];
		
		for(int i = 0; i< n ; i++) {
			Arrays.fill(vetor, 0);
			for(int j = 0; j< n ; j++) {
				if(A[i][j] == 1) {
					
					int aux1 = r.nextInt(QuantidadeRotulos) + 2;
					//AQUI!!!
					
					while(contains(vetor,aux1)) {
						aux1 = r.nextInt(QuantidadeRotulos) + 2;
						//System.out.println(aux1);
					}
					
					vetor[j] = aux1;
					A[i][j] = aux1;
				}
				//System.out.print(A[i][j] + " ");
			}
			//System.out.println(Arrays.toString(vetor));
			//System.out.println();
		}
		
		//System.out.println();
		
		return A;
	}
	

	private static boolean contains(int[] vetor, int aux) {
		boolean result = false;

        for(int i : vetor){
            if(i == aux){
                result = true;
                break;
            }
        }

        return result;
    }
	
	private static void setRotulos(int [][]A, String[] rotulos, int QuantidadeEstados,PrintWriter arquivo) {
		
		int n = QuantidadeEstados;
		for(int i = 0; i< n ; i++) {
			for(int j = 0; j< n ; j++) {
				if(A[i][j] != 0) {
					//System.out.println(rotulos[A[i][j]-2]);
					arquivo.println("q"+i+","+rotulos[A[i][j]-2]+","+"q"+j);
				}
			}
		}	
		
	}


}


