import java.util.Arrays;

/** Solutions for the five Week 4 Category C practice problems. */
public class ArrayWeek4Practice {

    /** Returns the indices of the unique pair whose values sum to target. */
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }

    /** Returns the largest profit from one buy followed by one later sell. */
    public static int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int lowestPrice = prices[0];
        int bestProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            bestProfit = Math.max(bestProfit, prices[i] - lowestPrice);
            lowestPrice = Math.min(lowestPrice, prices[i]);
        }
        return bestProfit;
    }

    /** Returns true if two different positions contain the same value. */
    public static boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Merges two ascending arrays into a new ascending array. */
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];
        int left = 0;
        int right = 0;
        int write = 0;

        while (left < arr1.length && right < arr2.length) {
            if (arr1[left] <= arr2[right]) {
                merged[write++] = arr1[left++];
            } else {
                merged[write++] = arr2[right++];
            }
        }
        while (left < arr1.length) {
            merged[write++] = arr1[left++];
        }
        while (right < arr2.length) {
            merged[write++] = arr2[right++];
        }
        return merged;
    }

    /** Returns a new array rotated right by k positions. */
    public static int[] rotateArray(int[] nums, int k) {
        int length = nums.length;
        if (length == 0) {
            return new int[0];
        }

        int shift = k % length;
        if (shift < 0) {
            shift += length;
        }
        int[] rotated = new int[length];
        for (int i = 0; i < length; i++) {
            rotated[(i + shift) % length] = nums[i];
        }
        return rotated;
    }

    public static void main(String[] args) {
        System.out.println("L1 Two Sum");
        System.out.println(Arrays.toString(twoSum(new int[] {2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(twoSum(new int[] {3, 2, 4}, 6)));

        System.out.println("L2 Best Time to Buy and Sell Stock");
        System.out.println(maxProfit(new int[] {7, 1, 5, 3, 6, 4}));
        System.out.println(maxProfit(new int[] {7, 6, 4, 3, 1}));

        System.out.println("L3 Contains Duplicate");
        System.out.println(containsDuplicate(new int[] {1, 2, 3, 1}));
        System.out.println(containsDuplicate(new int[] {1, 2, 3, 4}));

        System.out.println("L4 Merge Two Sorted Arrays");
        System.out.println(Arrays.toString(mergeSortedArrays(
                new int[] {1, 3, 5}, new int[] {2, 4, 6})));
        System.out.println(Arrays.toString(mergeSortedArrays(
                new int[0], new int[] {1, 2, 3})));

        System.out.println("L5 Rotate Array");
        System.out.println(Arrays.toString(rotateArray(new int[] {1, 2, 3, 4, 5, 6, 7}, 3)));
        System.out.println(Arrays.toString(rotateArray(new int[] {1, 2}, 3)));

        boolean edgeCasesPass = twoSum(new int[] {3, 3}, 6)[1] == 1
                && maxProfit(new int[] {5}) == 0
                && !containsDuplicate(new int[0])
                && Arrays.equals(mergeSortedArrays(new int[] {1, 2}, new int[] {1, 3}),
                        new int[] {1, 1, 2, 3})
                && Arrays.equals(rotateArray(new int[] {1, 2, 3}, -1), new int[] {2, 3, 1});
        if (!edgeCasesPass) {
            throw new AssertionError("A practice edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }
}
