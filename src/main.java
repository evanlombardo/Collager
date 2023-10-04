import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class main {

  public static void main(String[] args) {

    int[] array = {1, 2, 2, 3, 3, 3, 4, 5, 5};

    int[] result = solution(array, 1);

    for (int val : result) {
      System.out.println(val);
    }
  }

  public static int[] solution(int[] data, int n) {

    if (n < 1 || data.length == 0) {
      return new int[0];
    }

    HashMap<Integer, Integer> numCounts = new HashMap();

    return solutionHelper(data, n, 0, 0, numCounts);

  }

  public static int[] solutionHelper(int[] data, int n, int current, int totalResults, HashMap<Integer, Integer> counts) {

    int currentVal = data[current];

    if (!counts.containsKey(currentVal)) {
      counts.put(currentVal, 1);
      totalResults++;
    } else if (counts.get(currentVal) == n) {
      counts.put(currentVal, n + 1);
      totalResults -= n;
    } else if (counts.get(currentVal) < n) {
      counts.put(currentVal, counts.get(currentVal) + 1);
      totalResults++;
    }

    if (current == data.length - 1) {

      if (counts.get(currentVal) >= n) {

        int[] result = new int[totalResults];

        if (totalResults > 0) {
          result[0] = totalResults - 1; // first element represents next index to write to
        }

        return result;
      }

      int[] result = new int[totalResults];
      result[totalResults - 1] = currentVal;

      if (totalResults > 1) {
        result[0] = totalResults - 2; // first element represents next index to write to
      }

      return result;
    }

    int[] solvedPart = solutionHelper(data, n, current + 1, totalResults, counts);

    int currentCount = counts.get(currentVal);

    if (currentCount > n) {
      return solvedPart;
    }

    int nextIndex = solvedPart[0];

    solvedPart[nextIndex] = currentVal;

    if (nextIndex != 0) {
      solvedPart[0]--;
    }

    return solvedPart;
  }
}
