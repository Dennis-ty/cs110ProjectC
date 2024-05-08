import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
	}

	@Override
	public boolean add(T newEntry) {
		if (isEmpty()) {
			return super.add(newEntry);
		} else {
			return addEntryHelperNonRecursive(newEntry);
		}
	}

	// IMPLEMENT THIS METHOD; THIS METHOD CANNOT BE RECURSIVE
	private boolean addEntryHelperNonRecursive(T newEntry) {
		// YOUR CODE HERE! 
    		BinaryNode<T> newNode = new BinaryNode<>(newEntry);
    			if (root == null) {
        			root = newNode; // If the tree is empty, set the new node as the root
        			return true;
    			}

    		BinaryNode<T> currentNode = root;
    		boolean found = false;

    		while (!found) {
        		T currentEntry = currentNode.getData();
        		int comparison = newEntry.compareTo(currentEntry);

        		if (comparison == 0) {
            			if (currentNode.hasLeftChild()) {
                			currentNode = currentNode.getLeftChild();
            			} else {
                			found = true;
                			currentNode.setLeftChild(newNode);
            			}
        		} else if (comparison < 0) {
            			if (currentNode.hasLeftChild()) {
                			currentNode = currentNode.getLeftChild();
            			} else {
                			found = true;
                			currentNode.setLeftChild(newNode);
            			}
        		} else {
            			if (currentNode.hasRightChild()) {
	                	currentNode = currentNode.getRightChild();
            		} else {
                		found = true;
                		currentNode.setRightChild(newNode);
            			}
       			}
    		}
    		return true;
	}

	// THIS METHOD CANNOT BE RECURSIVE.
	// Make sure to take advantage of the sorted nature of the BST!
	public int countIterative(T target) {
		// YOUR CODE HERE!
		int count = 0;
		BinaryNode<T> currentNode = root;
		while(null != currentNode) {
			if(target.compareTo(currentNode.getData()) > 0) {
				currentNode = currentNode.getRightChild();
			} else {
				if(target.compareTo(currentNode.getData()) == 0) {
					count++;
				}
				currentNode = currentNode.getLeftChild();
			}
		}
		return count;
	}

	// THIS METHOD MUST BE RECURSIVE! 
	// You are allowed to create a private helper.
	// Make sure to take advantage of the sorted nature of the BST!
	public int countGreaterRecursive(T target) {
		// YOUR CODE HERE! 
			
		// this initial code is meant as a suggestion to get your started- use it or delete it!
		// int count = 0;
		// BinaryNode<T> rootNode = root;
				
		// consider a helper method!
			
		return countGreaterRecursiveHelper(target, root);
	}

	private int countGreaterRecursiveHelper(T target, BinaryNode<T> node) {
		if (null == node) {
			return 0;
		}
		if (target.compareTo(node.getData()) > 0) {
			return countGreaterRecursiveHelper(target, node.getRightChild());
		} else if (target.compareTo(node.getData()) < 0) {
			return 1 + countGreaterRecursiveHelper(target, node.getLeftChild())
					+ countGreaterRecursiveHelper(target, node.getRightChild());
		} else {
			return countGreaterRecursiveHelper(target, node.getLeftChild())
					+ countGreaterRecursiveHelper(target, node.getRightChild());
		}
	}

	// THIS METHOD CANNOT BE RECURSIVE.
	// Hint: use a stack!
	// Make sure to take advantage of the sorted nature of the BST!
	public int countGreaterIterative(T target) {
		// YOUR CODE HERE!
		
		// this initial code is meant as a suggestion to get your started- use it or delete it!
		int count = 0;
		BinaryNode<T> currentNode = root;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		// nodeStack.push(rootNode);

		// consider a loop based on the stack!
		while (!nodeStack.isEmpty() || null != currentNode) {
			// find rightmost node with no right child
			while (null != currentNode) {
				nodeStack.push(currentNode);
				currentNode = currentNode.getRightChild();
			}
			if (!nodeStack.isEmpty()) {
				BinaryNode<T> nextNode = nodeStack.pop();
				if (target.compareTo(nextNode.getData()) > 0) {
					// For BST, all the rest nodes should be less that target, no need to continue
					return count;
				}
				if (target.compareTo(nextNode.getData()) < 0) {
					count++;
				}
				currentNode = nextNode.getLeftChild();
			}
		}
		
		return count;
	}
			
	
	// For full credit, the method should be O(n).
	// You are allowed to use a helper method.
	// The method can be iterative or recursive.
	// If you make the method recursive, you might need to comment out the call to the method in Part B.
	public int countUniqueValues() {
		// YOUR EXTRA CREDIT CODE HERE! 
		Set<T> uniqueValues = new HashSet<>();
		countUniqueValuesHelper(root, uniqueValues);
		return uniqueValues.size();
	}
	
	private void countUniqueValuesHelper(BinaryNode<T> node, Set<T> uniqueValues) {
		if (null == node) {
			return;
		}
		uniqueValues.add(node.getData());
		countUniqueValuesHelper(node.getLeftChild(), uniqueValues);
		countUniqueValuesHelper(node.getRightChild(), uniqueValues);
	}
}
