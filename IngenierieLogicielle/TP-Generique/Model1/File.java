public class File extends AbstractStockage {

    private String content;

    public File(String name) {
        this.name = name;
    }

    public void ecrire(String content) {
        this.content = content;
    }

    public int size() {
        return this.basicSize + this.content.length() * 4;
    }

    public void cat() {
        System.out.println(this.content);
    }

    public int nbElem() {
        return content.length();
    }

}