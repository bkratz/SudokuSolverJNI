## Prerequisites
- Java 17 or higher
- You need to have a Shared Library named 'SudokuSolverCPP' in a 'lib'-folder (see Repository <?????> to build this Shared Library)

## Generate Header-File for native methods

```shell
javac -h . src/main/java/de/birgitkratz/sudokusolver/jni/SudokuSolverJNI.java
```

## Start Program
You need to set the path to the 'lib'-folder when starting the program:

```shell
java -Djava.library.path=<path-to-lib-folder> -classpath <path-to-classes-folder> de.birgitkratz.sudokusolver.jni.SudokuSolverJNI
```

