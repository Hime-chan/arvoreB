package arvores;

public class listaEncadeada extends grafo
    {public Nodo ini=null;
     public Nodo fim=null;
    
     public void importar(ArvoreBinaria arvore)
        {arvore.executarNodos(nodo -> {inserirDado(nodo.chave);});}
     
     public void inserirDado(int chave)
        {Nodo nodo_novo=new Nodo(chave);  
         if (ini==null) {ini=nodo_novo; fim=nodo_novo;}
         else {Nodo i; 
               for (i=ini; (i.dir!=null)&&(i.dir.chave<chave); i=i.dir)
                   {}
               nodo_novo.esq=i;
               if (i.dir!=null) 
                    {i.dir.esq=nodo_novo;
                     nodo_novo.dir=i.dir;}
               else {fim=nodo_novo;}      
               i.dir=nodo_novo;
               }
         }
    
    public void imprimir()
        {for (Nodo i=ini; (i!=null); i=i.dir)
            {System.out.print(i.chave+", ");}}
    }
