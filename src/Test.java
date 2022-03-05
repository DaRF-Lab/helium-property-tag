import lab.darf.HeliumPropertyTag;

public class Test {
    public static void main(String[] args) throws Exception {

        // Save the data
        generate();

        // Load the data
        HeliumPropertyTag t = load();
        // System.out.println("subsubtag3: " + t.get("tag4.subtag4.subsubtag3").get());

        // t.remove("tag4.subtag4.subsubtag3");
        // System.out.println(t.toString());

        // System.out.println(t.get("tag4.subtag4.subsubtag3").get());

    }

    public static HeliumPropertyTag load() throws Exception {
        return new HeliumPropertyTag("test.hpt");
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
        HeliumPropertyTag subtag3 = new HeliumPropertyTag("subtag3", "", "subtag3 value");
        HeliumPropertyTag subtag4 = new HeliumPropertyTag("subtag4", "");
        HeliumPropertyTag subsubtag1 = new HeliumPropertyTag("subsubtag1", "", "subsubtag1 value");
        HeliumPropertyTag subsubtag2 = new HeliumPropertyTag("subsubtag2", "", "subsubtag2 value");
        HeliumPropertyTag subsubtag3 = new HeliumPropertyTag("subsubtag3", "Test Value", "subsubtag3 value");
        HeliumPropertyTag subsubtag4 = new HeliumPropertyTag("subsubtag4", "");
        HeliumPropertyTag subsubsubtag1 = new HeliumPropertyTag("subsubsubtag1", "", "subsubsubtag1 value");
        HeliumPropertyTag subsubsubtag2 = new HeliumPropertyTag("subsubsubtag2", "", "subsubsubtag2 value");
        HeliumPropertyTag subsubsubtag3 = new HeliumPropertyTag("subsubsubtag3", "", "subsubsubtag3 value");

        // masterTag.set("tag4.subtag4.subsubtag3", null);

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

        System.out.println("Generated Tag: \n");

        System.out.println(masterTag.toString());

        System.out.println("=======================");

        // Save the data
        masterTag.save("test.hpt");
    }
}
