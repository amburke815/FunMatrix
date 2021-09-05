import org.junit.Assert;
import org.junit.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunMatrixTests {

  private final IMatrix<Object> emptyMatrix = new FunMatrix<>();

  private final IMatrix<Integer> m3x3_ints = new FunMatrix<>(new ArrayList<>(
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

  private final IMatrix<String> m2x3_strings = new FunMatrix<>(new ArrayList<>(
      Arrays.asList(
          new ArrayList<>(
              Arrays.asList("You're", "gonna", "need")
          ),
          new ArrayList<>(
              Arrays.asList("a", "bigger", "boat")
          )
      )
  ));

  private final IMatrix<Boolean> m4x1_bools = new FunMatrix<>(new ArrayList<>( // FIXME (toString() for column vector)
      Arrays.asList(
          new ArrayList<>(Arrays.asList(true)),
          new ArrayList<>(Arrays.asList(false)),
          new ArrayList<>(Arrays.asList(false)),
          new ArrayList<>(Arrays.asList(true))
      )
  ));

  private final IMatrix<Double> m1x2_doubles = new FunMatrix<>(new ArrayList<>(
      Arrays.asList(
          new ArrayList<>(
              Arrays.asList(3.14, 2.718)
          )
      )
  ));

  private final IMatrix<List<Character>> m2x2_lochars = new FunMatrix<>(
      new ArrayList<>(
          Arrays.asList(
              new ArrayList<>(
                  Arrays.asList(new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd')),
                      new ArrayList<>(Arrays.asList('e', 'f', 'g')))
              ),
              new ArrayList<>(
                  Arrays.asList(new ArrayList<>(Arrays.asList('h')),
                      new ArrayList<>(Arrays.asList('i', 'j', 'k', 'l', 'm', 'n', 'o', 'p')))
              )
          )
      )
  );


  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~toString tests~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  @Test
  public void testEmptyToString() {
    assertEquals("", emptyMatrix.toString());
  }

  @Test
  public void testIntsToString() {
    assertEquals("[1, 2, 3]\n" +
        "[4, 5, 6]\n" +
        "[7, 8, 9]\n", m3x3_ints.toString());
  }

  @Test
  public void testStringsToString() {
    assertEquals("[You're, gonna, need]\n" +
        "[a, bigger, boat]\n", m2x3_strings.toString());
  }

  @Test
  public void testBoolsToString() {
    assertEquals("", m4x1_bools.toString());
  }

  @Test
  public void testDoublesToString() {
    assertEquals("[3.14, 2.718]\n", m1x2_doubles.toString());
  }

  @Test
  public void testListOfCharsToString() {
    assertEquals("[[a, b, c, d], [e, f, g]]\n" +
        "[[h], [i, j, k, l, m, n, o, p]]\n", m2x2_lochars.toString());
  }



}
