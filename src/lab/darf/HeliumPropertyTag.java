package lab.darf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;

public class HeliumPropertyTag implements Serializable {

    public class NoSuchTagException extends RuntimeException {
        public NoSuchTagException(String message) {
            super("No such tag: " + message);
        }
    }

    public static final double VERSION = 2.0;
    private static final HeliumPropertyTag EMPTY = new HeliumPropertyTag("960984D06CD0C1723B0B539C87684708AECEB5741713B8B48D2F0202B53AA958CFF8ADFE1C781E9D4AF114A012B19E03B737133CC1A70179BAEF34ADF4360CF1", "960984D06CD0C1723B0B539C87684708AECEB5741713B8B48D2F0202B53AA958CFF8ADFE1C781E9D4AF114A012B19E03B737133CC1A70179BAEF34ADF4360CF1", null);

    private boolean isSingleTag = false;      // Cannot contain subtree data if single tag
    public String tagName;                    // Name of the tag
    public String tagDescription;             // Description of the tag
    public Object singleTagValue;             // Value of the tag if single tag

    private ArrayList<String> tagNames = new ArrayList<>();            // Names of the tags of the subtree data
    private ArrayList<HeliumPropertyTag> tag = new ArrayList<>();      // Subtree data of the tag
    private ArrayList<String> tagDescriptions = new ArrayList<>();     // Descriptions of the tags of the subtree data


    // 
    // 
    // 
    //        CONSTRUCTORS
    // 
    // 
    // 



    // Constructor for only parsing
    public HeliumPropertyTag(String filePath) throws IOException, ClassNotFoundException {

        // Load the file
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        // Cast the object to a HeliumPropertyTag
        HeliumPropertyTag t = (HeliumPropertyTag) objectIn.readObject();

        // Set all attributes of this object
        this.isSingleTag = t.isSingleTag;
        this.tagName = t.tagName;
        this.tagDescription = t.tagDescription;
        this.singleTagValue = t.singleTagValue;
        this.tagNames = t.tagNames;
        this.tag = t.tag;
        this.tagDescriptions = t.tagDescriptions;

        // Close the file
        objectIn.close();
        fileIn.close();
    }

    // Constructor for single tag
    public HeliumPropertyTag(String singleTagName, String singleTagDescription, Object singleTagValue) {
        
        // Name should not contain "."
        if (singleTagName.contains(".")) {
            throw new IllegalArgumentException("Tag name should not contain '.'.");
        }
        
        // Set all attributes of this object
        this.isSingleTag = true;
        this.tagName = singleTagName;
        this.tagDescription = singleTagDescription;
        this.singleTagValue = singleTagValue;
    }

    // Constructor for subtree data
    public HeliumPropertyTag(String tagName, String tagDescription) {
        
        // Name should not contain "."
        if (tagName.contains(".")) {
            throw new IllegalArgumentException("Tag name should not contain '.'.");
        }
        
        // Set all attributes of this object
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }






    // 
    // 
    // 
    //        GETTERS
    // 
    // 
    // 

    // Get the name of this tag
    public String getName() {
        return tagName;
    }

    // Get the names of all tags in this tag.
    public ArrayList<String> getSubtagNames() {
        return tagNames;
    }

    // Get the description of this tag
    public String getDescription() {
        return tagDescription;
    }

    // Get the number of tags in this tag.
    public int getTagCount() {
        return tagNames.size();
    }

    // Check if this tag is single tag
    public boolean isSingleTag() {
        return isSingleTag;
    }

