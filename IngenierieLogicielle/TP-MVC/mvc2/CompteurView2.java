package mvc2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CompteurView2 extends View {
    JProgressBar progressBar = new JProgressBar();

    public CompteurView2(Model m, Controller c) {
        super(m, c);
        progressBar.setMaximum(100);
        this.add(progressBar);
        this.update("valeur");

        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int progressBarVal = (int) Math.round(((double) mouseX / (double) progressBar.getWidth()) * progressBar.getMaximum());
                ((Compteur) m).raz();
                ((Compteur) m).changeValeur(progressBarVal);
            }
        });
    }

    public void update(Object how) {
        int newVal = ((Compteur) m).getValeur();
        progressBar.setValue(newVal);
    }
}