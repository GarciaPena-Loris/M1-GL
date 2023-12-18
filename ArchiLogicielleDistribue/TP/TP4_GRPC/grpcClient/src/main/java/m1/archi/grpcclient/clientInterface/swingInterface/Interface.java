package m1.archi.grpcclient.clientInterface.swingInterface;


import com.google.protobuf.Empty;
import m1.archi.grpcclient.exceptions.InterfaceException;
import m1.archi.proto.models.ComparateurOuterClass;
import m1.archi.proto.services.ComparateurServiceGrpc;

import javax.swing.*;
import java.awt.*;

public class Interface {

    public Interface(ComparateurServiceGrpc.ComparateurServiceBlockingStub proxyComparateur) throws InterfaceException {

        // Récupération le comparateur
        ComparateurOuterClass.Comparateur comparateurGrpc = proxyComparateur.getFirstComparateur(Empty.newBuilder().build());

        if (comparateurGrpc == null)
            throw new InterfaceException("Problème lors de la récupération du comparateur");

        // Création de l'interface graphique
        createAndShowGUI(proxyComparateur, comparateurGrpc);
    }

    private void createAndShowGUI(ComparateurServiceGrpc.ComparateurServiceBlockingStub proxyComparateur, ComparateurOuterClass.Comparateur comparateurGrpc) {
        SwingUtilities.invokeLater(() -> {
            String nomComparateur = comparateurGrpc.getNom();
            JFrame frame = new JFrame("Bienvenue sur le comparateur d'agences " + nomComparateur + " :");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CustomContentPane customContentPane = new CustomContentPane(frame);
            frame.setContentPane(customContentPane);

            // Création de la barre de recherche
            JPanel searchPanel = new SearchPanel(customContentPane, proxyComparateur);
            customContentPane.add(searchPanel, BorderLayout.NORTH);

            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}
