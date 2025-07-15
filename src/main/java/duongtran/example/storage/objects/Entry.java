package duongtran.example.storage.objects;

import duongtran.example.storage.ObjectStorage;

public class Entry {
    public static final String MODE = "100644";
    private final String name;
    private final String oid;

    public Entry(String name, String oid) {
        this.name = name;
        this.oid = oid;
    }

    public byte[] formatContent() {

        return null;
    }

    public String getName() {
        return name;
    }

    public String getOid() {
        return oid;
    }
}
