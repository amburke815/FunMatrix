public class Utils {

  public static <X> X notNull(X toCheck)
      throws IllegalArgumentException {
    if (toCheck == null) {
      throw new IllegalArgumentException("null parameter passed: " + toCheck.getClass().toString());
    }
    return toCheck;
  }

  public static int intBetween(int lowerBoundIncl, int toCheck, int upperBoundIncl)
      throws IllegalArgumentException {
    if (toCheck < lowerBoundIncl || toCheck > upperBoundIncl) {
      throw new IllegalArgumentException(toCheck + " out of range for bounds [" + lowerBoundIncl + "," +
          upperBoundIncl + "]");
    }
    return toCheck;
  }
}
