import lab.darf.HeliumPropertyTag;

public class Test {
    public static void main(String[] args) throws Exception {

        // Save the data
        generate();

        // Load the data
        HeliumPropertyTag t = load();
        String s = (String) (t.get("tag4").get("subtag4").get("subsubtag3").object());

        s = (String) (t.object("tag4.subtag4.subsubtag3"));
        System.out.println(s);

        // Remove subsubtag3
        t.remove("tag4.subtag4.subsubtag3");
        System.out.println(t.toString());
    }

    public static HeliumPropertyTag load() throws Exception {
        System.out.println("Loading test.hpt...");

        // t1 and t2 are the same
        HeliumPropertyTag t1 = new HeliumPropertyTag().parse("test.hpt");
        HeliumPropertyTag t2 = new HeliumPropertyTag("test.hpt");

        // Print
        System.out.println(t1.toString());
        System.out.println(t2.toString());

        // Return
        return t1;
    }

    public static void generate() throws Exception {

        // Generate bunch of data
        HeliumPropertyTag masterTag = new HeliumPropertyTag("Master Tag", "This is the master tag.");
        HeliumPropertyTag tag1 = new HeliumPropertyTag("tag1", "This is just a single tag.", "tag1 value");
        HeliumPropertyTag tag2 = new HeliumPropertyTag("tag2", "This is just a single tag 2", "tag2 value");
        HeliumPropertyTag tag3 = new HeliumPropertyTag("tag3", "This is just a single tag 3", "tag3 value");
        HeliumPropertyTag tag4 = new HeliumPropertyTag("tag4", "This is a subtree tag.");
        HeliumPropertyTag subtag1 = new HeliumPropertyTag("subtag1", "Descriptions will be replaced with _ from now on.", "subtag1 value");
        HeliumPropertyTag subtag2 = new HeliumPropertyTag("subtag2", null, "subtag2 value");
        HeliumPropertyTag subtag3 = new HeliumPropertyTag("subtag3", "_", "subtag3 value");
        HeliumPropertyTag subtag4 = new HeliumPropertyTag("subtag4", "_");
        HeliumPropertyTag subsubtag1 = new HeliumPropertyTag("subsubtag1", "_", "subsubtag1 value");
        HeliumPropertyTag subsubtag2 = new HeliumPropertyTag("subsubtag2", "_", "subsubtag2 value");
        HeliumPropertyTag subsubtag3 = new HeliumPropertyTag("subsubtag3", "_", "subsubtag3 value");
        HeliumPropertyTag subsubtag4 = new HeliumPropertyTag("subsubtag4", "_");
        HeliumPropertyTag subsubsubtag1 = new HeliumPropertyTag("subsubsubtag1", "_", "subsubsubtag1 value");
        HeliumPropertyTag subsubsubtag2 = new HeliumPropertyTag("subsubsubtag2", "_", "subsubsubtag2 value");
        HeliumPropertyTag subsubsubtag3 = new HeliumPropertyTag("subsubsubtag3", "_", "subsubsubtag3 value");

        masterTag.set("tag4.subtag4.subsubtag3", subsubtag3);

        // Build the tree
        masterTag
            .add(tag1)
            .add(tag2)
            .add(tag3)
            .add(tag4
                .add(subtag1)
                .add(subtag2)
                .add(subtag3)
                .add(subtag4
                    .add(subsubtag1)
                    .add(subsubtag2)
                    .add(subsubtag3)
                    .add(subsubtag4
                        .add(subsubsubtag1)
                        .add(subsubsubtag2)
                        .add(subsubsubtag3)
                    )
                )
            );

        System.out.println(masterTag.toString());

        System.out.println("Saving...");
        // Save the data
        masterTag.save("test.hpt");
    }
}
