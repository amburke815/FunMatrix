import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


// TODO: make sure that null args are checked everywhere later
public class FunMatrix<X> implements IMatrix<X> {

  //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~fields~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
  protected final int rows;
  protected final int cols;
  protected final List<List<X>> entries;

  //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ctors~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
  FunMatrix() {
    rows = 0;
    cols = 0;
    entries = new ArrayList<>();
  }

  FunMatrix(List<List<X>> elements) {
    Utils.notNull(allRowsSameSize(elements));
    if (elements.size() == 0) {
      entries = Utils.notNull(elements);
      rows = 0;
      cols = 0;
      return;
    }
    rows = Utils.intBetween(0, elements.size(), Integer.MAX_VALUE);
    cols = Utils.intBetween(0, elements.get(0).size(), Integer.MAX_VALUE);// guaranteed to exist
    entries = Utils.notNull(elements);
  }

  FunMatrix(List<X>... elements) {
    Utils.notNull(allRowsSameSize(elements));

    if (elements.length == 0) {
      entries = new ArrayList<>();
      rows = 0;
      cols = 0;
      return;
    }

    rows = Utils.intBetween(0, elements.length, Integer.MAX_VALUE);
    cols = Utils.intBetween(0, elements[0].toArray().length, Integer.MAX_VALUE);// guaranteed to exist
    entries = new ArrayList<>(Arrays.asList(elements));
  }


  FunMatrix(X uniformEntry, int _rows, int _cols)
      throws IllegalArgumentException {
    List<List<X>> _entries = new ArrayList<>();
    Utils.intBetween(0, _cols, Integer.MAX_VALUE);
    Utils.intBetween(0, _rows, Integer.MAX_VALUE);

    for (int i = 0; i < _rows; i++) {
      List<X> thisRow = new ArrayList<>();
      for (int j = 0; j < _cols; j++) {
        thisRow.add(Utils.notNull(uniformEntry));
      }
      _entries.add(thisRow);
    }

    entries = _entries;
    rows = _rows;
    cols = _cols;
  }

  FunMatrix(List<X> oneRow, int numRows)
      throws IllegalArgumentException {
    if (Utils.notNull(oneRow).size() == 0) {
      throw new IllegalArgumentException("Cannot make a matrix with copies of an empty row");
    }
    Utils.intBetween(0, numRows, Integer.MAX_VALUE);

    List<List<X>> _entries = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      _entries.add(oneRow);
    }

