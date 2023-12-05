package mvc2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CompteurView3 extends View {
    JSlider slider = new JSlider();

    public CompteurView3(Model m, Controller c) {
        super(m, c);
        slider.setMaximum(100);
        slider.setPreferredSize(new Dimension(200, 50)); // Augmenter la taille du JSlider
        this.add(slider);
        this.update("valeur");

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JSlider source = (JSlider)e.getSource();
                System.out.println(source.getValue());
                ((Compteur) m).raz();
                ((Compteur) m).changeValeur(source.getValue());
            }
        });
    }

    public void update(Object how) {
        int newVal = ((Compteur) m).getValeur();
        slider.setValue(newVal);
    }
}