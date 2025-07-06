package duongtran.example.storage;

// TODO: write in java way: maybe enum ???
public class Blob {

    private String oid;
    private final byte[] data;

    public Blob(byte[] data) {
        this.data = data;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return "blob";
    }

    @Override
    public String toString() {
        return new String(data);
    }


}
