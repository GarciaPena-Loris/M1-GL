package mvc2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class CompteurController extends Controller implements ActionListener {

	public CompteurController(Model m) {
		super(m);
	}

	public void v1actionPerformed(ActionEvent e) {
		Compteur cm = (Compteur) m;
		String action = e.getActionCommand();
        switch (action) {
            case "incr" -> cm.incr();
            case "decr" -> cm.decr();
            case "raz" -> cm.raz();
        }
	}

	public void actionPerformed(ActionEvent e) {
			Compteur cm = (Compteur) m;
			String action = e.getActionCommand();
			try {
				//Class c = Class.forName("Compteur");
				Class<? extends Compteur> c = cm.getClass();
				Method m = c.getDeclaredMethod(action, null);
				m.invoke(cm, null);
				System.out.println("CompteurController : " + action);
			}
		catch (Exception ex) {
			throw new RuntimeException(
					"Ouille : un compteur ne comprends pas le message " + action);
		}
	}
}
