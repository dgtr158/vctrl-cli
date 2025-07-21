package duongtran.example.storage.objects;

import duongtran.example.storage.ObjectStorage;
import duongtran.example.storage.ObjectType;
import duongtran.example.utils.HexUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

public class Tree extends ObjectStorage {
    private final List<Entry> entries;

    public Tree(List<Entry> entries) {
        this.entries = entries;
        entries.sort(Comparator.comparing(Entry::getName));
    }

    public ObjectType getType() {
        return ObjectType.TREE;
    }

    @Override
    protected byte[] toBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (Entry entry : entries) {
                String entryHeader = String.format("%s %s\0", Entry.MODE, entry.getName());
                byte[] entryData = entryHeader.getBytes(StandardCharsets.ISO_8859_1);
                out.write(entryData);

                byte[] objectID = HexUtil.hexStringToByteArray(entry.getOid());
                out.write(objectID);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }

}
