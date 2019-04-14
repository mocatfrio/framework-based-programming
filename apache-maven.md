# Apache Maven

1. Verify Maven installation by checking the version
    ```bash
    mvn --version
    ```
    The output will be:
    ```bash
    Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-25T01:41:47+07:00)
    Maven home: /Users/mocatfrio/Applications/apache-maven-3.6.0
    Java version: 12, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-12.jdk/Contents/Home
    Default locale: en_US, platform encoding: UTF-8
    OS name: "mac os x", version: "10.14.4", arch: "x86_64", family: "mac"
    ```
2. Create a Maven project through terminal
    ```bash
    mvn -X archetype:generate -DgroupId=com.mocatfrio.webapp -DartifactId=book-management -DarchetypeArtifactId=maven-archetype-quickstart -Dversion=1.0.0-SNAPSHOT
    ```
    Wait until Maven project created successly
    ```bash
    ...
    [INFO] project created from Old (1.x) Archetype in dir: /Users/mocatfrio/book-management
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  03:42 min
    [INFO] Finished at: 2019-04-13T22:11:50+07:00
    [INFO] ------------------------------------------------------------------------
    ```
3. After creating project, let's check the POM (Project Object Model) file structure
    ```java
    <project>
    // basic project info comes here
        <properties>
            // local project based properties can be stored here
        <properties>
        <dependencies>
            // all third party dependencies come here
        </dependencies>
        <build>
            <plugins>
                // build plugin and compiler arguments come here
            </plugins>
        </build>
        <profiles>
            // All profiles like staging, production come here
        </profiles>
    </project>
    ```
4. So, Maven helps manage the third-party libraries in our operating system.  When we need some third-party libraries in our code, all we need is just write the dependencies on **POM.xml** file, then Maven will download all the dependencies we need on its local repository. Every operating system has their own local Maven repository location:
   * **Windows** Maven central repository location: `C:\Users\<username>\.m2\repository\`
   * **Linux** Maven central repository location: `/home/<username>/.m2/repository`
   * **MAC** Maven central repository location: `/Users/<username>/.m2/repository`

    Maven repositories can be found at [here](https://mvnrepository.com/).

5. Try to add some third-party libraries, for example:
    ```java
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.9.1</version>
    </dependency>
    ```
6. Use dependency trees to locate specific dependencies or display dependency conflicts.
   * Move to Maven project (wherever the **pom.xml** file is available)
   * Execute this command
        ```bash
        mvn dependency:tree
        ```
    The output will be:
    ```bash
    [INFO] Scanning for projects...
    [INFO]
    [INFO] ----------------< com.mocatfrio.webapp:book-management >----------------
    [INFO] Building book-management 1.0.0-SNAPSHOT
    [INFO] --------------------------------[ jar ]---------------------------------
    [INFO]
    [INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ book-management ---
    ...
    ```

## Full Code

[Apache Maven Program](/apache-maven)