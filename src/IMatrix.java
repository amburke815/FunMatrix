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

  X getElement(int row, int col);

  <Y> IMatrix<Y> map(Function<X, Y> elementMapper);

  <Y> IMatrix<Y> map(BiFunction<Integer, Integer, Y> rowColMapper);

  List<X> filter(Predicate<X> condition);

  <Y> Y foldNW(BiFunction<X, Y, Y> folder, Y base);

  <Y> Y foldSE(BiFunction<X, Y, Y> folder, Y base);

  <Y, Z> IMatrix<Z> elementWiseCombine(BiFunction<X, Y, Z> combiner, IMatrix<Y> combineWith);

  <Y, Z, α> IMatrix<α> pseudoMultiply(BiFunction<X, Y, Z> interMatrixOperation, BiFunction<Z, Z, α> intraMatrixOperation,
                                      IMatrix<Y> combineWith, α αIdentity)
      throws IllegalArgumentException;

  IMatrix<X> sort(Comparator<X> comparator);

  List<X> asList();

  IMatrix<X> updateEntry(X newEntry, int row, int col);

  IMatrix<X> updateRow(List<X> newRow, int rowNum);

  IMatrix<X> fillWith(X uniformEntry);

  IMatrix<X> findAndReplace(X toFind, X replaceWith);

  IMatrix<X> subMatrix(int firstRowIncl, int lastRowIncl, int firstColIncl, int lastColIncl);

  IMatrix<X> subMatrix(int lastRowIncl, int lastColIncl);

  IMatrix<X> RREF(Comparator<X> comparator);

  int getWidth();

  int getHeight();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();

  @Override
  String toString();


}
