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
		int[] temp = new int[nums.length];   //��̬�滮����
		temp[0] = nums[0];					//���һ��Ԫ��
		int len = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > temp[len]) {		//�õ���temp������һ���ϸ�������С�
				temp[len + 1] = nums[i];
				len++;
			} else {
				int index = binarySearch(temp, len, nums[i]);	//���ֲ��ң���õ�ǰnums[i]��temp��ǰԪ�ؿ��ܸ���Ǳ����Ԫ�ص��±꣬�滻��
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

	public static void main(String[] args) {
		int[] nums = { 6, 7, 8, 9, 10, 2, 3, 4, 5 };
		System.out.println(lengthOfLIS(nums));
	}
}
