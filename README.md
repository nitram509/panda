# Panda [![Build Status](https://travis-ci.org/Panda-Programming-Language/Panda.svg?branch=master)](https://travis-ci.org/Panda-Programming-Language/Panda)
Panda is a lightweight and powerful programming language written in Java<br>
Project website: [panda-lang.org](https://panda-lang.org/)

#### Example
![current_test.panda preview](https://panda-lang.org/screenshot/4XEbOCn8.png)

#### Repository structure
```
panda/
+--examples/                 Example scripts written in Panda
+--panda/                    Panda module
   +----/src                 All sources of Panda
   +----pom.xml              The main maven build script for Panda module
+--panda-framework/          Panda Framework module
   +----/src                 All sources of Panda Framework
   +----pom.xml              The main maven build script for Panda Framework module
+--panda-utilities/          Panda Utilities module
   +----/src                 All sources of Panda Utilities
   +----pom.xml              The main maven build script for Panda Utilities module
+--pom.xml                   The main maven build script
```

#### Maven
The latest indev build:

```xml
<dependency>
    <groupId>org.panda-lang</groupId>
    <artifactId>panda</artifactId>
    <version>indev-0.7.0</version>
</dependency>
```

Maven repository: [repo.panda-lang.org](https://repo.panda-lang.org/)

```xml
<repository>
    <id>panda-repo</id>
    <name>Panda Repository</name>
    <url>https://repo.panda-lang.org/</url>
</repository>
```

#### Other
- Manual download the latest version of Panda: [panda-indev-0.7.0.jar](https://repo.panda-lang.org/org/panda-lang/panda/indev-0.7.0/panda-indev-0.7.0.jar)
- Lily the Panda IDE: https://github.com/Panda-Programming-Language/Lily <br>
- Light: https://github.com/dzikoysk/Light
