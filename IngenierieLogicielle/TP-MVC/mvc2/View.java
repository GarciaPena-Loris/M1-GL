package mvc2;

/// [COURS] Demande des données aux modèles et les affiches
/// Modularité
/// Les vues sont des observateurs
public abstract class View extends javax.swing.JPanel{
	protected Controller c;
	protected Model m;

	public View(Model m, Controller c){
	  this.m = m;
	  this.c = c;
	  MV_Association.add(m, this);  }
	
	public abstract void update (Object how);
	
}
