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
     * value by applying a well-defined binary function <code>folder</code> with signature <code>folder :: X Y -> Y
     * </code>. This function first combines the element at (0,0) with the given <code>base</code> case of the
     * operation (a <code>Y</code>). Next, the element at (0,1) is combined with the previous result, and so on and so
     * forth until all elements of the first row have been <code>fold</code>ed. When the first row is finished being
     * <code>fold</code>ed, the process repeats on the next row, and elements (1,0), (1,1),... are <code>fold</code>ed
     * into the resultant value. The final result of this operation is a <code>Y</code> that represents the fully
     * <code>fold</code>ed value.
     * <br>
     * This method is named <code>fold<b>NW</b></code> to represent that it folds the matrix starting from its
     * <code><b>N</b></code>orth<code><b>W</b></code>est corner.
     *
     * @param folder A binary lambda abstraction with signature <code>folder :: X Y -> Y</code> used for
     *               folding all of the elements in the matrix starting from the northwest corner
     * @param base   The first value to start <code>fold</code>ing with, of type <code>Y</code>.
     * @param <Y>    The output of the <code>folder</code> lambda abstraction and the type resulting from folding the
     *               matrix
     * @return The fully <code>fold</code>ed value resulting from a fold that starts at the southeast corner of the
     * matrix and folds left to right and top to bottom, in that order
     */
    <Y> Y foldNW(BiFunction<X, Y, Y> folder, Y base);

    /**
     * Starting at the bottom-right of the matrix, (coordinate (0,0) ), <i>folds</i> the matrix into a single, combined
     * value by applying a well-defined binary function <code>folder</code> with signature <code>folder :: X Y -> Y
     * </code>. This function first combines the element at (h,w) with the given <code>base</code> case of the
     * operation (a <code>Y</code>), for an arbitrary matrix of height <i>h</i> and width <i>w</i>.
     * Next, the element at (h,w-1) is combined with the previous result, and so on and so
     * forth until all elements of the last row have been <code>fold</code>ed. When the last row is finished being
     * <code>fold</code>ed, the process repeats on the previous row, and elements (h-1,w), (h-1,w),... are
     * <code>fold</code>ed
     * into the resultant value. The final result of this operation is a <code>Y</code> that represents the fully
     * <code>fold</code>ed value.
     * <br>
     * This method is named <code>fold<b>SE</b></code> to represent that it folds the matrix starting from its
     * <code><b>S</b></code>outh<code><b>E</b></code>ast corner.
     *
     * @param folder A binary lambda abstraction with signature <code>folder :: X Y -> Y</code> used for
     *               folding all of the elements in the matrix starting from the southwest corner
     * @param base   The first value to start <code>fold</code>ing with, of type <code>Y</code>.
     * @param <Y>    The output of the <code>folder</code> lambda abstraction and the type resulting from folding the
     *               matrix
     * @return The fully <code>fold</code>ed value resulting from a fold that starts at the southeast corner of the
     * matrix and folds right to left and bottom to top in that order.
     */
    <Y> Y foldSE(BiFunction<X, Y, Y> folder, Y base);

    /**
     * Let <i>M1</i> and <i>M2</i> be matrices of equal width <i>m</i> and height <i>n</i>, with
     * elements <i>m1ij</i> and <i>m2ij</i>, respectively, placed at logical zeroed indices
     * (i,j), for i < m, j < n.
     * <br>
     * Then, <code>elementWiseCombine(combiner, combineWith)</code> produces a new matrix <i>M*</i> such that
     * for all <i>m*ij</i> in <i>M*</i>: <i>m*ij = </i><code>combiner.apply</code><i>(m1ij, m2ij)</i>, where
     * <ul>
     *     <li><i>M* ≡ </i><code>elementWiseCombine(combiner, combineWith)</code></li>
     *     <li><i>M1 ≡ </i><code>this</code></li>
     *     <li><i>M2 ≡ </i><code>combineWith</code></li>
     *</ul>
     *
     * <br>
     * <strong>EXAMPLE:</strong>
     * <br>
     * The simplest example of this is addition on a pair of matrices of numbers, say integers.
     * Then, matrix addition is defined to be <i>M* = </i>
     * <code>elementWiseCombine( (m1ij, m2ij) -> (m1ij + m2ij), m2)</code>, and <code>elementWiseCombine</code> would
     * adapt the signature <code>elementWiseCombine :: IMatrix</code>
     *
     * @param combiner   The binary function to combine the two matrices elements with.
     * @param combineWith The matrix to <code>combineWith</code>
     * @param <Y> the type of the matrix to combine with and also the type of the second argument of
     *          <code>combiner</code>.
     * @param <Z> The type of the resultant matrix's entries, as well as the output type of <code>combiner</code>
     * @return
     */
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
