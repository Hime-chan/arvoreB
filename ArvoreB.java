package arvores;
public class ArvoreB {
	public class Nodo
	    {public int chave;
	     public Pagina dir, esq;
         public Nodo(int chave) {this.chave=chave;}}
	
	public class Pagina
		{public Nodo[] nodos;
		 public Pagina pai;
		 
		 public Pagina(Pagina pai, int ordem) 
		 	{this.pai=pai; this.nodos=new Nodo[2*ordem];}
		 
		 public int inserir(int chave)
		 	{int i;
		 	 for (i = 0; (i < ordem) && (this.nodos[i] != null)  && (chave > this.nodos[i].chave); i++){}
		 	 //Aqui i tem o lugar onde terei que colocar o novo nodo.
		 	 if (this.nodos[i] == null || this.nodos[i].chave!=chave)
		 	 	{for (int j = ordem - 1; j > i; j--) {this.nodos[j] = this.nodos[j - 1];}
		 	 	 this.nodos[i]=new Nodo(chave);}
		 	 return i;//Retorno o lugar onde coloquei o novo nodo.
			 }
		 }
	
	public int ordem;
	public Pagina raiz = new Pagina(null, ordem);
    public ArvoreB(int ordem) 
    	{this.ordem = ordem;
         raiz = new Pagina(null, ordem);}	


    public void inserir(int chave) 
    	{if (raiz.nodos[0]==null) {raiz.inserir(chave);}
    	else {inserirRec(raiz,chave);}}
    public void inserirRec(Pagina PagAtual, int chave)
    	{int i;
    	 for (i = 0; (i < ordem) && (PagAtual.nodos[i] != null)  
    			 && (chave > PagAtual.nodos[i].chave); i++){}
    	//"i" indica o lugar onde nosso Nodo deve ficar
    	 Pagina ProxPag=(i==0)?PagAtual.nodos[0].esq:PagAtual.nodos[i-1].dir;
    	//"ProxPag" é a página onde o buscaremos
    	 if (ProxPag==null) 
    	 	{PagAtual.inserir(chave); //Aqui chamamos o método da classe Pagina
    	 	 quebrarVetor(PagAtual);}
    	 else {inserirRec(ProxPag,chave);}
    	 }
    
    
    public void quebrarVetor(Pagina pag)
	{if (sizeNotNull(pag)>=ordem)
		{Nodo nodoSubindo=pag.nodos[ordem/2]; //nodo escolhido pra subir
		 int j=0;
		 if (pag.pai==null) 
		 	{pag.pai=new Pagina(null, ordem);
		 	 pag.pai.nodos[0]= new Nodo(nodoSubindo.chave);
		 	 this.raiz=pag.pai;}
		 else {j = pag.pai.inserir(nodoSubindo.chave);}
		 //pag.pai.nodos[j] é o nodo que subiu

		 Nodo[] esq = new Nodo[2*ordem];
		 for (int i=0;i<ordem/2;i++) {esq[i]=pag.nodos[i];}
		 pag.pai.nodos[j].esq=new Pagina(pag.pai,ordem);
		 pag.pai.nodos[j].esq.nodos=esq;
		 
		 Nodo[] dir = new Nodo[2*ordem];
		 for (int i=(ordem/2)+1;i<ordem;i++) {dir[i-(ordem/2)-1]=pag.nodos[i];}
		 pag.pai.nodos[j].dir=new Pagina(pag.pai,ordem);
		 pag.pai.nodos[j].dir.nodos=dir;
		 
		 for (int i = 0; (i < ordem) && (pag.pai.nodos[i] != null); i++)
		 	{if (pag.pai.nodos[i].esq==pag) {pag.pai.nodos[i].esq=pag.pai.nodos[j].dir;}
		 	 if (pag.pai.nodos[i].dir==pag) {pag.pai.nodos[i].dir=pag.pai.nodos[j].esq;}}
		 quebrarVetor(pag.pai);
		 pag=null;
		}
	 }    
    
    public void ImprimirPagina(Pagina pag) 
    	{for (int i = 0; (i<ordem) && (pag.nodos[i]!=null); i++) 
    	 	 {System.out.print(pag.nodos[i].chave+", ");}
    	 System.out.println();}
    
