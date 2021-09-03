import java.util.ArrayList;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    IMatrix<Integer> intsMatrix = new FunMatrix<>(new ArrayList<>(
        Arrays.asList(
            new ArrayList<>(
                Arrays.asList(1, 2, 3)
            ),
            new ArrayList<>(
                Arrays.asList(4, 5, 6)
            ),
            new ArrayList<>(
                Arrays.asList(7, 8, 9)
            )
    )));

    System.out.println("toString: \n" + intsMatrix.toString());
    System.out.println("foldr +: \n" + intsMatrix.foldNW((x,y) -> (x + y), 0));
    System.out.println("foldr +: \n" + intsMatrix.foldSE((x,y) -> (x + y), 0));
    System.out.println("map 2x: \n" + intsMatrix.map((x) -> (2 * x)));
    System.out.println("map 2x: \n" + intsMatrix.map((i, j) -> "(" + i + "," + j + ")"));



  }
}
