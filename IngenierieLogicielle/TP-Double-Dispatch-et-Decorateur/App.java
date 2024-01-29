public class App {
    public static void main(String[] args) {
        // Double - dispatch
        Client cl = new Client("Bob");
        Produit lgv = new Produit("La grande vadrouille", 20);
        System.out.println(lgv);
        Compte cmt1 = new Compte(cl);
        System.out.println("CompteNormal + ProduitNormal : " + cmt1.prixLocation(lgv));
        Compte cmt2 = new CompteAvecReduction(cl, 0.1);
        System.out.println("CompteReduction (10%) + ProduitNormal : " + cmt2.prixLocation(lgv));
        Compte cmt3 = new CompteAvecSeuil(cl, 2);
        System.out.println("CompteSeuil Seuil1 + ProduitNormal : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil Seuil2 + ProduitNormal : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil Seuil3 + ProduitNormal : " + cmt3.prixLocation(lgv)); // doit afficher 0
        Produit r4 = new ProduitSolde("RockyIV", 10.0, 0.5);
        System.out.println(r4);
        System.out.println("CompteNormal + ProduitSolde (50%) : " + cmt1.prixLocation(r4));
        System.out.println("CompteReduction + ProduitSolde (50%) : " + cmt2.prixLocation(r4));
        System.out.println("CompteSeuil Seuil1 + ProduitSolde (50%) : " + cmt3.prixLocation(r4));
        System.out.println("CompteSeuil Seuil2 + ProduitSolde (50%) : " + cmt3.prixLocation(r4));
        System.out.println("CompteSeuil Seuil3  + ProduitSolde (50%) : " + cmt3.prixLocation(r4));

        System.out.println("\n###############################################\n");

        // Decorateur
        Produit hp = new Produit("Harry Potter", 10.0);
        System.out.println(hp);

        Client c1 = new Client("Dupont");

        // un compte normal pour le client Dupont
        AbstractCompte cmt = new CompteBasique(c1);

        System.out.println("basique (Harry Potter) : " + cmt.prixLocation(hp));
        // Dupont achete un forfait réduction.
        cmt = new ForfaitReduction(cmt, 0.2);
        System.out.println(cmt);

        System.out.println("réduction (Harry Potter) : " + cmt.prixLocation(hp));

        /// Dupont achete en plus un forfait seuil, le seuil est à 2
        cmt = new ForfaitSeuil(cmt, 2);
        System.out.println(cmt);

        System.out.println("Seuil1 + Reduction (Harry Potter) : " + cmt.prixLocation(hp));
        System.out.println("Seuil2 + Reduction (Harry Potter) : " + cmt.prixLocation(hp));
        System.out.println("Seuil3 + Reduction (Harry Potter) : " + cmt.prixLocation(hp)); // rend O

        // Dupont avec ses 2 forfaits loue un produit soldé
        Produit e4 = new ProduitSolde("Expendable 4", 10.0, 0.5);
        System.out.println(e4);

        System.out.println("Seuil1 + Reduction + Solde (Expendable 4) : " + cmt.prixLocation(e4));
        System.out.println("Seuil2 + Reduction + Solde (Expendable 4) : " + cmt.prixLocation(e4));
        System.out.println("Seuil3 + Reduction + Solde (Expendable 4) : " + cmt.prixLocation(e4));

        // Dupont achete un forfait réduction.
        cmt = new ForfaitReduction(cmt, 0.2);
        System.out.println("réduction (Expendable 4) : " + cmt.prixLocation(e4));
        
        cmt = new ForfaitReduction(cmt, 0.2);
        System.out.println("réduction (Expendable 4) : " + cmt.prixLocation(e4));

        cmt = new ForfaitReduction(cmt, 0.2);
        System.out.println("réduction (Expendable 4) : " + cmt.prixLocation(e4));

        cmt = new ForfaitReduction(cmt, 0.2);
        System.out.println("réduction (Expendable 4) : " + cmt.prixLocation(e4));
        
        
        // affiche l'instance de cmt
        System.out.println(cmt);

    }
}

        