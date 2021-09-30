package cl.entel.eai.model;

public class TerminalEnclosure {

    private long id;

    private String xCoordEg;
    private String yCoordEg;

    public TerminalEnclosure() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getXCoordEg() {
        return xCoordEg;
    }

    public void setXCoordEg(String xCoordEg) {
        this.xCoordEg = xCoordEg;
    }

    public String getYCoordEg() {
        return yCoordEg;
    }

    public void setYCoordEg(String yCoordEg) {
        this.yCoordEg = yCoordEg;
    }
}
