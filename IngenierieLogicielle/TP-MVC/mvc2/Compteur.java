package mvc2;

public class Compteur extends Model {
	
	protected int valeur;
	
	public Compteur() { this(0); }
	
	public Compteur(int init) { valeur = init; }

	//implantation de Observer ("notify" remplacé par "changed"
	//"j'ai changé" dit le compteur
	
	protected void changeValeur(int i){
	  valeur = valeur + i;
	  this.changed("valeur");}

	//vérifiez que les méthodes sont correctes
	public int getValeur(){return valeur;}

	public void incr() {
		this.changeValeur(1);
		System.out.println("Valeur incr : " + valeur);
	}

	public void decr() {
		this.changeValeur(-1);
		System.out.println("Valeur decr : " + valeur);
	}

	public void raz()  { //à écrire
		this.changeValeur(-valeur);
		System.out.println("Valeur raz : " + valeur);
	}
	
	}
