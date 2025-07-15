package duongtran.example.storage.objects;


import duongtran.example.storage.ObjectStorage;
import duongtran.example.storage.ObjectType;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class Blob extends ObjectStorage<byte[]> {

    public Blob(byte[] rawData) throws NoSuchAlgorithmException {
        super(rawData);
    }

    @Override
    protected byte[] getData(byte[] data) {
        return data;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.BLOB;
    }
}
