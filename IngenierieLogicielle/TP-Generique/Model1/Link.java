public class Link extends AbstractStockage {

    private String targetPath;

    public Link(String name) {
        this.name = name;
    }

    public int size() {
        return this.basicSize + this.targetPath.length() * 4;
    }

    public void create(String target) {
        targetPath = target;
    }

    public void cat() {
        System.out.println(targetPath);
    }

}