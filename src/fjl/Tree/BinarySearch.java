package fjl.Tree;
//Given a complete binary tree, count the number of nodes.
//Definition of a complete binary tree from Wikipedia:
//In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
//Subscribe to see which companies asked this question
//Hide Tags Tree Binary Search
//Hide Similar Problems (E) Closest Binary Search Tree Value

public class BinarySearch {
	/**
	 * Definition for a binary tree node. public class TreeNode { int val;
	 * TreeNode left; TreeNode right; TreeNode(int x) { val = x; } }
	 */
	public class Solution222 {
		/**
		 * @param root
		 * 此方法相当于给完全二叉树编号
		 * 左节点为根节点*2，右节点为根节点*2+1.因此只要找到最后一个节点的编号，即为节点个数。
		 * @return
		 */
		public int countNodes0(TreeNode root) {
			return root == null ? 0 : findLastIndex(root, 1);
		}

		/**
		 * @param node
		 * @param count
		 * 没有子节点，高度为0.
		 * @return
		 */
		private int lHeight(TreeNode node, int count) {
			return node == null ? count - 1 : lHeight(node.left, count + 1);
		}

		/**
		 * @param node
		 * @param currIndex
		 * 找到最后一个节点编号。
		 * @return
		 */
		private int findLastIndex(TreeNode node, int currIndex) {
			if (node.left == null && node.right == null)
				return currIndex;
			if (lHeight(node.left, 1) == lHeight(node.right, 1))
				return findLastIndex(node.right, currIndex * 2 + 1);
			else
				return findLastIndex(node.left, currIndex * 2);
		}

		public int countNodes(TreeNode root) {
			if (root == null)
				return 0;
			int l = oneSide(root.left); // 计算左子树的高度
			int r = oneSide(root.right);// 计算右子树的高度
			if (l == r) { // 如果左子树和右子树高度一样，因为是完全二叉树，所以左子树肯定为满二叉树。不然右子树高度不可能一样。
				return countNodes(root.right) + (1 << l); // 满二叉树节点个数2^n-1.所以使用1<<l为左子树的节点个数+根节点
				// 再加上右子树的节点个数，递归计算右子树节点数目即可。
			}
			return countNodes(root.left) + (1 << r);// 如果不一样，那么最后一个节点在左子树上，右子树为满二叉树。递归计算左子树。
		}

		/**
		 * @param 传入一棵树计算高度
		 * @return
		 */
		public int oneSide(TreeNode left) {
			int height = 0;
			while (left != null) {
				left = left.left;
				height++;
			}
			return height;
		}

		/**
		 * @param root
		 * 二分查找计算完全二叉树节点个数
		 * 高度为h的完全二叉树，h-1层以上为满二叉树，节点个数是2^h-1。
		 * 使用二分查找计算第h层的节点个数。先算出h，再找到最后一层中间节点的高度是否等于h。
		 * 若等于，则最后节点在右子树，若不等，则最后节点在左子树。
		 * @return
		 */
		public int countNodes1(TreeNode root) {
			if (root == null) {
				return 0;
			}
			TreeNode node = root.left;
			int height = 0;
			while (node != null) {
				node = node.left;
				height++;
			}
			// 以上为计算整棵树高度。
			int level = 0;
			int depth = 0;
			int count = 0;
			while (root != null) {
				depth = level;
				node = root.left;
				if (node == null) {
					break;
				}
				while (node != null) {
					node = node.right;
					depth++;//找到中间节点的高度
				}
				level++;	//每向下寻找一层，level++。当前求的二叉树高度即为height-level。
				if (depth == height) {// 若中间点达到最大高度，则去往右边的二叉树寻找。
					root = root.right;
					count += 1 << (height - level);  //左边为满二叉树。
				} else {
					root = root.left;// 否则最后一个节点在左子树，继续在左子树寻找
				}
			}
			if (root != null && level == height) {
				count++;	//只有根节点的情况
			}
			return (1 << height) - 1 + count; //h-1层为满二叉树，节点个数为2^h-1。再加上最后一层的个数count.
		}
	}

}