    public void imprimir()
    	{if (raiz!=null) {System.out.println("ÁRVORE:"); imprimirRec(raiz);} 
    	 else {System.out.println("raiz é nula");}
    	}
    public void imprimirRec(Pagina PagAtual)
    	{ImprimirPagina(PagAtual);
    	 if ((PagAtual.nodos[0]!=null)&&(PagAtual.nodos[0].esq!=null)) 
    	 	{imprimirRec(PagAtual.nodos[0].esq);}
    	 for (int i=0;(PagAtual.nodos[i]!=null);i++)
    	 	{if (PagAtual.nodos[i].dir!=null) {imprimirRec(PagAtual.nodos[i].dir);}}
    	}
    

public void buscar(int chave) 
	{System.out.println((buscarRec(raiz, chave)!=null)?"Encontrado!":"Não encontrado.");}

private Pagina buscarRec(Pagina PagAtual, int chave) 
	{if ((PagAtual == null)||(PagAtual.nodos[0] == null)) {return null;}
	 int i;
	 for (i=0; (i < ordem) && (PagAtual.nodos[i] != null)  
			 && (chave > PagAtual.nodos[i].chave); i++){}
	 if ((PagAtual.nodos[i]!=null)&&PagAtual.nodos[i].chave==chave) {return PagAtual;}
	 return buscarRec((i==0)?PagAtual.nodos[0].esq:PagAtual.nodos[i-1].dir, chave);
	 }

private int sizeNotNull(Pagina pag)
	{int i;
	 for (i=0; pag.nodos[i]!=null; i++) {} 
	 return i;}

private Nodo encontrarSucessor(Pagina pag) 
	{while ((pag.nodos[0] != null) && (pag.nodos[0].esq != null)) {pag = pag.nodos[0].esq;}
     return pag.nodos[0];}

public void remover(int chave) {removerRec(raiz,chave);}
private void removerRec(Pagina PagMaior, int chave)
	{Pagina PagAtual=buscarRec(PagMaior,chave);
	 if (PagAtual!=null) 
		 {//PagAtual é a página que tem o Nodo a ser retirado.
		 int i=0;
		 for (; (PagAtual.nodos[i]!=null); i++)
		 	{if (chave==PagAtual.nodos[i].chave) break;}
		 Nodo NodoAtual=PagAtual.nodos[i];
		 //NodoAtual é o nodo a ser retirado.
		 if (sizeNotNull(PagAtual)>ordem/2) // CASO 1: dá pra tirar normalmente
			{removerCaso1(PagAtual,NodoAtual,i,chave);}
		 else if(NodoAtual.dir != null) //Caso 2: não é folha
			{removerCaso2(PagAtual,NodoAtual,i,chave);}
		 else // Caso 3: É folha
		 	{removerCaso3(PagAtual,NodoAtual,i,chave);} 
	 	}}

private boolean removerCaso1(Pagina PagAtual, Nodo NodoAtual, int i,int chave)
	{System.out.println("Caso 1:"+chave);
	 Pagina filhos=new Pagina(PagAtual,ordem);
	//percorrer NodoAtual esq e dir criando uma lista de filhos
	 int j=0;
	 if (NodoAtual.esq!=null)
	 	{for (j=0; j<ordem && PagAtual.nodos[i].esq.nodos[j]!=null; j++)
	 		{filhos.nodos[j]=PagAtual.nodos[i].esq.nodos[j];}}
	 if (NodoAtual.dir!=null)
	 	{for (int k=0; k<ordem && PagAtual.nodos[i].dir.nodos[k]!=null; k++)
		 	{filhos.nodos[j]=PagAtual.nodos[i].dir.nodos[k];
		 	 j++;}}
	 if (j!=0) 
	 	{if (i!=0) {PagAtual.nodos[i-1].dir=filhos;}
	 	 if (PagAtual.nodos[i+1]!=null) {PagAtual.nodos[i+1].esq=filhos;}}
	//Apagando o "i" e organizando os demais elementos do vetor:
	for (j=i; j<ordem; j++) 
		{PagAtual.nodos[j]=PagAtual.nodos[j+1];}
	quebrarVetor(filhos);
	return true;}

private boolean removerCaso2(Pagina PagAtual, Nodo NodoAtual, int i,int chave)
	{System.out.println("Caso 2: "+chave);
	 Nodo sucessor = encontrarSucessor(NodoAtual.dir);
	 NodoAtual.chave = sucessor.chave;
	 //Removendo o sucessor
	 removerRec(NodoAtual.dir, sucessor.chave);
	 return true;}

private boolean removerCaso3(Pagina PagAtual, Nodo NodoAtual, int i,int chave)
	{System.out.println("Caso 3: "+chave);
	 if (PagAtual.pai != null) 
		{// A página não tem chaves suficientes, mas tem irmãs
		 Pagina irma=null;
		 Integer doadoChave=null;
		 int k; //PagAtual.pai.nodos[k] guarda o nodo que tem como filho a PagAtual
		 for (k=0; (k<ordem) && (PagAtual.pai.nodos[k]!=null);k++)
	 	 	{if (PagAtual.pai.nodos[k].dir==PagAtual)
	 	 		{//A página tem irmã à esquerda que pode doar elementos
	 	 		 irma=PagAtual.pai.nodos[k].esq;
	 	 		 if (sizeNotNull(irma)>ordem/2) 
	 	 		 	{for (int j = ordem; j >= 0; j--) 
		 	 		 	{if (irma.nodos[j] != null) {doadoChave=irma.nodos[j].chave; break;}}
	 	 		 	 //Aqui estamos selecionando o último elemento da esquerda, q será doado
	 	 		 	 }
	 	 		  break;}
	 	 	 else if ((PagAtual.pai.nodos[k].esq==PagAtual))
	 	 	 		 {//tem irmã à direita que pode doar
	 	 		 	  irma=PagAtual.pai.nodos[k].dir;
	 	 		 	  if (sizeNotNull(irma)>ordem/2) 
	 	 		 	  	{doadoChave=irma.nodos[0].chave;} 
	 	 		 	  //O primeiro elemento da direita será doado
	 	 		 	  break;}}
		 	if (doadoChave!=null) 
		 		{System.out.println("Há doadores!");
		 		 removerRec(PagAtual.pai, doadoChave);//removendo o elemento doado da irmã
			 	 int j; //apagando o elemento a ser removido originalmente:
			 	 for (j=i; j<ordem-1; j++) {PagAtual.nodos[j]=PagAtual.nodos[j+1];}
		 		 PagAtual.nodos[j]=null;
			 	 PagAtual.inserir(doadoChave); //inserindo o elemento doado na PagAtual.
			 	 return true;}
		 	else {System.out.println("Não há doadores!");
		 	      //Neste caso, não há valor a ser doado, já que a irmã é muito pequena. 
		 		  //Temos que juntar as duas irmãs e o pai numa só página.
		 		  //Como as duas páginas são folhas, não precisamos ter cuidado com dir e esq
		 		  
		 		  for (int j=1;irma.nodos[j]!=null;j++)
		 		  	{PagAtual.pai.inserir(irma.nodos[j].chave);}
		 		  for (int j=1;PagAtual.nodos[j]!=null;j++)
		 		  	{if (i!=j) {PagAtual.pai.inserir(PagAtual.nodos[j].chave);}}
		 		  
		 		  PagAtual.pai.nodos[k].dir=null;
		 		  PagAtual.pai.nodos[k].esq=null;
		 		  
		 		  //Vamos rodar o quebrarVetor, pro caso dessa página ter ficado muito grande.
		 		  //Agora PagAtual.pai está correta, mas as outras irmãs estão menores.
		 		  quebrarVetor(PagAtual.pai);
		 		  //avô: PagAtual.pai.pai
		 		  if (PagAtual.pai.pai==null) {return true;} // Se avô não existe, acabou
		 		  //Se não, Precisaremos juntar o(s) tio(s) na página do vô 
		 		  //(não são folhas! cuidado com esq e dir)
		 		  for (int j=0;PagAtual.pai.pai.nodos[j]!=null;j++) //percorrendo a página avô
	 		  		{Nodo avo_nodo=PagAtual.pai.pai.nodos[j];
		 			 if (avo_nodo.esq!=null && (avo_nodo.esq!=PagAtual.pai))
	 		  			{int inserido=0;
	 		  			 for (int l=0;avo_nodo.esq.nodos[l]!=null;l++)
	 		  				 {inserido=PagAtual.pai.pai.inserir(avo_nodo.esq.nodos[l].chave);
	 		  				  PagAtual.pai.pai.nodos[inserido].esq=avo_nodo.esq.nodos[l].esq;
	 		  				  PagAtual.pai.pai.nodos[inserido].dir=avo_nodo.esq.nodos[l].dir;
	 		  				 }
	 		  			 //PagAtual.pai.pai.nodos[j].esq=null;
	 		  			PagAtual.pai.pai.nodos[j].esq=PagAtual.pai.pai.nodos[inserido].dir;
	 		  			quebrarVetor(PagAtual.pai.pai);
	 		  			 }
	 		  		 if (PagAtual.pai.pai.nodos[j].dir!=null && 
	 		  				 (PagAtual.pai.pai.nodos[j].dir!=PagAtual.pai))
	 		  		 	{int primeiro_inserido=0;
	 		  			 for (int l=0;avo_nodo.dir.nodos[l]!=null;l++)
	 		  				 {int inserido=PagAtual.pai.pai.inserir(avo_nodo.dir.nodos[l].chave);
	 		  				  if (primeiro_inserido==0) {primeiro_inserido=inserido;}
	 		  				  PagAtual.pai.pai.nodos[inserido].esq=avo_nodo.dir.nodos[l].esq;
	 		  				  PagAtual.pai.pai.nodos[inserido].dir=avo_nodo.dir.nodos[l].dir;
	 		  				 }
	 		  			//PagAtual.pai.pai.nodos[j].dir=null;
	 		  			PagAtual.pai.pai.nodos[j].dir=PagAtual.pai.pai.nodos[primeiro_inserido].esq;
	 		  			quebrarVetor(PagAtual.pai.pai);
	 		  			 }
	 		  		}
		 		  }
	 	 	}return false;}

}
