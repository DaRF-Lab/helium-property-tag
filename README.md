# Helium Property Tag

Helium Property Tag is a data structure for Java, which can easily be written on and loaded from the disk.



## Usage

*All descriptions may be null.*

Declaring a tag with subtree:

```java
HeliumPropertyTag tag = new HeliumPropertyTag(name, description);

// Ex.
HeliumPropertyTag master = new HeliumPropertyTag("Master Tag", "This is the master tag.");
```



Declaring a single tag:

```java
HeliumPropertyTag tag = new HeliumPropertyTag(name, description, value);

// Ex.
HeliumPropertyTag value = new HeliumPropertyTag("Name of Student", "This property tag contains the name of the student.", "Alexander");
```



Adding subtree data

```java
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
```



Get data from the object

```java
String s = (String) (t.get("tag4").get("subtag4").get("subsubtag3").object());
```



Saving to disk

```java
masterTag.save("test.hpt");
```



Load from disk

```java
// Both ways are fine
HeliumPropertyTag t1 = new HeliumPropertyTag().parse("test.hpt");
HeliumPropertyTag t2 = new HeliumPropertyTag("test.hpt");
```





## Documentation

**Constructors**

```java
HeliumPropertyTag()
  // This will not allow developers from accessing all methods but parse(String filePath) and save(String filePath, HeliumPropertyTag htag).
```

```java
HeliumPropertyTag(String filePath)
  // This will directly read the file from given path and parse it.
```

```java
HeliumPropertyTag(String singleTagName, String singleTagDescription, Object singleTagValue)
  // This will create a single tag.
  // Description may be null.
```

```java
HeliumPropertyTag(String singleTagName, String singleTagDescription)
  // This will create a tag with subtree.
  // Description may be null.
```



**Getters**

```java
String getTagName()
  // Will return the tag name.
```

```java
String getTagDescription()
  // Will get the tag description.
```

```java
Object data()
Object object()
  // Will get the actual data from the tag.
  // If the tag is a single tag, then it will return its value.
  // If the tag has a subtree, then it will return its subtree.
```

```java
Object data(String address)
Object object(String address)
  // Will get the actual data from the tag by the address.
  // If the tag is a single tag, then it will throw an error.
  // The accessing address style is same as JSON. (object.object.object...)
```

```java
boolean isSingleTag()
  // Will get the boolean info about whether the tag is single tag or not.
```

```java
HeliumPropertyTag get(String tagName)
  // Will specifically get the tag with the given name if the tag has a subtree.
  // If not, it will return null.
```

```java
HeliumPropertyTag get(int index)
  // Will return the tag in the index.
```

```java
int indexOf(String tagName)
  // Will return the index of the tag based on the name.
```

```java
int getTagCount()
  // Will return the number of subtree tags.
```

```java
ArrayList<String> getTagNames()
  // Will return the array list of the names of subtree tags
```



**Setters**

```java
HeliumPropertyTag setName(String tagName)
  // Will set a the tag name to the given name
```

```java
HeliumPropertyTag setDescription(String tagDescription)
  // Will set the description with the given description
```

```java
HeliumPropertyTag setValue(Object tagValue)
  // Will set the value of the tag if it is a single tag
```

```java
HeliumPropertyTag set(int index, HeliumPropertyTag tag)
  // Will set the value of the subtree subtag at the given index
```

```java
HeliumPropertyTag set(String address, HeliumPropertyTag tag)
HeliumPropertyTag set(String address, Object object)
  // Will set the value of the subtree subtag at the given address
  // The accessing address style is same as JSON. (object.object.object...)
```





**Modifiers**

```java
HeliumPropertyTag add(String tagName, String tagDescription, Object tagValue)
  // Will add a single tag to the subtree
```

```java
HeliumPropertyTag add(HeliumPropertyTag tag)
  // Will add another property tag to the subtree
```

```java
HeliumPropertyTag remove(String tagName)
  // Will remove a subtree tag with the name
  // tagName could be a accessing address.
  // The accessing address style is same as JSON. (object.object.object...)
```

```java
HeliumPropertyTag remove(int index)
  // Will remove a subtree tag at the index
```

```java
HeliumPropertyTag removeAll()
  // Will remove all subtree tags
```



**Filesystem**

```java
save(String filePath) throws IOException
  // Will save the tag to the filesystem to the given path
```

```java
save(String filePath, HeliumPropertyTag htag) throws IOException
  // Will save the given tag with the filesystem to the given path
```

```java
HeliumPropertyTag parse(String filePath) throws IOException, ClassNotFoundException
  // Will load the saved property file and parse it to be the instance of HeliumPropertyTag.
```