    // Get the index number of this tag based on name.
    public int indexOf(String tagName) {
        
        // Linear search for the tag
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tagName)) {
                return i;
            }
        }
        return -1;
    }

    // Get the value of this tag.
    public Object get() {
        try {
            if (isSingleTag) {
                return singleTagValue;    // If this tag is single tag, return the value of this tag.
            }else{
                return tag;               // If this tag is subtree data, return all subtree as object. Casting is necessary.
            }
        }catch(NullPointerException e) {
            throw new NoSuchTagException(tagName);
        }
    }

    // Get the value of this tag based on name.
    public HeliumPropertyTag get(String tagName) {
        try{    
            // If this tag is single tag, throw exception
            if (isSingleTag) {
                throw new IllegalStateException("This tag is single tag.");
            }else{

                Object o = null;

                // If the given tagName contains ., 
                // then consider as address and search it.
                if (tagName.contains(".")) {

                    String[] tagNames = tagName.split("\\.");      // Split the tagName by "."
                    HeliumPropertyTag t = this;                    // Set the current tag as this tag
                    for (int i = 0; i < tagNames.length; i++) {    // Search until the end of the address
                        t = t.get(tagNames[i]);                    // Get the tag with the name of the tagName
                        if (t == null) return null;                // If the tag is not found, return null
                    }
                    o = t.get();                                   // Get the value of the last tag
                    tagName = t.getName();                         // Set the name of the last tag as tagName


                // If the given tagName does not contain ".", 
                // then consider as name and search it from this tag.
                }else{
                    int index = tagNames.indexOf(tagName);         // Get the index of the tagName
                    if (index < 0) return null;                    // If the tag is not found, return null
                    o = tag.get(index);                            // Get the value of the tag
                }
                
                // Cast the object to a HeliumPropertyTag
                if (o instanceof HeliumPropertyTag) {
                    return (HeliumPropertyTag) o;                  // If the object is HeliumPropertyTag, return it
                }else{
                    return new HeliumPropertyTag(tagName, "", o);  // If the object is not HeliumPropertyTag, convert it
                }
            }
        }catch(NullPointerException e) {
            throw new NoSuchTagException(tagName);
        }
    }

    // Get the value of this tag based on index.
    public HeliumPropertyTag get(int index) {
        try {
            return tag.get(index);   // Pull from the ArrayList
        }catch(NullPointerException e) {
            throw new NoSuchTagException(tagName);
        }
    }

    

    


    // 
    // 
    // 
    //        SETTERS
    // 
    // 
    // 


    // Change the name of this tag.
    public HeliumPropertyTag setName(String tagName) {
        
        // Name should not contain "."
        if (tagName.contains(".")) {
            throw new IllegalArgumentException("Tag name should not contain '.'.");
        }

        this.tagName = tagName;
        return this;
    }

    // Change the description of this tag.
    public HeliumPropertyTag setDescription(String tagDescription) {
        this.tagDescription = tagDescription;
        return this;
    }

    // Change the value of this tag.
    public HeliumPropertyTag set(Object tagValue) {

        // If nullify, set the value to null
        if (tagValue instanceof HeliumPropertyTag) {
            if (((HeliumPropertyTag) tagValue).getName() != null && ((HeliumPropertyTag) tagValue).getName().equals(EMPTY.getName())) {
                this.singleTagValue = null;
                this.tagName = null;
                this.tagDescription = null;
                this.tagNames = null;
                this.tagDescriptions = null;
                this.tag = null;
                this.isSingleTag = false;
                return this;
            }
        }

        // If this is a single tag, then replace the value with tagValue.
        if (isSingleTag) {
            this.singleTagValue = tagValue;
            return this;


        // If this has subtree data
        }else{

            // If the tagValue is a HeliumPropertyTag:
            if (tagValue instanceof HeliumPropertyTag) {
                HeliumPropertyTag t = (HeliumPropertyTag)tagValue;   // Cast the object to a HeliumPropertyTag
                int i = indexOf(t.getName());                        // Get the index of the tagName

                if (i == -1) {                                       // If the tag is not found,
                    add(t);                                          // Add the tag
                    return this;
                }else{                                               // If the tag is found,
                    tag.set(i, t);                                   // Set the tag
                    return this;
                }

            // If the tagValue is not a HeliumPropertyTag, throw an error   
            }else{
                throw new IllegalStateException("This tag is not single tag.");
            }
        }
    }

    // Change the value of the subtag with the new value by address.
    public HeliumPropertyTag set(String address, Object object) {
        if (isSingleTag) throw new IllegalStateException("This tag is single tag.");

        // If the address contains ".", then consider it as address and search it.
        String[] tagNames = address.split("\\.");

        // Set depth to 0
        HeliumPropertyTag t = this;
        HeliumPropertyTag previousTag = this;

        HeliumPropertyTag[] tagAccessHistory = new HeliumPropertyTag[tagNames.length];

        // Search until the end of the address
        for (int i = 0; i < tagNames.length; i++) {
            t = t.get(tagNames[i]);

            // If it is reached to the end of the address,
            if (i == tagNames.length - 1) {

                // If the object is did not exist, add it.
                if (t == null) {

                    // Wrap the object to a HeliumPropertyTag
                    if (object instanceof HeliumPropertyTag) {
                        t = (HeliumPropertyTag) object;
                    }else{
                        t = new HeliumPropertyTag(tagNames[i], "", object);
                    }

                    // Add the tag
                    previousTag.add(t);

                }else{
                    // If the object exists, set the value of the tag
                    t.set(object);
                }

            // If it is not reached to the end of the address,
            }else{

                // If the tag is not found, add it.
                if (t == null) {
                    t = new HeliumPropertyTag(tagNames[i], "");
                    previousTag.add(t);
                }
                previousTag = t;
            }

            tagAccessHistory[i] = t;
        }

        // Apply to the previous tag
        clearEmptyObjects();

        for (int i = tagAccessHistory.length; i >= 1; i--) {
            previousTag = tagAccessHistory[i - 1];
            try {
                previousTag.set(t);
            }catch(Exception ignored){}
            
            t = previousTag;
        }

        // clearEmptyObjects();
        return this;
    }






    // 
    // 
    // 
    //        FUNCTIONAL METHODS
    // 
    // 
    // 

    // Clear all empty objects 
    private void clearEmptyObjects() {
        if (isSingleTag) {
            return;
        }else{
            for (int i = 0; i < tag.size(); i++) {
                if (tag.get(i).get() == null) {
                    tag.remove(i);
                    tagNames.remove(i);
                    tagDescriptions.remove(i);
                    i--;
                }else {
                    // If the tag is not empty, then check its subtree (Recursive)
                    tag.get(i).clearEmptyObjects();
                }
            }
        }
    }

    // Add subtag to this tag, but will add as a single tag.
    public HeliumPropertyTag add(String tagName, String tagDescription, Object tagValue) {
        
        // Check if the tag already exists
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tagName)) {
                set(tagName, new HeliumPropertyTag(tagName, tagDescription, tagValue));
                return this;
            }
        }

        // Name should not contain "."
        if (tagName.contains(".")) {
            throw new IllegalArgumentException("Tag name should not contain '.'.");
        }

        // Add a new single tag
        this.tagNames.add(tagName);
        this.tag.add(new HeliumPropertyTag(tagName, tagDescription, tagValue));
        this.tagDescriptions.add(tagDescription);
        return this;
    }

    // Add subtag to this tag, but will add as a subtree data.
    public HeliumPropertyTag add(HeliumPropertyTag tag) {
        
        // Check if the tag already exists
        for (int i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).equals(tag.getName())) {
                set(tag.getName(), tag);
                return this;
            }
        }

        // Name should not contain "."
        if (tag.getName().contains(".")) {
            throw new IllegalArgumentException("Tag name should not contain '.'.");
        }

        this.tagNames.add(tag.getName());
        this.tag.add(tag);
        this.tagDescriptions.add(tag.getDescription());
        return this;
    }


    // Remove a subtag from this tag based on name.
    public HeliumPropertyTag remove(String tagName) {
        

        // If the tagName contains . (dot), remove the subtree
        if (tagName.contains(".")) {
            set(tagName, HeliumPropertyTag.EMPTY);

            return this;
        }

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
        
        // Check if the index is valid
        if (index < 0 || index >= tagNames.size()) {
            throw new IllegalArgumentException("The index is invalid.");
        }

        tagNames.remove(index);
        tag.remove(index);
        tagDescriptions.remove(index);
        return this;
    }

    // Remove all subtags from this tag.
    public HeliumPropertyTag removeAll() {
        tagNames.clear();
        tag.clear();
        tagDescriptions.clear();
        return this;
    }

    // Convert to string for human readability.
    // HPT Parser will not recognize the stringified tag.
    @Override
    public String toString() {
        return "HeliumPropertyTag" + VERSION + structureDisplay(0);
    }

    // Convert to string for human readability.
    private String structureDisplay(int indent) {
        StringBuilder sb = new StringBuilder();
        if (tagNames.size() > 0) {
            sb.append("\n");
        }
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
                if (tag.get(i).getDescription() == null || tag.get(i).getDescription().equals("null") || tag.get(i).getDescription().length() > 0) {
                    sb.append("{" + tag.get(i).get() + "}   (" + tag.get(i).getDescription() + ")\n");
                }else{
                    sb.append("{" + tag.get(i).get() + "}\n");
                }

            // If subtags, display the subtags recursively
            }else{
                sb
                .append("{")
                .append(tag.get(i).structureDisplay(indent + 1));
                for (int j = 0; j < indent; j++) {
                    sb.append("\t");
                }
                sb.append("}");
                
                if (tag.get(i).getDescription() == null || tag.get(i).getDescription().equals("null") || tag.get(i).getDescription().length() > 0) {
                    sb.append("   (" + tag.get(i).getDescription() + ")\n");
                }else{
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    // Convert to file to save
    public void save(String filePath) throws IOException {
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
}
