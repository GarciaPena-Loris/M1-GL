package mvc2;

import java.awt.event.ActionListener;

/// [COURS] Envois des messages au Models et donne l'interface entre le model et ses vues associés
/// Modularité
/// Le controller est un observateur, il est notifié par le broadcast
public abstract class Controller implements ActionListener{
	//Implantation de Contrôleur (MVC) dans une vision réduite awt.event.ActionListener
	//Un contrôleur réagit lorsqu'il reçoit un évènement ActionEvent.
	
	//dans cette implantation un controlleur connait le modèle qu'il contrôle.
	//il pourrait également connaître la vue ... à ajouter si souhaité

  Model m;

  Controller() {};

  public Controller(Model m){
    this.m = m;
  }
}