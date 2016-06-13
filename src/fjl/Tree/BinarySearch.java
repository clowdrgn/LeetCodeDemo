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
		 * �˷����൱�ڸ���ȫ���������
		 * ��ڵ�Ϊ���ڵ�*2���ҽڵ�Ϊ���ڵ�*2+1.���ֻҪ�ҵ����һ���ڵ�ı�ţ���Ϊ�ڵ������
		 * @return
		 */
		public int countNodes0(TreeNode root) {
			return root == null ? 0 : findLastIndex(root, 1);
		}

		/**
		 * @param node
		 * @param count
		 * û���ӽڵ㣬�߶�Ϊ0.
		 * @return
		 */
		private int lHeight(TreeNode node, int count) {
			return node == null ? count - 1 : lHeight(node.left, count + 1);
		}

		/**
		 * @param node
		 * @param currIndex
		 * �ҵ����һ���ڵ��š�
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
			int l = oneSide(root.left); // �����������ĸ߶�
			int r = oneSide(root.right);// �����������ĸ߶�
			if (l == r) { // ������������������߶�һ������Ϊ����ȫ�������������������϶�Ϊ������������Ȼ�������߶Ȳ�����һ����
				return countNodes(root.right) + (1 << l); // ���������ڵ����2^n-1.����ʹ��1<<lΪ�������Ľڵ����+���ڵ�
				// �ټ����������Ľڵ�������ݹ�����������ڵ���Ŀ���ɡ�
			}
			return countNodes(root.left) + (1 << r);// �����һ������ô���һ���ڵ����������ϣ�������Ϊ�����������ݹ������������
		}

		/**
		 * @param ����һ��������߶�
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
		 * ���ֲ��Ҽ�����ȫ�������ڵ����
		 * �߶�Ϊh����ȫ��������h-1������Ϊ�����������ڵ������2^h-1��
		 * ʹ�ö��ֲ��Ҽ����h��Ľڵ�����������h�����ҵ����һ���м�ڵ�ĸ߶��Ƿ����h��
		 * �����ڣ������ڵ����������������ȣ������ڵ�����������
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
			// ����Ϊ�����������߶ȡ�
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
					depth++;//�ҵ��м�ڵ�ĸ߶�
				}
				level++;	//ÿ����Ѱ��һ�㣬level++����ǰ��Ķ������߶ȼ�Ϊheight-level��
				if (depth == height) {// ���м��ﵽ���߶ȣ���ȥ���ұߵĶ�����Ѱ�ҡ�
					root = root.right;
					count += 1 << (height - level);  //���Ϊ����������
				} else {
					root = root.left;// �������һ���ڵ�����������������������Ѱ��
				}
			}
			if (root != null && level == height) {
				count++;	//ֻ�и��ڵ�����
			}
			return (1 << height) - 1 + count; //h-1��Ϊ�����������ڵ����Ϊ2^h-1���ټ������һ��ĸ���count.
		}
	}

}
