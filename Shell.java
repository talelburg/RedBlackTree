/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree {

	public static final RBNode NULL = new RBNode();
	// constant node, parent of root, child of leaves
	private RBNode root = NULL;

	/**
	 * public class RBNode
	 */
	public static class RBNode {
		private int key;
		private String value;
		private RBNode left, right, parent;
		private boolean isRed;
		private int size;

		/**
		 * public RBNODE(int key, String value, RBNode parent)
		 * 
		 * initializes a new node with key, value and parent according to input
		 * left and right initialized to RBTree.NULL, color initialized as red
		 */
		public RBNode(int key, String value, RBNode parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.right = RBTree.NULL;
			this.left = RBTree.NULL;
			this.isRed = true;
			this.size = 1;
		}

		/**
		 * public RBNode()
		 * 
		 * special constructor, used only to instantiate RBTree.NULL - sets it
		 * as parent and both children, its color as black, key as -1 and value
		 * as null
		 */
		public RBNode() {
			this.key = -1;
			this.value = null;
			this.parent = RBTree.NULL;
			this.right = RBTree.NULL;
			this.left = RBTree.NULL;
			this.isRed = false;
			this.size = 0;
		}

		/**
		 * standard getters and setters
		 */
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

		public void setSize(int size) {
			this.size = size;
		}

		public void increaseSize() {
			this.size++;
		}

		public void decreaseSize() {
			this.size--;
		}

		public void updateSize() {
			if (!this.equals(NULL)) {
				this.size = this.right.getSize() + this.left.getSize() + 1;
			}
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

		public int getSize() {
			return this.size;
		}

		/**
		 * public boolean equals(Object obj)
		 * 
		 * standard equals, used to check if node is RBTree.NULL (just to be
		 * safe)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof RBNode))
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
			if (parent == null) {
				if (other.parent != null)
					return false;
			} else if (!parent.equals(other.parent))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			if (size != other.size)
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
		if (!this.root.equals(NULL)) {
			return this.root;
		}
		return null;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return this.root.getSize() == 0;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		// find node
		RBNode y = nodeSearch(k);
		if (y != null) {
			return y.getValue();
		}
		return null;
	}

	/**
	 * public RBNode nodeSearch(int k)
	 * 
	 * returns the node with key k if it exists in the tree otherwise, returns
	 * null
	 */

	public RBNode nodeSearch(int k) {
		RBNode x = this.root;
		// binary search
		while (!x.equals(NULL)) {
			if (k < x.getKey()) {
				x = x.getLeft();
			} else if (k > x.getKey()) {
				x = x.getRight();
			} else {
				return x;
			}
		}
		return null;
	}

	/**
	 * public void leftRotate(RBNode x)
	 * 
	 * rotates the subtree rooted at the node x to the left
	 */

	public void leftRotate(RBNode x) {
		RBNode y = x.getRight();
		x.setRight(y.getLeft());
		if (!y.getLeft().equals(NULL)) {
			y.getLeft().setParent(x); // transfer y's left subtree to x
		}
		y.setParent(x.getParent());
		if (x.getParent().equals(NULL)) {
			this.root = y; // if original was root, fix pointer
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		} // y is now child of its parent
		y.setLeft(x);
		x.setParent(y); // fix child and parent pointers, rotation now complete
		y.setSize(x.getSize());
		x.updateSize();
	}

	/**
	 * public void rightRotate(RBNode x)
	 * 
	 * rotates the subtree rooted at the node x to the right
	 */

	public void rightRotate(RBNode x) {
		RBNode y = x.getLeft();
		x.setLeft(y.getRight());
		if (!y.getRight().equals(NULL)) {
			y.getRight().setParent(x); // transfer y's right subtree to x
		}
		y.setParent(x.getParent());
		if (x.getParent().equals(NULL)) {
			this.root = y; // if original was root, fix pointer
		} else if (x == x.getParent().getRight()) {
			x.getParent().setRight(y);
		} else {
			x.getParent().setLeft(y);
		} // y is now child of its parent
		y.setRight(x);
		x.setParent(y); // fix child and parent pointers, rotation now complete
		y.setSize(x.getSize());
		x.updateSize();
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
		// check if node exists
		if (nodeSearch(k) != null) {
			return -1;
		}

		RBNode x = this.root;
		RBNode y = NULL;
		RBNode z = new RBNode(k, v, NULL); // node does not exist, create new

		// binary search to find appropriate position to insert
		while (!x.equals(NULL)) {
			y = x;
			if (k < x.getKey()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		z.setParent(y); // found position, insert node

		if (y.equals(NULL)) { // if tree was empty, update root
			this.root = z;
			z.setIsRed(false);
		} else if (k < y.getKey()) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		} // z is now in appropriate subtree of y
		x = z;
		while (x != this.root) {
			x = x.getParent();
			x.increaseSize();
		}
		return insertFixUp(z); // restore red and black rules
	}

	/**
	 * public int insertFixUp(RBNode z)
	 * 
	 * performs color switches and rotations to preserve the red and black rules
	 * following the insertion of the node z. returns the number of
	 * color-switches performed while fixing the tree.
	 */

	public int insertFixUp(RBNode z) {
		RBNode y = NULL;
		int count = 0;
		while (z.getParent().isRed()) {
			if (z.getParent() == z.getParent().getParent().getLeft()) {
				// z's parent is a left child
				y = z.getParent().getParent().getRight();
				if (y.isRed()) { // case 1 - z's uncle y is red
					z.getParent().setIsRed(false);
					count++;
					y.setIsRed(false);
					count++;
					z.getParent().getParent().setIsRed(true);
					count++;
					z = z.getParent().getParent();
				} else { // z's uncle y is black
					if (z == z.getParent().getRight()) { // z is a right child
						z = z.getParent();
						leftRotate(z);
					} // case 3 - z is a left child
					z.getParent().setIsRed(false);
					count++;
					z.getParent().getParent().setIsRed(true);
					count++;
					rightRotate(z.getParent().getParent());
				}
			} else { // z's parent is a right child
				y = z.getParent().getParent().getLeft();
				if (y.isRed()) { // case 1 - z's uncle y is red
					z.getParent().setIsRed(false);
					count++;
					y.setIsRed(false);
					count++;
					z.getParent().getParent().setIsRed(true);
					count++;
					z = z.getParent().getParent();
				} else { // z's uncle y is black
					if (z == z.getParent().getLeft()) { // case 2 - z is a left
														// child
						z = z.getParent();
						rightRotate(z);
					} // case 3 - z is a right child
					z.getParent().setIsRed(false);
					count++;
					z.getParent().getParent().setIsRed(true);
					count++;
					leftRotate(z.getParent().getParent());
				}
			}
		}
		if (this.root.isRed()) {
			count++;
		}
		this.root.setIsRed(false); // make sure root is black

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
	public int delete(int k) {
		RBNode z = nodeSearch(k); // find node to be deleted
		if (z == null) {
			return -1;
		}
		// node exists
		RBNode y = z, x;
		boolean isOriginalYRed = y.isRed();
		int count = 0;

		while (y != this.root) {
			y = y.getParent();
			y.decreaseSize();
		}
		y = z;

		if (z.getLeft().equals(NULL)) { // check if z has less than two children
			x = z.getRight();
			transplant(z, z.getRight());
		} else if (z.getRight().equals(NULL)) {
			x = z.getLeft();
			transplant(z, z.getLeft());
		} else { // z has two children
			y = findSuccessor(z);
			isOriginalYRed = y.isRed();
			x = y.getRight(); // keep track of the node to carry the extra
								// blackness
			// move y into z's position in the tree
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			if (y.isRed() != z.isRed()) {
				count++;
			}
			y.setIsRed(z.isRed());
		}
		if (!isOriginalYRed) { // if original was black extra blackness is
								// present
			count += deleteFixUp(x); // fix extra blackness
		}
		return count;
	}

	/**
	 * public void transplant(RBNode x, RBNode y)
	 * 
	 * moves node y into x's position in the tree, setting its parent to be x's
	 * and changing the parent's child to be y. does not make x's children y's
	 * children.
	 * 
	 * might change NULL's parent to be some node - necessary for fix-up to work
	 * properly, fix-up will restore NULL's parent as NULL after finishing its
	 * work.
	 */

	public void transplant(RBNode x, RBNode y) {
		if (x.getParent().equals(NULL)) { // if x is root update root
			this.root = y;
		} else if (x == x.getParent().getLeft()) { // make sure y is x's parent
													// appropriate child
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setParent(x.getParent()); // update y's parent
	}

	/**
	 * public int deleteFixUp(RBNode x)
	 * 
	 * performs color switches and rotations to preserve the red and black
	 * rules. receives as input the node x that carries the extra blackness
	 * following a deletion. returns the number of color switches performed
	 * while fixing the tree.
	 */

	public int deleteFixUp(RBNode x) {
		int count = 0;
		RBNode w;
		while (!x.equals(this.root) && !x.isRed()) {
			if (x == x.getParent().getLeft()) { // x is a left child
				w = x.getParent().getRight();
				if (w.isRed()) { // case 1 - x's sibling w is red
					w.setIsRed(false);
					count++;
					x.getParent().setIsRed(true);
					count++;
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				} // x's sibling w is now definitely black
				if (!w.getLeft().isRed() && !w.getRight().isRed()) {
					// case 2 - both of w's children are black
					w.setIsRed(true);
					count++;
					x = x.getParent();
				} else {
					if (!w.getRight().isRed()) {
						// case 3 - w's right child is black and w's left child
						// is red
						w.getLeft().setIsRed(false);
						count++;
						w.setIsRed(true);
						count++;
						rightRotate(w);
						w = x.getParent().getRight();
					} // case 4 - w's right child is red
					if (w.isRed() != x.getParent().isRed()) {
						count++;
					}
					w.setIsRed(x.getParent().isRed());
					if (x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(false);
					w.getRight().setIsRed(false);
					count++;
					leftRotate(x.getParent());
					x = this.root;
				}
			} else {// x is a right child
				w = x.getParent().getLeft();
				if (w.isRed()) { // case 1 - x's sibling w is red
					w.setIsRed(false);
					count++;
					x.getParent().setIsRed(true);
					count++;
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				} // x's sibling w is now definitely black
				if (!w.getRight().isRed() && !w.getLeft().isRed()) {
					// case 2 - both of w's children are black
					w.setIsRed(true);
					count++;
					x = x.getParent();
				} else {
					if (!w.getLeft().isRed()) {
						// case 3 - w's left child is black and w's right child
						// is red
						w.getRight().setIsRed(false);
						count++;
						w.setIsRed(true);
						count++;
						leftRotate(w);
						w = x.getParent().getLeft();
					} // case 4 - w's left child is red
					if (w.isRed() != x.getParent().isRed()) {
						count++;
					}
					w.setIsRed(x.getParent().isRed());
					if (x.getParent().isRed()) {
						count++;
					}
					x.getParent().setIsRed(false);
					w.getLeft().setIsRed(false);
					count++;
					rightRotate(x.getParent());
					x = this.root;
				}
			}
		}
		if (x.isRed()) {
			count++;
		}
		x.setIsRed(false); // either make sure root is black, or get rid of
							// extra blackness by coloring from red to black
		NULL.setParent(NULL); // make sure NULL's parent is NULL
		return count;
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (this.empty()) {
			return null;
		}
		return treeMin(this.root).getValue();
	}

	/**
	 * public RBNode treeMin(RBNode x)
	 * 
	 * returns the node with minimal key in the subtree rooted at the node x
	 */

	public RBNode treeMin(RBNode x) {
		RBNode node = x;
		while (!node.getLeft().equals(NULL)) { // go as far left as possible
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
		if (this.empty()) {
			return null;
		}
		return treeMax(this.root).getValue();
	}

	/**
	 * public RBNode treeMax(RBNode x)
	 * 
	 * returns the node with maximal key in the subtree rooted at the node x
	 */
	public RBNode treeMax(RBNode x) {
		RBNode node = x;
		while (!node.getRight().equals(NULL)) { // go as far right as possible
			node = node.getRight();
		}
		return node;
	}

	/**
	 * public RBNode findSuccessor(RBNode x)
	 * 
	 * returns the node with the next-largest key, following the node x
	 */

	public RBNode findSuccessor(RBNode x) {
		RBNode node = x;
		if (!node.getRight().equals(NULL)) { // if x has a right child
			return treeMin(node.getRight());
			// find minimal node in right subtree of x, a.k.a its successor
		}
		// x does not have a right child
		while (node == node.getParent().getRight()) {
			node = node.getParent();
		}
		return node.getParent();
		// return the lowest node which has x in its left subtree
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		if (empty()) { // triviality check - if tree is empty
			return new int[0];
		}
		int[] arr = new int[size()];
		RBNode node = treeMin(this.root);
		arr[0] = node.getKey(); // start with minimal key
		for (int i = 1; i < arr.length; i++) { // insert next smallest key
			node = findSuccessor(node);
			arr[i] = node.getKey();
		}
		return arr;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		if (empty()) { // triviality check - if tree is empty
			return new String[0];
		}
		String[] arr = new String[size()];
		RBNode node = treeMin(this.root);
		arr[0] = node.getValue(); // start with minimal key
		for (int i = 1; i < arr.length; i++) { // insert by order of keys
			node = findSuccessor(node);
			arr[i] = node.getValue();
		}
		return arr;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return this.root.getSize();
	}

	/**
	 * public int rank(int k)
	 *
	 * Returns the number of nodes in the tree with a key smaller than k.
	 *
	 * precondition: none postcondition: none
	 */
	public int rank(int k) {
		if (empty()) {
			// if tree is empty, rank is zero
			return 0;
		}
		if (k > treeMax(this.root).getKey()) {
			// k is bigger then all the keys in the tree
			return size();
		}
		RBNode z = nodeSearch(k);
		if (z == null) {
			RBNode m = this.root;
			RBNode y = NULL;
			
			while (!m.equals(NULL)) {
				y = m;
				if (k < m.getKey()) {
					m = m.getLeft();
				} else {
					m = m.getRight();
				}
			}

			if (k < y.getKey()) {
				z = y;
			} else {
				z = findSuccessor(y);
			}
		}

		int rank = z.getLeft().getSize();
		RBNode x = z;
		while (x != this.root) {
			if (x == x.getParent().getRight()) {
				rank += x.getParent().getLeft().getSize() + 1;
			}
			x = x.getParent();
		}
		return rank;
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */
}
