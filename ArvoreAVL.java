package arvores;
import static java.lang.Math.sqrt;

public class ArvoreAVL {
    public class Nodo
        {public int dado, altd, alte;
         public int qtd;
         public Nodo dir,esq;

         public Nodo(int dado) 
            {this.dado = dado;
             qtd=1;  
             dir = esq = null;
             altd = alte = 0;}
        }

    public Nodo raiz;
    public ArvoreAVL() {raiz = null;}

    public void inserir(int dado) {raiz = inserirDado(raiz,dado);}

    private Nodo inserirDado(Nodo raiz, int dado) 
        {if (raiz == null) 
            {raiz = new Nodo(dado);
             return raiz;}
         if (dado < raiz.dado) 
            {raiz.esq = inserirDado(raiz.esq,dado);
             if (raiz.esq.altd > raiz.esq.alte) 
                  {raiz.alte = raiz.esq.altd + 1;} 
             else {raiz.alte = raiz.esq.alte + 1;}} 
         else if (dado > raiz.dado) 
            {raiz.dir = inserirDado(raiz.dir,dado);
             if (raiz.dir.altd > raiz.dir.alte) 
                  {raiz.altd = raiz.dir.altd + 1;} 
             else {raiz.altd = raiz.dir.alte + 1;}}
         else if (dado == raiz.dado)
             {raiz.qtd++;}
         raiz = balanceamento(raiz);    
         return raiz;}

    private Nodo balanceamento (Nodo raiz) 
        {int fb = raiz.altd - raiz.alte;
         int fbSubArv; //System.out.println(raiz.dado+"="+fb);
         if (fb == 2) 
            {fbSubArv = raiz.dir.altd - raiz.dir.alte;
             if (fbSubArv >=0) 
                  {raiz = rotacao_esquerda(raiz);} 
             else {raiz.dir = rotacao_direita(raiz.dir);
                   raiz = rotacao_esquerda(raiz);}
             } 
         else if (fb == -2) 
            {fbSubArv = raiz.esq.altd - raiz.esq.alte;
             if (fbSubArv <= 0) 
                  {raiz = rotacao_direita(raiz);} 
             else {raiz.esq = rotacao_esquerda(raiz.esq);
                   raiz = rotacao_direita(raiz);}
             }  
         return raiz;}

    private Nodo rotacao_esquerda(Nodo raiz) 
        {Nodo aux1, aux2;
         aux1 = raiz.dir;
         aux2 = aux1.esq; // aqui
         raiz.dir = aux2;
         aux1.esq = raiz;
         if (raiz.dir == null) {raiz.altd = 0;} 
         else if (raiz.dir.alte > raiz.dir.altd) 
              {raiz.altd = raiz.dir.alte + 1;} 
         else {raiz.altd = raiz.dir.altd + 1;}
         if (aux1.esq.alte > aux1.esq.altd) 
              {aux1.alte = aux1.esq.alte + 1;} 
         else {aux1.alte = aux1.esq.altd + 1;}
         return aux1;}

    private Nodo rotacao_direita(Nodo raiz) 
        {Nodo aux1, aux2;
         aux1 = raiz.esq;
         aux2 = aux1.dir;
         raiz.esq = aux2;
         aux1.dir = raiz;
         if (raiz.esq == null) {raiz.alte = 0;} 
         else if (raiz.esq.alte > raiz.esq.altd) 
              {raiz.alte = raiz.esq.alte + 1;} 
         else {raiz.alte = raiz.esq.altd + 1;}
         if (aux1.dir.alte > aux1.dir.altd) 
              {aux1.altd = aux1.dir.alte + 1;} 
         else {aux1.altd = aux1.dir.altd + 1;}
         return aux1;}

    public void mostrarEmOrdem() {mostrandoOrdenado(raiz);}
    public void mostrandoOrdenado(Nodo raiz) 
        {if (raiz != null) 
            {mostrandoOrdenado(raiz.esq);
             System.out.print(raiz.dado+"("+raiz.qtd+"), ");
             mostrandoOrdenado(raiz.dir);}}

    public void exibirDir() { exibirDirRec(raiz,"");}
    private void exibirDirRec(Nodo nodo_atual,String empilhamento_str)
        {if (nodo_atual!=null)
            {System.out.println(empilhamento_str+nodo_atual.dado + "("+nodo_atual.alte+","+nodo_atual.altd+")");
             empilhamento_str+="_";
             exibirDirRec(nodo_atual.esq,empilhamento_str);
             exibirDirRec(nodo_atual.dir,empilhamento_str);}
        }             

