package fjl.dp;

public class LIS300 {
	/**
	 * 
	 * Given an unsorted array of integers, find the length of longest
	 * increasing subsequence.
	 * 
	 * For example, Given [10, 9, 2, 5, 3, 7, 101, 18], The longest increasing
	 * subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there
	 * may be more than one LIS combination, it is only necessary for you to
	 * return the length.
	 * 
	 * Your algorithm should run in O(n2) complexity.
	 * 
	 * Follow up: Could you improve it to O(n log n) time complexity?
	 * 
	 * Credits: Special thanks to @pbrother for adding this problem and creating
	 * all test cases.
	 * 
	 * Subscribe to see which companies asked this question
	 * 
	 * Hide Tags Dynamic Programming Binary Search Show Similar Problems
	 * 
	 * @param nums
	 * @return
	 */
	public static int lengthOfLIS(int[] nums) {
		int[] temp = new int[nums.length];   //动态规划数组
		temp[0] = nums[0];					//存第一个元素
		int len = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > temp[len]) {		//得到的temp数组是一个严格递增序列。
				temp[len + 1] = nums[i];
				len++;
			} else {
				int index = binarySearch(temp, len, nums[i]);	//二分查找，求得当前nums[i]比temp当前元素可能更有潜力的元素的下标，替换。
				temp[index] = nums[i];
			}
		}
		return len + 1;
	}

	public static int binarySearch(int[] temp, int len, int val) {
		int start = 0;
		while (start <= len) {
			int mid = start + (len - start) / 2;
			if (temp[mid] == val) {
				return mid;
			} else if (temp[mid] < val) {
				start = mid + 1;
			} else {
				len = mid - 1;
			}
		}
		return start;
	}
	 
    public static int longestIncreasingSubsequence(int[] nums) {
        int []f = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            f[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] <= nums[i]) {
                    f[i] = f[i] > f[j] + 1 ? f[i] : f[j] + 1;
                }
            }
            if (f[i] > max) {
                max = f[i];
            }
        }
        return max;
    }
    public static int longestIncreasingSubsequence1(int[] nums) {
        int[] minLast = new int[nums.length + 1];
        minLast[0] = -1;
        for (int i = 1; i <= nums.length; i++) {
            minLast[i] = Integer.MAX_VALUE;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // find the first number in minLast > nums[i]
            int index = binarySearch(minLast, nums[i]);
            minLast[index] = nums[i];
        }
        
        for (int i = nums.length; i >= 1; i--) {
            if (minLast[i] != Integer.MAX_VALUE) {
                return i;
            }
        }
        
        return 0;
    }
    
    // find the first number > num
    private static int binarySearch(int[] minLast, int num) {
        int start = 0, end = minLast.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (minLast[mid] == num) {
                start = mid;
            } else if (minLast[mid] < num) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (minLast[start] > num) {
            return start;
        }
        return end;
    }
	public static void main(String[] args) {
		int[] nums = { 6, 7, 8, 9, 10, 2, 3, 4, 5 };
		System.out.println(lengthOfLIS(nums));
		System.out.println(longestIncreasingSubsequence(nums));
		System.out.println(longestIncreasingSubsequence1(nums));
	}
}
