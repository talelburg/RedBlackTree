/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree {
	private RBNode root;
	private static final RBNode NULL = new RBNode();
	private int size = 0;

	/**
	 * public class RBNode
	 */
	public static class RBNode {
		private int key;
		private String value;
		private RBNode left, right, parent;
		private boolean isRed;

		public RBNode(int key, String value, RBNode parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.right = NULL;
			this.left = NULL;
			this.isRed = true;
		}

		public RBNode() {
			this.key = -1;
			this.value = null;
			this.parent = this;
			this.right = NULL;
			this.left = NULL;
			this.isRed = false;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public void setLeft(RBNode left) {
			this.left = left;
		}

		public void setRight(RBNode right) {
			this.right = right;
		}

		public void setParent(RBNode parent) {
			this.parent = parent;
		}

		public void setIsRed(boolean isRed) {
			this.isRed = isRed;
		}

		public int getKey() {
			return this.key;
		}

		public String getValue() {
			return this.value;
		}

		public RBNode getLeft() {
			return this.left;
		}

		public RBNode getRight() {
			return this.right;
		}

		public RBNode getParent() {
			return this.parent;
		}

		public boolean isRed() {
			return this.isRed;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RBNode other = (RBNode) obj;
			if (isRed != other.isRed)
				return false;
			if (key != other.key)
				return false;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
	}

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot() {
		return this.root;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return this.size == 0;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		RBNode y = NodeSearch(k);
		if (y != null) {
			return y.getValue();
		}
		return null;
	}

	public RBNode NodeSearch(int k) {
		RBNode x = this.root;
		RBNode y = NULL;

		while (!x.equals(NULL)) {
			y = x;
			if (k < x.getKey()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}

		if (y.getKey() == k) {
			return y;
		}
		return null;
	}

	public void LeftRotate(RBNode x) {
		RBNode y = x.getRight();
		x.setRight(y.getLeft());
		if (!y.getLeft().equals(NULL)) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent().equals(NULL)) {
			this.root = y;
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
	}

	public void RightRotate(RBNode x) {
		RBNode y = x.getLeft();
		x.setLeft(y.getRight());
		if (!y.getRight().equals(NULL)) {
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent().equals(NULL)) {
			this.root = y;
		} else if (x == x.getParent().getRight()) {
			x.getParent().setRight(y);
		} else {
			x.getParent().setLeft(y);
		}
		y.setRight(x);
		x.setParent(y);
	}

	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {

		if (NodeSearch(k) == null) {
			return -1;
		}

		this.size++;
		RBNode x = this.root;
		RBNode y = NULL;
		RBNode z = new RBNode(k, v, NULL);

		while (!x.equals(NULL)) {
			y = x;
			if (k < x.getKey()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		z.setParent(y);

		if (y.equals(NULL)) {
			this.root = z;
		} else if (k < y.getKey()) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		}
		return InsertFixUp(z);
	}

	public int InsertFixUp(RBNode z) {
		RBNode y = NULL;
		int count = 0;
		while (z.getParent().isRed()) {
			if (z.getParent() == z.getParent().getParent().getLeft()) {
				y = z.getParent().getParent().getRight();
				if (y.isRed()) {
					if (z.getParent().isRed) {
						count++;
					}
					z.getParent().setIsRed(false);
					if (y.isRed) {
						count++;
					}
					y.setIsRed(false);
					if (!z.getParent().getParent().isRed()) {
						count++;
					}
					z.getParent().getParent().setIsRed(true);
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getRight()) {
						z = z.getParent();
						LeftRotate(z);
					}
					if (z.getParent().isRed) {
						count++;
					}
					z.getParent().setIsRed(false);
					if (!z.getParent().getParent().isRed()) {
						count++;
					}
					z.getParent().getParent().setIsRed(true);
					RightRotate(z.getParent().getParent());
				}
			} else {
				y = z.getParent().getParent().getLeft();
				if (y.isRed()) {
					if (z.getParent().isRed) {
						count++;
					}
					z.getParent().setIsRed(false);
					if (y.isRed) {
						count++;
					}
					y.setIsRed(false);
					if (!z.getParent().getParent().isRed()) {
						count++;
					}
					z.getParent().getParent().setIsRed(true);
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getLeft()) {
						z = z.getParent();
						RightRotate(z);
					}
					if (z.getParent().isRed) {
						count++;
					}
					z.getParent().setIsRed(false);
					if (!z.getParent().getParent().isRed()) {
						count++;
					}
					z.getParent().getParent().setIsRed(true);
					LeftRotate(z.getParent().getParent());
				}
			}
			this.root.setIsRed(false);
		}
		return count;
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int Delete(int k) {
		RBNode z = NodeSearch(k);
		if (z == null) {
			return -1;
		}
		RBNode y = z, x;
		boolean isOriginalYRed = y.isRed();
		int count = 0;
		if (z.getLeft().equals(NULL)) {
			x = z.getRight();
			Transplant(z, z.getRight());
		} else if (z.getRight().equals(NULL)) {
			x = z.getLeft();
			Transplant(z, z.getLeft());
		} else {
			y = TreeMin(z.getRight());
			isOriginalYRed = y.isRed();
			x = y.getRight();
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				Transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			Transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			if (y.isRed()) {
				count++;
			}
			y.setIsRed(z.isRed());
		}
		if (!isOriginalYRed) {
			count += DeleteFixUp(x);
		}
		return count;
	}

	public void Transplant(RBNode x, RBNode y) {
		if (x.getParent().equals(NULL)) {
			this.root = y;
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setParent(x.getParent());
	}

	public int DeleteFixUp(RBNode x) {
		int count = 0;
		RBNode w;
		while (!x.equals(this.root) && !x.isRed()) {
			if (x == x.getParent().getLeft()) {
				w = x.getParent().getRight();
				if (w.isRed()) {
					w.setIsRed(false);
					count++;
					if (!x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(true);
					LeftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if (!w.getLeft().isRed() && !w.getRight().isRed()) {
					if (!w.isRed()) {
						count++;
					}
					w.setIsRed(true);
					x = x.getParent();
				} else {
					if (!w.getRight().isRed()) {
						if (w.getLeft().isRed) {
							count++;
						}
						w.getLeft().setIsRed(false);
						if (!w.isRed()) {
							count++;
						}
						w.setIsRed(true);
						RightRotate(w);
						w = x.getParent().getRight();
					}
					if (w.isRed() != x.getParent().isRed()) {
						count++;
					}
					w.setIsRed(x.getParent().isRed());
					if (x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(false);
					if (w.getRight().isRed) {
						count++;
					}
					w.getRight().setIsRed(false);
					LeftRotate(x.getParent());
					x = this.root;
				}
			} else {
				w = x.getParent().getLeft();
				if (w.isRed()) {
					w.setIsRed(false);
					count++;
					if (!x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(true);
					RightRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if (!w.getRight().isRed() && !w.getLeft().isRed()) {
					if (!w.isRed()) {
						count++;
					}
					w.setIsRed(true);
					x = x.getParent();
				} else {
					if (!w.getLeft().isRed()) {
						if (w.getRight().isRed) {
							count++;
						}
						w.getRight().setIsRed(false);
						if (!w.isRed()) {
							count++;
						}
						w.setIsRed(true);
						LeftRotate(w);
						w = x.getParent().getLeft();
					}
					if (w.isRed() != x.getParent().isRed()) {
						count++;
					}
					w.setIsRed(x.getParent().isRed());
					if (x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(false);
					if (w.getLeft().isRed) {
						count++;
					}
					w.getLeft().setIsRed(false);
					RightRotate(x.getParent());
					x = this.root;
				}
			}
		}
		x.setIsRed(false);
		return count;
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		return TreeMin(this.root).getValue();
	}

	public RBNode TreeMin(RBNode x) {
		RBNode node = x;
		while (!node.getLeft().equals(NULL)) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() {
		RBNode node = this.root;
		while (!node.getRight().equals(NULL)) {
			node = node.getRight();
		}
		return node.getValue();
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		String[] arr = new String[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return 42; // to be replaced by student code
	}

	/**
	 * public int rank(k)
	 *
	 * Returns the number of nodes in the tree with a key smaller than k.
	 *
	 * precondition: none postcondition: none
	 */
	public int rank(RBNode k) {
		return 42; // to be replaced by student code
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */

}
