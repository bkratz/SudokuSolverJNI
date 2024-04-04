package de.birgitkratz.sudokusolver.jni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SudokuSolverJNI {

    static {
        System.loadLibrary("SudokuSolverCPP");
    }

    public static void main(String[] args) throws IOException {
        final var sudokuSolverJNI = new SudokuSolverJNI();

        int[][] board = readInBoard();
        final var solvedSudoku = sudokuSolverJNI.solveSudoku(board);
        writeOutSolvedBoard(solvedSudoku);
    }

    private native int[] solveSudoku(int[][] board);

    private static int[][] readInBoard() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final var is = classloader.getResourceAsStream("input.txt");

        final var board = new int[27][3];

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.startsWith("-")) {
                    continue;
                }
                line = line.replace("_", "0");
                line = line.replace("| ", "");
                final var split = line.split(" ");
                final var array0 = Arrays.stream(split).skip(0).limit(3).mapToInt(Integer::valueOf).toArray();
                final var array1 = Arrays.stream(split).skip(3).limit(3).mapToInt(Integer::valueOf).toArray();
                final var array2 = Arrays.stream(split).skip(6).limit(3).mapToInt(Integer::valueOf).toArray();

                board[lineCounter] = array0;
                board[lineCounter + 1] = array1;
                board[lineCounter + 2] = array2;

                lineCounter += 3;
            }
        }
        return board;
    }

    private static void writeOutSolvedBoard(int[] solution) {
        IntStream.range(0, 9).forEach(i -> {
            IntStream.range(0, 3).forEach(j -> {
                final var list = Arrays.stream(solution).skip(i * 9L + j * 3L).limit(3)
                        .boxed().toList();
                final var output = list.toString()
                        .replace(", ", " ")
                        .replace("[", "")
                        .replace("]", " | ");
                System.out.print(output);
            });
            if ((i + 1) % 3 == 0) {
                System.out.println("\n-----------------------");
            } else {
                System.out.println();
            }
        });
    }
}
