package arvores;

public class teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		        ArvoreB arvore = new ArvoreB(3);

		        arvore.inserir(10);
		        arvore.inserir(30);
		        arvore.inserir(40);
		        arvore.inserir(5);
		        arvore.inserir(20);
		        System.out.println("Inserção 1:");
		        arvore.imprimir();
		        arvore.inserir(11);
		        arvore.inserir(7);
		        arvore.inserir(2);
		        arvore.inserir(1);
		        System.out.println("Inserção 2:");
		        arvore.imprimir();
		        //System.out.println("Remoção:");
		        //arvore.remover(20);
		        
		
		     //   System.out.println("Buscar 5:");
		       // arvore.buscar(5); 
		        
		      //  System.out.println("Buscar 6:");
		        //arvore.buscar(6); 
	}

}