    entries = _entries;
    rows = numRows;
    cols = oneRow.size();
  }

  FunMatrix(BiFunction<Integer, Integer, X> rowColDependentFunction, int _rows, int _cols)
      throws IllegalArgumentException {
    Utils.notNull(rowColDependentFunction);
    Utils.intBetween(0, _rows, Integer.MAX_VALUE);
    Utils.intBetween(0, _cols, Integer.MAX_VALUE);
    List<List<X>> _entries = new ArrayList<>();

    for (int i = 0; i < _rows; i++) {
      List<X> thisRow = new ArrayList<>();
      for (int j = 0; j < _cols; j++) {
        thisRow.add(rowColDependentFunction.apply(i, j));
      }
      _entries.add(thisRow);
    }

    entries = _entries;
    rows = _rows;
    cols = _cols;
  }


  //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~public methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
  @Override
  public X getElement(int row, int col)
      throws IllegalArgumentException {
    return entries.get(Utils.intBetween(0, row, rows)).get(Utils.intBetween(0, col, cols));
  }

  @Override
  public <Y> IMatrix<Y> map(Function<X, Y> elementMapper)
      throws IllegalArgumentException {
    Utils.notNull(elementMapper);

    List<List<Y>> mapped = new ArrayList<>();

    for (int i = 0; i < rows; i++) {
      List<Y> mappedRow = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        mappedRow.add(elementMapper.apply(getElement(i, j)));
      }
      mapped.add(mappedRow);
    }

    return new FunMatrix<Y>(mapped);
  }

  @Override
  public <Y> IMatrix<Y> map(BiFunction<Integer, Integer, Y> rowColMapper)
      throws IllegalArgumentException {
    Utils.notNull(rowColMapper);

    List<List<Y>> mapped = new ArrayList<>();

    for (int i = 0; i < rows; i++) {
      List<Y> mappedRow = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        mappedRow.add(rowColMapper.apply(i, j));
      }
      mapped.add(mappedRow);
    }

    return new FunMatrix<Y>(mapped);
  }

  @Override
  public List<X> filter(Predicate<X> condition)
      throws IllegalArgumentException {
    Utils.notNull(condition);

    List<X> filtered = new ArrayList<>();

    for (List<X> aRow : entries) {
      for (X anElement : aRow) {
        if (condition.test(anElement)) {
          filtered.add(anElement);
        }
      }
    }

    return filtered;
  }

  @Override
  public <Y> Y foldNW(BiFunction<X, Y, Y> folder, Y base)
      throws IllegalArgumentException {
    Utils.notNull(folder);
    Y folded = Utils.notNull(base);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        folded = folder.apply(getElement(i, j), folded);
      }
    }

    return folded;
  }

  @Override
  public <Y> Y foldSE(BiFunction<X, Y, Y> folder, Y base)
      throws IllegalArgumentException {
    Utils.notNull(folder);
    Y folded = Utils.notNull(base);

    for (int i = rows - 1; i >= 0; i--) {
      for (int j = cols - 1; j >= 0; j--) {
        folded = folder.apply(getElement(i, j), folded);
      }
    }

    return folded;
  }

  @Override
  public <Y, Z> IMatrix<Z> elementWiseCombine(BiFunction<X, Y, Z> combiner, IMatrix<Y> combineWith)
      throws IllegalArgumentException {
    Utils.notNull(combiner);
    Utils.notNull(combineWith);

    if (rows != combineWith.getHeight() || cols != combineWith.getWidth()) {
      throw new IllegalArgumentException("cannot combine matrices with unequal dimensions");
    }

    List<List<Z>> combinedLst = new ArrayList<>();

    for (int i = 0; i < rows; i++) {
      List<Z> thisRow = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        thisRow.add(combiner.apply(getElement(i, j), combineWith.getElement(i, j)));
      }
      combinedLst.add(thisRow);
    }

    return new FunMatrix<>(combinedLst);
  }

  @Override
  public <Y, Z, α> IMatrix<α> pseudoMultiply(BiFunction<X, Y, Z> interMatrixOperation,
                                             BiFunction<Z, Z, α> intraMatrixOperation, IMatrix<Y> combineWith,
                                             α αIdentity) {

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {

      }
    }

    return null;
  }

  @Override
  public IMatrix<X> sort(Comparator<X> comparator) {
    return null;
  }

  @Override
  public IMatrix<X> replaceMap(Predicate<X> replaceIf, X replaceWith) {
    return map(x -> replaceIf.test(x) ? replaceWith : x);
  }

  @Override
  public List<X> asList() {
    List<X> _asList = new ArrayList<>();

    for (List<X> aRow : entries) {
      _asList.addAll(aRow);
    }

    return _asList;
  }

  @Override
  public IMatrix<X> copy() {
    return map(x -> x);
  }

  @Override
  public IMatrix<X> updateEntry(X newEntry, int row, int col)
      throws IllegalArgumentException {
    List<List<X>> newEntries = new ArrayList<>();
    Collections.copy(newEntries, entries);

    newEntries.get(Utils.intBetween(0, row, rows - 1)).
        set(Utils.intBetween(0, col, cols - 1), Utils.notNull(newEntry));

    return new FunMatrix<>(newEntries);
  }

  @Override
  public IMatrix<X> updateRow(List<X> newRow, int rowNum)
      throws IllegalArgumentException {
    List<List<X>> newEntries = new ArrayList<>();
    Collections.copy(newEntries, entries);

    newEntries.set(Utils.intBetween(0, rowNum, rows - 1), Utils.notNull(newRow));

    return new FunMatrix<>(newEntries);
  }

  @Override
  public IMatrix<X> updateCol(List<X> newCol, int colNum) {
    // is the desired column number valid?
    Utils.intBetween(0, colNum, getWidth() - 1);
    // does the specified new column have the right number of entries?
    Utils.intBetween(getHeight(), newCol.size(), getHeight());

    return map((i, j) -> j == colNum ? newCol.get(i) : getElement(i,j));
  }

  @Override
  public IMatrix<X> fillWith(X uniformEntry)
      throws IllegalArgumentException {
    Utils.notNull(uniformEntry);
    return map(x -> uniformEntry);
  }

  @Override
  public IMatrix<X> findAndReplace(X toFind, X replaceWith)
      throws IllegalArgumentException {
    Utils.notNull(toFind);
    Utils.notNull(replaceWith);

    return replaceMap(x -> x.equals(toFind), replaceWith);
  }

  @Override
  public boolean orMap(Predicate<X> condition) {
    return map(x -> condition.test(x)).foldNW((b1, b2) -> (b1 || b2), false);
  }

  @Override
  public boolean andMap(Predicate<X> condition) {
    return map(x -> condition.test(x)).foldNW((b1, b2) -> (b1 && b2), true);
  }

  @Override
  public IMatrix<X> subMatrix(int firstRowIncl, int lastRowIncl, int firstColIncl, int lastColIncl) {
    Utils.intBetween(0, firstRowIncl, lastRowIncl);
    Utils.intBetween(firstRowIncl, 0, rows);
    Utils.intBetween(0, firstColIncl, lastColIncl);
    Utils.intBetween(firstColIncl, 0, cols);

    List<List<X>> subMatrixLst = new ArrayList<>();

    for (int i = firstRowIncl; i <= lastRowIncl; i++) {
      List<X> thisRow = new ArrayList<>();
      for (int j = firstColIncl; j <= lastColIncl; j++) {
        thisRow.add(getElement(i, j));
      }
      subMatrixLst.add(thisRow);
    }

    return new FunMatrix<>(subMatrixLst);
  }

  @Override
  public IMatrix<X> subMatrix(int lastRowIncl, int lastColIncl) {
    return subMatrix(0, lastRowIncl, 0, lastColIncl);
  }

  @Override
  public int getWidth() {
    return cols;
  }

  @Override
  public int getHeight() {
    return rows;
  }

  @Override
  public boolean equals(Object o) {
    // fast path
    if (this == o) {
      return true;
    }

    // check instanceof
    if ( !(o instanceof IMatrix) ) {
      return false;
    }

    // safe cast
    IMatrix anotherMatrix = (IMatrix) o;

    // check sizes
    if (this.getWidth() != anotherMatrix.getWidth() || this.getHeight() != anotherMatrix.getHeight()) {
      return false;
    }

    // intensional equality check
    return map( (i,j) -> this.getElement(i,j).equals(anotherMatrix.getElement(i,j)))
        .foldNW( (b1, b2) -> (b1 && b2), true);

  }

  @Override
  public int hashCode() {
    return map(elem -> Objects.hashCode(elem)).foldNW((hash1, hash2) -> (hash1 + hash2), 0);
  }

  @Override
  public String toString() {
    return
        map((i, j) -> {
          String renderedElem = getElement(i,j).toString();
          if (j == 0) {
            return "[" + renderedElem + ", ";
          }
          else if (j == getWidth() - 1) {
            return renderedElem + "]\n";
          }
          else {
            return renderedElem + ", ";
          }
        }).foldSE((str1, str2) -> (str1 + str2), "");
  }



  //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~private methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!
  private final List<List<X>> allRowsSameSize(List<List<X>> toCheck)
      throws IllegalArgumentException {
    if (Utils.notNull(toCheck).size() == 0) {
      return toCheck;
    }

    boolean _allRowsSameSize = true;
    int targSize = toCheck.get(0).size();
    for (List<X> aRow : toCheck) {
      _allRowsSameSize &= aRow.size() == targSize;
    }

    if (!_allRowsSameSize) {
      throw new IllegalArgumentException("All rows must be of the same length when creating this matrix");
    }
    return toCheck;
  }

  private final List<X>[] allRowsSameSize(List<X>[] toCheck) {
    if (Utils.notNull(toCheck).length == 0) {
      return toCheck;
    }

    boolean _allRowsSameSize = true;
    int targSize = toCheck[0].size();
    for (List<X> aRow : toCheck) {
      _allRowsSameSize &= aRow.size() == targSize;
    }

    if (!_allRowsSameSize) {
      throw new IllegalArgumentException("All rows must be of the same length when creating this matrix");
    }
    return toCheck;
  }


}
