package mvc2;
import java.util.*;

/// [COURS] Composants centraux qui font le boulot
/// Informe le controller et la vue lors de changements
// Le modele ne connais pas ces vus directement, il communique avec le broadcast
// Le modele est un observateur, il est notifié par le broadcast

public class Model {
	//Implante MVC incluant le schéma Observateur
    //Un modèle connait indirectement ses vues
	//Il est un observé, voir https://www.oodesign.com/observer-pattern
	
	//quand un modèle change, ses vues sont prévenues
  
	//A ecrire la méthode notify du schéma Observateur
	//également nommée changed dans le schéma MVC

    public void changed(Object how) {
        for (View observer : MV_Association.getViews(this)) {
            observer.update(how);
        }
    }
}
