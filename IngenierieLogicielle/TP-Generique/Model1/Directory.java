
import java.util.*;

public class Directory extends AbstractStockage {

    private ArrayList<AbstractStockage> listStockages;

    public Directory(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
        this.listStockages = new ArrayList<>();
        this.basicSize = 4;
    }

    public void ls() {
        for (AbstractStockage stockage : this.listStockages) {
            System.out.println(stockage);
        }
    }

    public int nbElem() {
        int nbElem = 0;
        for (AbstractStockage stockage : this.listStockages) {
            if (stockage instanceof File) {
                nbElem += ((File) stockage).nbElem();
            } else if (stockage instanceof Directory) {
                nbElem += ((Directory) stockage).nbElem();
            }
        }
        return nbElem;
    }

    // Rend la collection des adresses absolues des elements de nom name qu’il
    // contient
    public Collection<String> find(String name) {
        Collection<String> list = new ArrayList<>();
        for (AbstractStockage stockage : this.listStockages) {
            if (stockage instanceof File || stockage instanceof Link) {
                if (stockage.name.equals(name)) {
                    list.add(stockage.absoluteAddress());
                }
            }
        }
        return list;
    }

    public Collection<String> findR(String name) {
        Collection<String> list = new ArrayList<>();
        for (AbstractStockage stockage : this.listStockages) {
            if (stockage instanceof Directory) {
                list.addAll(((Directory) stockage).findR(name));
            } else {
                if (stockage.name.equals(name)) {
                    list.add(stockage.absoluteAddress());
                }
            }
        }
        return list;
    }

}