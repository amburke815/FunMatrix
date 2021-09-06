import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.X509KeyManager;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~map tests~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void testMapMultiplyIntsBy2() {
        assertEquals(new FunMatrix<Integer>(
                        new ArrayList<>(Arrays.asList(2, 4, 6)),
                        new ArrayList<>(Arrays.asList(8, 10, 12)),
                        new ArrayList<>(Arrays.asList(14, 16, 18))),
                m3x3_ints.map(x -> 2 * x));
    }

    @Test
    public void testMapTransformIntsToStrings() {
        assertEquals(new FunMatrix<String>(
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList("4", "5", "6")),
                        new ArrayList<>(Arrays.asList("7", "8", "9"))),
                m3x3_ints.map(x -> Integer.toString(x)));
    }

    @Test
    public void testMapIntsToSumOfIndices() {
        assertEquals(new FunMatrix<Integer>(
                        new ArrayList<>(Arrays.asList(0, 1, 2)),
                        new ArrayList<>(Arrays.asList(1, 2, 3)),
                        new ArrayList<>(Arrays.asList(2, 3, 4))),
                m3x3_ints.map((i, j) -> (i + j)));
    }

    @Test
    public void testMapEmptyMatrixToArbitraryTypeReturnsNewEmptyMatrix() {
        assertEquals(new FunMatrix<Object>(),
                emptyMatrix.map(x -> x));
        // does not return the same empty matrix (check memory locations to verify)
        assertFalse(emptyMatrix == emptyMatrix.map(x -> x));
    }

    @Test
    public void testMapLOCharsToLOCharsViaIndentityReturnsEqualButPhysicallyDifferentMatrix() {
        IMatrix<List<Character>> copyOfLOCharsMatrix = m2x2_lochars.map(x -> x);

        assertTrue(m2x2_lochars.equals(copyOfLOCharsMatrix));
        assertFalse(m2x2_lochars == copyOfLOCharsMatrix);
    }

    @Test
    public void testMapLOCharsToCharLength() {
        assertEquals(new FunMatrix<Integer>(
                        new ArrayList<>(Arrays.asList(4, 3)),
                        new ArrayList<>(Arrays.asList(1, 8))),
                m2x2_lochars.map(loChar -> loChar.size()));
    }

    @Test
    public void testMapBoolsToBinaryDigits() {
        assertEquals(new FunMatrix<Integer>(
                        new ArrayList<>(Arrays.asList(1)),
                        new ArrayList<>(Arrays.asList(0)),
                        new ArrayList<>(Arrays.asList(0)),
                        new ArrayList<>(Arrays.asList(1))),
                m4x1_bools.map(b -> b ? 1 : 0)); // true == 1, false == 0
    }

    @Test
    public void testMapDoublesToBoolsWhetherDoubleIsGreaterThan3() {
        assertEquals(new FunMatrix<Boolean>(
                        new ArrayList<>(Arrays.asList(true, false)), 1),
                m1x2_doubles.map(dbl -> dbl > 3)
        );
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~foldNW, foldSE tests~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void testFoldNWSumInts() {
        // TODO
    }

    @Test
    public void testFoldSESumInts() {
        // TODO
    }

    @Test
    public void testFoldNWSumIntsEqualToSESumInts() {
        // TODO
    }

    @Test
    public void testFoldNWSubtractInts() {
        // TODO
    }

    @Test
    public void testFoldSESubtractInts() {
        // TODO
    }

    @Test
    public void testFoldNWSubtractIntsNotEqualToFoldSESubtractInts() {
        // TODO
    }

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
