package duongtran.example.storage.objects;

import duongtran.example.storage.ObjectStorage;
import duongtran.example.storage.ObjectType;
import duongtran.example.utils.HexUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tree extends ObjectStorage<List<Entry>> {

    public Tree(List<Entry> entries) throws NoSuchAlgorithmException {
        super(entries);
    }

    public ObjectType getType() {
        return ObjectType.TREE;
    }

    @Override
    protected byte[] getData(List<Entry> data) {
        List<Entry> sortedEntries = new ArrayList<>(data);
        sortedEntries.sort(Comparator.comparing(Entry::getName));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (Entry entry : sortedEntries) {
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
