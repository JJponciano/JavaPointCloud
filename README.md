# JavaPointCloud
Java Point Cloud is an open project for point cloud processing.

## Instalation 
Jpc needs libraries to work. Being entirely written in java, the easiest way to install it is to use MAVEN. To do so, install MAVEN by following the instructions given [here](https://maven.apache.org/install.html).

Then clone or download the source code, go to the directory where the "pom.xml" file is located and execute the following command in a terminal: 

```
mvn install
```
Or simply add the following dependency to your pom.xml file :
```
<dependency>
  <groupId>info.ponciano.lab</groupId>
  <artifactId>jpc</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
