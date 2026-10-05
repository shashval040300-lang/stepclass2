import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Solutions for the five Week 4 Category C assignment problems. */
public class ArrayWeek4Assignment {

    /** Builds products on each side of every index without division. */
    public static int[] productExceptSelf(int[] nums) {
        int[] answer = new int[nums.length];
        int leftProduct = 1;
        for (int i = 0; i < nums.length; i++) {
            answer[i] = leftProduct;
            leftProduct *= nums[i];
        }

        int rightProduct = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            answer[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        return answer;
    }

    /** Kadane's algorithm; the chosen subarray must contain at least one value. */
    public static int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int currentSum = nums[0];
        int bestSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            bestSum = Math.max(bestSum, currentSum);
        }
        return bestSum;
    }

    /** Returns each distinct zero-sum triplet once; leaves the input unchanged. */
    public static int[][] threeSum(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<int[]> triplets = new ArrayList<>();

        for (int i = 0; i < sorted.length - 2; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = sorted.length - 1;
            while (left < right) {
                long sum = (long) sorted[i] + sorted[left] + sorted[right];
                if (sum == 0) {
                    triplets.add(new int[] {sorted[i], sorted[left], sorted[right]});
                    int leftValue = sorted[left];
                    int rightValue = sorted[right];
                    while (left < right && sorted[left] == leftValue) {
                        left++;
                    }
                    while (left < right && sorted[right] == rightValue) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return triplets.toArray(new int[triplets.size()][]);
    }

    /** Counts contiguous subarrays with sum k using prefix-sum frequencies. */
    public static int subarraySum(int[] nums, int k) {
        Map<Long, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1);
        long prefixSum = 0;
        int count = 0;

        for (int value : nums) {
            prefixSum += value;
            count += prefixCounts.getOrDefault(prefixSum - k, 0);
            prefixCounts.put(prefixSum, prefixCounts.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }

    /** Finds the minimum in a rotated ascending array of distinct values. */
    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > nums[right]) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println("A1 Product of Array Except Self");
        System.out.println(Arrays.toString(productExceptSelf(new int[] {1, 2, 3, 4})));
        System.out.println(Arrays.toString(productExceptSelf(new int[] {-1, 1, 0, -3, 3})));

        System.out.println("A2 Maximum Subarray");
        System.out.println(maxSubArray(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[] {-3, -1, -2}));

        System.out.println("A3 3Sum");
        System.out.println(Arrays.deepToString(threeSum(new int[] {-1, 0, 1, 2, -1, -4})));
        System.out.println(Arrays.deepToString(threeSum(new int[] {0, 0, 0})));

        System.out.println("A4 Subarray Sum Equals K");
        System.out.println(subarraySum(new int[] {1, 1, 1}, 2));
        System.out.println(subarraySum(new int[] {1, -1, 0}, 0));

        System.out.println("A5 Find Minimum in Rotated Sorted Array");
        System.out.println(findMin(new int[] {3, 4, 5, 1, 2}));
        System.out.println(findMin(new int[] {4, 5, 6, 7, 0, 1, 2}));
        System.out.println(findMin(new int[] {11, 13, 15, 17}));

        boolean edgeCasesPass = Arrays.equals(productExceptSelf(new int[] {0, 4}),
                        new int[] {4, 0})
                && maxSubArray(new int[] {-8}) == -8
                && Arrays.deepEquals(threeSum(new int[] {-2, 0, 0, 2, 2}),
                        new int[][] {{-2, 0, 2}})
                && subarraySum(new int[] {1, -1, 1}, 1) == 3
                && findMin(new int[] {2, 1}) == 1;
        if (!edgeCasesPass) {
            throw new AssertionError("An assignment edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }
}
