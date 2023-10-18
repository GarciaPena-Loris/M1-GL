public abstract class AbstractStockage {

    protected int basicSize = 0;
    protected Directory parent;
    protected String name;
    protected String absoluteAddress;

    public AbstractStockage() {
    }

    public int size() {
        return this.basicSize;
    }

    public String absoluteAddress() {
        return absoluteAddress;
    }

}