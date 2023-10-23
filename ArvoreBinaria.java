package arvores;

import java.util.function.Consumer;

public class ArvoreBinaria extends grafo
	{public Nodo raiz=null;

	//implementação do método inserir (forma natural: recursão)
	public void inserir(int chave) { raiz = inserirDado(raiz,chave); }
	private Nodo inserirDado(Nodo nodo_atual, int chave) 
		{if (nodo_atual == null) {nodo_atual = new Nodo(chave);}
		 else {if (chave<nodo_atual.chave) 		
				  nodo_atual.esq=inserirDado(nodo_atual.esq, chave);
			   else if(chave > nodo_atual.chave) 
			  		{nodo_atual.dir = inserirDado(nodo_atual.dir, chave);}}
		 return nodo_atual;}
	
	public void executarNodos(Consumer<Nodo> funcao) {executarNodosRec(raiz, funcao);}
	private void executarNodosRec(Nodo nodo_atual, Consumer<Nodo> funcao) 
		{if (nodo_atual != null) 
			{executarNodosRec(nodo_atual.esq, funcao);
			 funcao.accept(nodo_atual);
			 executarNodosRec(nodo_atual.dir, funcao);}
	}

	 //implementação do método pra imprimir linearmente do menor pro maior
	public void exibirLinear() {executarNodos(nodo -> System.out.print(nodo.chave+", "));}

	//implementação do método pra imprimir em forma de diretórios
	public void exibirDir() { exibirDirRec(raiz,"");}
	private void exibirDirRec(Nodo nodo_atual,String empilhamento_str) 
		{if (nodo_atual!=null)
			{System.out.println(empilhamento_str+nodo_atual.chave);
			 empilhamento_str+="_";
			 exibirDirRec(nodo_atual.esq,empilhamento_str);
			 exibirDirRec(nodo_atual.dir,empilhamento_str);}
		 }
	public void exibirMaior() {System.out.println(exibirMaiorMenorRec(raiz,true).chave);}
	public void exibirMenor() {System.out.println(exibirMaiorMenorRec(raiz,false).chave);}
	public Nodo exibirMaiorMenorRec(Nodo nodo_atual,boolean ladoDir) 
		{Nodo nodo_prox=ladoDir?nodo_atual.dir:nodo_atual.esq;
		return (nodo_prox==null)?nodo_atual:exibirMaiorMenorRec(nodo_prox,ladoDir);}
		
	public void exibirFolhas() 
		{String[] folha_chave={""};
		 executarNodos(nodo -> {if (nodo.esq==null && nodo.dir==null) 
									folha_chave[0]=folha_chave[0]+nodo.chave+", ";});
		 System.out.print(folha_chave[0]);}

	public void exibirAncestrais(int chave) {exibirAncestraisRec(raiz,chave);}
	private void exibirAncestraisRec(Nodo nodo_atual, int chave)
		{if (nodo_atual.chave!=chave)
			{System.out.print(nodo_atual.chave+", ");
				exibirAncestraisRec(nodo_atual.chave<chave?nodo_atual.dir:nodo_atual.esq,chave);
			}
		}

	public void exibirDescendentes(int chave) {exibirDescendentesRec(raiz,chave);}
	private void exibirDescendentesRec(Nodo nodo_atual, int chave)
		{if (nodo_atual.chave!=chave)
			{exibirDescendentesRec(nodo_atual.chave<chave?nodo_atual.dir:nodo_atual.esq,chave);}
			else {executarNodosRec(nodo_atual.esq,nodo -> System.out.print(nodo.chave+", ")); 
					executarNodosRec(nodo_atual.dir,nodo -> System.out.print(nodo.chave+", "));}	
		}

	public void exibirSubarvoreDir(int chave) {exibirSubarvoreRec(raiz,chave,true);}
	public void exibirSubarvoreEsq(int chave) {exibirSubarvoreRec(raiz,chave,false);}
	private void exibirSubarvoreRec(Nodo nodo_atual, int chave, boolean ladoDir)
		{if (nodo_atual.chave!=chave)
			{exibirSubarvoreRec(nodo_atual.chave<chave?nodo_atual.dir:nodo_atual.esq, chave, ladoDir);}
			else {executarNodosRec(ladoDir?nodo_atual.dir:nodo_atual.esq,nodo -> System.out.print(nodo.chave+", "));}	
		}

	public void exibirPares() {exibirParesRec(raiz);}
	private void exibirParesRec(Nodo nodo_atual)
		{executarNodos(nodo -> {if(nodo.chave%2==0) System.out.print(nodo.chave+", ");});}

	public void exibirNvl(int chave) {exibirNvlRec(raiz,chave,1);}
	private void exibirNvlRec(Nodo nodo_atual,int chave, int contador)
		{if (nodo_atual==null) {System.out.println("Não encontrado");}
		 else {if (nodo_atual.chave==chave) System.out.println("O nodo "+chave+" está no nível "+contador);
			   else exibirNvlRec((nodo_atual.chave<chave)?nodo_atual.dir:nodo_atual.esq,chave,contador+1);}
			}	  

	public void busca(int chave) {buscaRec(raiz,chave);}
	private void buscaRec(Nodo nodo_atual,int chave)
		{if (nodo_atual==null) {System.out.println("Não encontrado");}
		 else {if (nodo_atual.chave==chave) System.out.println("Encontrado");
			   else buscaRec((nodo_atual.chave<chave)?nodo_atual.dir:nodo_atual.esq,chave);}
			}	  
	
	
	private int altura = 0;			 
	public void exibirAltura() 
		{calcularAlturaRec(raiz,0); 
			System.out.print("A altura da árvore é de "+altura);}
	private void calcularAlturaRec(Nodo nodo_atual, int contador)
		{if (nodo_atual!=null) 
			{contador=contador+1;
				if (contador>altura) altura=contador;
				calcularAlturaRec(nodo_atual.dir,contador);
				calcularAlturaRec(nodo_atual.esq,contador);
				}	
			}			 
	
	public void exibirTamanho() 
		{int[] contador = {0}; //Vetores são modificados como referência e não como valor.
			executarNodosRec(raiz,nodo -> contador[0]++);
			System.out.print("O tamanho da árvore é de "+contador[0]+" nodos.");}

	public void inserirNaoRec(int chave)
		{Nodo nodo_prox;
			if (raiz==null) {raiz=new Nodo(chave);}
			for(Nodo nodo_atual=raiz; nodo_atual!=null && nodo_atual.chave!=chave; nodo_atual=nodo_prox)
			{if (nodo_atual.chave<chave)
					{nodo_prox=nodo_atual.dir;
					if (nodo_prox==null) nodo_atual.dir=new Nodo(chave);}
				else{nodo_prox=nodo_atual.esq;
					if (nodo_prox==null) nodo_atual.esq=new Nodo(chave);}
				}
			}

				
}
