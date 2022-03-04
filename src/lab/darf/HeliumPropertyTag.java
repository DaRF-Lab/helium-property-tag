package lab.darf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;

public class HeliumPropertyTag implements Serializable {
    public static final double VERSION = 1.0;

    private boolean isUsable = true;          // Cannot contain data if only for parsing
    private boolean isSingleTag = false;      // Cannot contain subtree data if single tag
    public String tagName;                    // Name of the tag
    public String tagDescription;             // Description of the tag
    public Object singleTagValue;             // Value of the tag if single tag

    private ArrayList<String> tagNames = new ArrayList<>();            // Names of the tags of the subtree data
    private ArrayList<HeliumPropertyTag> tag = new ArrayList<>();      // Subtree data of the tag
    private ArrayList<String> tagDescriptions = new ArrayList<>();     // Descriptions of the tags of the subtree data

    // Constructor for only parsing
    public HeliumPropertyTag() {
        isUsable = false;
    }

    // Constructor for only parsing
    public HeliumPropertyTag(String filePath) throws IOException, ClassNotFoundException {
        HeliumPropertyTag t = parse(filePath);
        this.isSingleTag = t.isSingleTag;
        this.tagName = t.tagName;
        this.tagDescription = t.tagDescription;
        this.singleTagValue = t.singleTagValue;
        this.tagNames = t.tagNames;
        this.tag = t.tag;
        this.tagDescriptions = t.tagDescriptions;
    }

    // Constructor for single tag
    public HeliumPropertyTag(String singleTagName, String singleTagDescription, Object singleTagValue) {
        this.isSingleTag = true;
        this.tagName = singleTagName;
        this.tagDescription = singleTagDescription;
        this.singleTagValue = singleTagValue;
    }

    // Constructor for subtree data
    public HeliumPropertyTag(String singleTagName, String singleTagDescription) {
        this.tagName = singleTagName;
        this.tagDescription = singleTagDescription;
    }

    // Get the name of this tag
    public String getTagName() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return tagName;
    }

    // Get the description of this tag
    public String getTagDescription() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return tagDescription;
    }

    // Get the value of this tag.
    // If this tag is single tag, return the value of this tag.
    // If this tag is subtree data, return all subtree as object. Casting is necessary.
    public Object data() { return object(); }
    public Object object() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        if (isSingleTag) {
            return singleTagValue;
        }else{
            return tag;
        }
    }

    // Check if this tag is single tag
    public boolean isSingleTag() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return isSingleTag;
    }

    // Get the value of this tag based on name.
    public HeliumPropertyTag get(String tagName) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");

        // Linear search for the tag
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tagName)) {
                return tag.get(i);
            }
        }
        return null;
    }

    // Get the value of this tag based on index.
    public HeliumPropertyTag get(int index) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return tag.get(index);
    }

    // Get the index number of this tag based on name.
    public int indexOf(String tagName) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");

        // Linear search for the tag
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tagName)) {
                return i;
            }
        }
        return -1;
    }

    // Get the number of tags in this tag.
    public int getTagCount() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return tagNames.size();
    }

    // Get the names of all tags in this tag.
    public ArrayList<String> getTagNames() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return tagNames;
    }

    // Add subtag to this tag, but will add as a single tag.
    public HeliumPropertyTag add(String tagName, String tagDescription, Object tagValue) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        
        // Add a new single tag
        this.tagNames.add(tagName);
        this.tag.add(new HeliumPropertyTag(tagName, tagDescription, tagValue));
        this.tagDescriptions.add(tagDescription);
        return this;
    }

    // Add subtag to this tag, but will add as a subtree data.
    public HeliumPropertyTag add(HeliumPropertyTag tag) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        this.tagNames.add(tag.getTagName());
        this.tag.add(tag);
        this.tagDescriptions.add(tag.getTagDescription());
        return this;
    }

    // Remove a subtag from this tag based on name.
    public HeliumPropertyTag remove(String tagName) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");

        // Linear search for the tag name
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tagName)) {
                tagNames.remove(i);
                tag.remove(i);
                tagDescriptions.remove(i);
                return this;
            }
        }
        return null;
    }

    // Remove a subtag from this tag based on index.
    public HeliumPropertyTag remove(int index) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        tagNames.remove(index);
        tag.remove(index);
        tagDescriptions.remove(index);
        return this;
    }

    // Remove all subtags from this tag.
    public HeliumPropertyTag removeAll() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        tagNames.clear();
        tag.clear();
        tagDescriptions.clear();
        return this;
    }

    // Change the name of this tag.
    public HeliumPropertyTag setName(String tagName) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        this.tagName = tagName;
        return this;
    }

    // Change the description of this tag.
    public HeliumPropertyTag setDescription(String tagDescription) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        this.tagDescription = tagDescription;
        return this;
    }

    // Change the value of this tag.
    public HeliumPropertyTag setValue(Object tagValue) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        if (isSingleTag) {
            this.singleTagValue = tagValue;
            return this;
        }else{
            throw new IllegalStateException("This tag is not single tag.");
        }
    }

    // Change the value of the subtag at index with the new value.
    public HeliumPropertyTag set(int index, HeliumPropertyTag tag) {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        this.tagNames.set(index, tag.getTagName());
        this.tag.set(index, tag);
        this.tagDescriptions.set(index, tag.getTagDescription());
        return this;
    }

    // Convert to string for human readability.
    // HPT Parser will not recognize the stringified tag.
    @Override
    public String toString() {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        return "HeliumPropertyTag" + VERSION + "\n" + structureDisplay(0);
    }

    // Convert to string for human readability.
    private String structureDisplay(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagNames.size(); i++) {

            // Indentation
            for (int j = 0; j < indent; j++) {
                sb.append("\t");
            }

            // Tag name
            sb
            .append(tagNames.get(i))
            .append(": ");

            // If single tag, display the value.
            if (tag.get(i).isSingleTag) {
                sb
                .append("{" + tag.get(i).singleTagValue + "}" + "   (" + tag.get(i).getTagDescription() + ")")
                .append("\n");

            // If subtags, display the subtags recursively
            }else{
                sb
                .append("{\n")
                .append(tag.get(i).structureDisplay(indent + 1));
                for (int j = 0; j < indent; j++) {
                    sb.append("\t");
                }
                sb.append("}   (" + tag.get(i).getTagDescription() + ")\n");
            }
        }
        return sb.toString();
    }

    // Convert to file to save
    public void save(String filePath) throws IOException {
        if (!isUsable) throw new IllegalStateException("This tag is not usable.");
        save(filePath, this);
    }

    // Convert to file to save
    public void save(String filePath, HeliumPropertyTag htag) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(htag);
        objectOut.close();
        fileOut.close();
    }

    // Load from file
    public HeliumPropertyTag parse(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        HeliumPropertyTag tag = (HeliumPropertyTag) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return tag;
    }
}
