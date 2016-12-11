### build the project
run:
```mvn clean package```

### run the app
run:
```java -jar target/drawing-1.0-SNAPSHOT.jar```

### Notes:

* Requirements is in drawing_program.txt file.
* In the sample I/O in the requirement file, when drawing the rectangle, 
  the given sample is using an inconsistent coordinate. 
  e.g. given R 16 1 20 3 which means (16, 1) and (20, 3). But the real index 
  showing in the diagram is (14, 1) and (18, 3). Other examples are fine so I 
  make the assumption this is a typo.
  