import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A matrix of values of type <code>X</code>.
 * <ul>
 *     <li>This interface is functional-oriented in that each operation produces a new {@link IMatrix}</li>
 * </ul>
 *
 * @param <X> The type of the entry included in this matrix
 */
public interface IMatrix<X> {

    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~abstract operations using lambda~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Produces a new <code>IMatrix</code> where for every element in the original matrix, it is transformed to a
     * new value based on the application of some unary well-defined function <code>elementMapper</code> with signature
     * <code>elementMapper :: X -> Y</code>
     * <br>
     * Produces a new <code>IMatrix</code> of type <code>Y</code> and of the same size as the original matrix.
     *
     * @param elementMapper a well-defined lamda abstraction with signature <code>elementMapper :: X -> Y</code>, for
     *                      abstract data types <code>X</code> and <code>Y</code>
     * @param <Y>           the output type of the <code>elementMapper</code> lambda abstraction.
     * @return a new <code>IMatrix</code> of type <code>Y</code> of the same size as the original matrix where each
     * entry is the result of <code>elementMapper(x)</code>, where x is of type <code>X</code>.
     */
    <Y> IMatrix<Y> map(Function<X, Y> elementMapper);

    /**
     * Produces a new <code>IMatrix</code> where for every element in the original matrix, it is transformed to a
     * new value based on the application of some well-defined binary function <code>elementMapper</code> with signature
     * <code>rowColMapper :: Integer Integer -> Y</code>. This function produces an entry in the new matrix based on the
     * <code>Integer</code> at which it lies.
     * <br>
     * Produces a new <code>IMatrix</code> of type <code>Y</code> and of the same size as the original matrix.
     *
     * @param rowColMapper a well-defined lamda abstraction with signature <code>rowColMapper :: Integer Integer ->
     *                     Y</code>, for
     *                     abstract data type <code>Y</code>
     * @param <Y>          the output type of the <code>rowCol</code> lambda abstraction.
     * @return a new <code>IMatrix</code> of type <code>Y</code> of the same size as the original matrix where each
     * entry is the result of <code>rowColMapper(i, j)</code>, where i and j are <code>Integer</code>s greater than or
     * equal to 0 but less than the height and width dimensions of the matrix, respectively.
     */
    <Y> IMatrix<Y> map(BiFunction<Integer, Integer, Y> rowColMapper);

    /**
     * Goes through this <code>IMatrix</code>, and outputs a <code>List</code> containing only elements <code>x</code>
     * such that <code>x</code> is <code>true</code> for the given <code>Condition</code> and <code>x</code> is a member
     * of the original matrix.
     *
     * @param condition a <code>Predicate</code> lambda abstraction with signature <code>condition :: X -> Boolean
     *                  </code>
     * @return A <code>List</code> of only elements that held true for the given <code>condition</code>.
     */
    List<X> filter(Predicate<X> condition);


    /**
     * Starting at the top-left of the matrix, (coordinate (0,0) ), <i>folds</i> the matrix into a single, combined
     * element by
     *
     * @param folder
     * @param base
     * @param <Y>
     * @return
     */
    <Y> Y foldNW(BiFunction<X, Y, Y> folder, Y base);

    <Y> Y foldSE(BiFunction<X, Y, Y> folder, Y base);

    <Y, Z> IMatrix<Z> elementWiseCombine(BiFunction<X, Y, Z> combiner, IMatrix<Y> combineWith);

    <Y, Z, α> IMatrix<α> pseudoMultiply(BiFunction<X, Y, Z> interMatrixOperation, BiFunction<Z, Z, α> intraMatrixOperation,
                                        IMatrix<Y> combineWith, α αIdentity)
            throws IllegalArgumentException;

    IMatrix<X> sort(Comparator<X> comparator);

    IMatrix<X> replaceMap(Predicate<X> replaceIf, X replaceWith);

    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~tweaked abstractions for convenience~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
    IMatrix<X> findAndReplace(X toFind, X replaceWith);

    boolean orMap(Predicate<X> condition);

    boolean andMap(Predicate<X> condition);
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~getters~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!


    X getElement(int row, int col);

    int getWidth();

    int getHeight();

    List<X> asList();

    IMatrix<X> copy();

    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"""setters"""~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    IMatrix<X> updateEntry(X newEntry, int row, int col);

    IMatrix<X> updateRow(List<X> newRow, int rowNum);

    IMatrix<X> updateCol(List<X> newCol, int colNum);

    IMatrix<X> fillWith(X uniformEntry);


    IMatrix<X> subMatrix(int firstRowIncl, int lastRowIncl, int firstColIncl, int lastColIncl);

    IMatrix<X> subMatrix(int lastRowIncl, int lastColIncl);

    // IMatrix<X> RREF(Comparator<X> comparator);


    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~overriden from Object~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();


}