     private Nodo encontrarSucessor(Nodo nodo_atual) 
          {while (nodo_atual.esq != null) {nodo_atual = nodo_atual.esq;}
          return nodo_atual;}
     public void excluirNodo(int valor) 
          {raiz = balanceamento(excluirNodoRec(raiz, valor,false));}
     private Nodo excluirNodoRec(Nodo nodo_atual, int valor, boolean sempre) 
          {if (nodo_atual == null) {return nodo_atual;}
           if (valor < nodo_atual.dado) 
               {nodo_atual.alte-=1;
                nodo_atual.esq = excluirNodoRec(nodo_atual.esq, valor,sempre);}
           else if (valor > nodo_atual.dado) 
               {nodo_atual.altd-=1;
                nodo_atual.dir = excluirNodoRec(nodo_atual.dir, valor,sempre);}
          else {//Encontramos o nodo a ser apagado!
                if ((!sempre) &&(nodo_atual.qtd>1)) {nodo_atual.qtd--; return(nodo_atual);}
                if (nodo_atual.esq == null) {return nodo_atual.dir;} 
                if (nodo_atual.dir == null) {return nodo_atual.esq;}
                Nodo nodo_sucessor=encontrarSucessor(nodo_atual.dir);
                nodo_atual.dado = nodo_sucessor.dado;
                nodo_atual.qtd = nodo_sucessor.qtd;
                nodo_atual.altd-=1;
                nodo_atual.dir = excluirNodoRec(nodo_atual.dir, nodo_atual.dado,true);}
          //return nodo_atual;
          return balanceamento(nodo_atual);
          }

     private boolean NodoBalanceado(Nodo nodo_atual) {int fb=nodo_atual.altd-nodo_atual.alte; return (fb<2 && fb>-2);}    
     public boolean isAVL() {return isAVLRec(raiz);}
     private boolean isAVLRec(Nodo nodo_atual)
          {if (nodo_atual==null) return true;
           else return (NodoBalanceado(nodo_atual)&&(isAVLRec(nodo_atual.dir))&&(isAVLRec(nodo_atual.esq)));}

     private int qtdPrimos=0;      
     private boolean isPrime(int n) 
          {if (n<=1) {return false;}
           else for(int i=2;i<=sqrt(n);i++)
                    {if (n%i==0) return false;}
           //System.out.println(n);                 
           return true;}
     public int Prime() {qtdPrimos=0; PrimeRec(raiz); return(qtdPrimos);}
     private void PrimeRec(Nodo nodo_atual) 
          {if (nodo_atual!=null) 
               {if (isPrime(nodo_atual.dado)) {qtdPrimos+=1;}
                PrimeRec(nodo_atual.dir);
                PrimeRec(nodo_atual.esq);}}

     public void ExibirNvl(int nvl) {ExibirNvlRec(nvl-1,raiz);}
     private void ExibirNvlRec (int nvl, Nodo nodo_atual)
          {if (nodo_atual!=null)
               {if (nvl>0) 
                    {ExibirNvlRec(nvl-1,nodo_atual.esq); 
                     ExibirNvlRec(nvl-1,nodo_atual.dir);}
                else if (nvl==0) {System.out.print(nodo_atual.dado+", ");}}
           }
     
     public void busca(int chave) {if (buscaRec(raiz,chave)) System.out.println("Encontrado");}
     private boolean buscaRec(Nodo nodoAtual, int chave) 
     {if (nodoAtual == null) 
         {return false;}
      if (chave < nodoAtual.dado) 
         {return buscaRec(nodoAtual.esq,chave);} 
      else if (chave> nodoAtual.dado) 
      	 {return buscaRec(nodoAtual.dir,chave);}
      else {return true;}}

     private int SomaNvlsImpares=0;
     public int SomaNosNvlsImpares() 
          {SomaNvlsImpares=0; SomaNosNvlsImparesRec(1, raiz); return SomaNvlsImpares;}
     private void SomaNosNvlsImparesRec (int nvl,Nodo nodo_atual)
          {if (nodo_atual!=null)
               {if (nvl%2==1) SomaNvlsImpares+=nodo_atual.dado;
                SomaNosNvlsImparesRec(nvl+1,nodo_atual.dir); 
                SomaNosNvlsImparesRec(nvl+1,nodo_atual.esq);}
           }


    }  