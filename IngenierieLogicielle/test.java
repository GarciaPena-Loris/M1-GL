public class test {

    public class RAM extends ComposantOrdi {
    }
    
    public abstract class ComposantOrdi {
        public void equiv(ComposantOrdi comp, String c) {
            System.out.println("ComposantOrdi.equiv");
        }
    }

    public class Montage extends ComposantOrdi {
        public void equiv(ComposantOrdi m, String c) {
            System.out.println("Montage.equiv");
        }
    }    

    public static void main(String[] args) {
        RAM m1 = new RAM();
        Montage m2 = new Montage();
        ComposantOrdi m3 = m1;
        ComposantOrdi m4 = m2;
        
        m2.equiv(m4, "test");
        m3.equiv(m4, "test");
        m4.equiv(m4, "test");
        m4.equiv((Montage) m4, "test");
        m4.equiv(m3, "test");

    }
    
}
