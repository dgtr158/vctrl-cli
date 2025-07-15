package duongtran.example.storage;

import duongtran.example.utils.HexUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ObjectStorage<T> {
    private static final String HASH_ALGORITHM = "SHA-1";

    private final String oid; // object ID
    private final byte[] content; // formatted content for the object

    public ObjectStorage(T data) throws NoSuchAlgorithmException {
        this.content = formatContent(data);
        this.oid = calculateOid();
    }

    public String getOid() {
        return oid;
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * Calculates the object ID using SHA-1 hash.
     * @return Hexadecimal string of the hash
     * @throws NoSuchAlgorithmException If SHA-1 is not available
     */
    private String calculateOid() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        return HexUtil.bytesToHex(digest.digest(content));
    }

    protected byte[] formatContent(T data) {
        // Get original content of the object
        byte[] originalData = getData(data);

        // Header of the object
        String header = String.format("%s %d\0", getType().toString(), originalData.length);
        byte[] headerData = header.getBytes(StandardCharsets.ISO_8859_1);

        // Create object content
        byte[] content = new byte[headerData.length + originalData.length];
        System.arraycopy(headerData, 0, content, 0, headerData.length);
        System.arraycopy(originalData, 0, content, headerData.length, originalData.length);

        return content;
    }

    protected abstract byte[] getData(T data);

    public abstract ObjectType getType();
}
