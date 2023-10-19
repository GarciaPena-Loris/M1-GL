public class App {
    public static void main(String[] args) {
        Client cl = new Client("Bob");
        Produit lgv = new Produit("LGV", 20);
        System.out.println(lgv);
        Compte cmt1 = new Compte(cl);
        System.out.println("Compte : " + cmt1.prixLocation(lgv));
        Compte cmt2 = new CompteAvecReduction(cl, 0.1);
        System.out.println("CompteReduction (10%): " + cmt2.prixLocation(lgv));
        Compte cmt3 = new CompteAvecSeuil(cl, 2);
        System.out.println("CompteSeuil (seuil 2): " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : (seuil 2) " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : (seuil 2) " + cmt3.prixLocation(lgv)); // doit afficher 0
        Produit r4 = new ProduitSolde("RockyIV", 10.0, 0.5);
        System.out.println(r4);
        System.out.println("CompteNormal + ProduitSolde (50%) : " + cmt1.prixLocation(r4));
        System.out.println("CompteReduction + ProduitSolde (50%) : " + cmt2.prixLocation(r4));        
        System.out.println("CompteSeuil (seuil 2) + ProduitSolde (50%) : " + cmt3.prixLocation(r4));
        System.out.println("CompteSeuil (seuil 2) + ProduitSolde (50%) : " + cmt3.prixLocation(r4));
        System.out.println("CompteSeuil (seuil 2)  + ProduitSolde (50%) : " + cmt3.prixLocation(r4));

    }
}
