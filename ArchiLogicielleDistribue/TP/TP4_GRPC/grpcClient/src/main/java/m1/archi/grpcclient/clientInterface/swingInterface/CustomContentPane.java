package m1.archi.grpcclient.clientInterface.swingInterface;


import m1.archi.grpcclient.models.hotelModels.Offre;
import m1.archi.proto.services.ComparateurServiceGrpc;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CustomContentPane extends JPanel {
    private final JFrame frame;
    public CustomContentPane(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

    }

    public void rebuild(Map<Long, List<List<Offre>>> mapAgenceOffres, ComparateurServiceGrpc.ComparateurServiceBlockingStub proxyComparateur) {
        Component[] components = getComponents();
        if (components.length > 1) {
            remove(components[1]);
        }
        add(new ResultPanel(this, mapAgenceOffres, proxyComparateur), BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    public void clean() {
        Component[] components = getComponents();
        if (components.length > 1) {
            remove(components[1]);
        }
        frame.revalidate();
        frame.repaint();
    }
}
