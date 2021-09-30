package cl.entel.eai.model;

public class XygoAddress {

    private long idXygo;
    private String longitude;
    private String latitude;

    public XygoAddress() { }

    public long getIdXygo() {
        return idXygo;
    }

    public void setIdXygo(long idXygo) {
        this.idXygo = idXygo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
