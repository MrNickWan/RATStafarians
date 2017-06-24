import java.util.Set;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Robert Lin
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }

        for (T element : data) {
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * This private helper method is used in add method, returning
     * the appropriate node for setting reference
     *
     * @param data the data the method is inserting
     * @param node the current node at which the addHelper is on
     * @return the node at which the parent's reference should
     * point to
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            AVLNode<T> newNode = new AVLNode<>(data);
            newNode.setHeight(0);
            newNode.setBalanceFactor(0);
            return newNode;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        }
        node = rebalance(node);
        return node;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0) {
            throw new NoSuchElementException(
                    "No matching element because AVL is empty");
        } else {
            AVLNode<T> itemReturned = new AVLNode<T>(null);
            root = removeHelper(data, root, itemReturned);
            return itemReturned.getData();
        }
    }

    /**
     * This method is the private remove helper method that has several cases
     * in which the method can go with, the node has no children, 1 child, or
     * 2 children and acts accordingly
     *
     * @throws NoSuchElementException if the data do not exit in the AVL
     * @param data the data up for removal
     * @param node the node at which the recursive call is at
     * @param item the dummy node to store the data of removed node
     * @return the removed node
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> item) {
        if (node == null) {
            throw new NoSuchElementException("Data does not exist");
        } else if (data.compareTo(node.getData()) == 0) {
            item.setData(node.getData());
            size--;
            if (node.getLeft() != null) {
                if (node.getRight() != null) {
                    AVLNode<T> placeHolder = new AVLNode<T>(null);
                    node.setRight(removePredecessor(
                            node.getRight(), placeHolder));
                    node.setData(placeHolder.getData());
                } else {
                    return node.getLeft();
                }
            } else if (node.getRight() != null) {
                return node.getRight();
            } else {
                return null;
            }
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), item));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), item));
        }
        node = rebalance(node);
        return node;
    }

    /**
     * The private helper method for replacing the predecessor
     *
     * @param node the current node at the recursive call
     * @param placeHolder the dummy node for storing predecessor value
     * @return the node that helps reference the new subtree
     */
    private AVLNode<T> removePredecessor(AVLNode<T> node,
                                         AVLNode<T> placeHolder) {
        if (node.getLeft() != null) {
            node.setLeft(removePredecessor(node.getLeft(), placeHolder));
        } else {
            placeHolder.setData(node.getData());
            if (node.getRight() != null) {
                return node.getRight();
            } else {
                return null;
            }
        }
        node = rebalance(node);
        return node;
    }

    /**
     * The private helper method for AVL left rotations
     *
     * @param node the node the method is operating on
     * @return the replaced balanced node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        int leftHeight = (node.getLeft() == null) ? -1
                : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? -1
                : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
        return rightNode;
    }

    /**
     * The private helper method for AVL right rotations
     *
     * @param node the node the method is operating on
     * @return the replaced balanced node
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        int leftHeight = (node.getLeft() == null) ? -1
                : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? -1
                : node.getRight().getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
        return leftNode;
    }

    /**
     * The re-balance method is the private helper method that re-balances the
     * node every time a operation (add/remove) has been done on that subtree
     * and parent node
     *
     * @param node the node it is rebalancing
     * @return the balanced node
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        int leftHeight = (node.getLeft() == null) ? -1
                : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? -1
                : node.getRight().getHeight();

        if (leftHeight - rightHeight > 1 || leftHeight - rightHeight < -1) {
            if (leftHeight - rightHeight > 1) {
                if (node.getLeft() != null && node.getLeft()
                        .getBalanceFactor() == -1) {
                    node.setLeft(rotateLeft(node.getLeft()));
                }
                node = rotateRight(node);
            } else if (leftHeight - rightHeight < -1) {
                if (node.getRight() != null && node.getRight()
                        .getBalanceFactor() == 1) {
                    node.setRight(rotateRight(node.getRight()));
                }
                node = rotateLeft(node);
            }
            node.setHeight(Math.max(node.getLeft().getHeight(),
                    node.getRight().getHeight()) + 1);
            node.setBalanceFactor(node.getLeft().getHeight()
                    - node.getRight().getHeight());
        } else {
            node.setHeight(Math.max(leftHeight, rightHeight) + 1);
            node.setBalanceFactor(leftHeight - rightHeight);
        }
        return node;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0) {
            throw new NoSuchElementException(
                    "No matching element because AVL is empty");
        }
        return getHelper(data, root, true);
    }

    /**
     * The private helper method for get and contains(because I am lazy)
     *
     * @throws NoSuchElementException if the data do not exit in the AVL
     * @param data the data of interest in the AVL
     * @param node the current node in the recursive call
     * @param getMethod the boolean value that determines which
     *                  method called the helper method
     * @return the data of interest in the AVL
     */
    private T getHelper(T data, AVLNode<T> node, boolean getMethod) {
        int compare = data.compareTo(node.getData());
        if (compare == 0) {
            return node.getData();
        } else if (compare > 0) {
            if (node.getRight() == null) {
                if (getMethod) {
                    throw new NoSuchElementException("Data is not in the tree");
                } else {
                    return null;
                }
            } else {
                return getHelper(data, node.getRight(), getMethod);
            }
        } else {
            if (node.getLeft() == null) {
                if (getMethod) {
                    throw new NoSuchElementException("Data is not in the tree");
                } else {
                    return null;
                }
            } else {
                return getHelper(data, node.getLeft(), getMethod);
            }
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0) {
            return false;
        }

        return (getHelper(data, root, false) != null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> preOrderList = new ArrayList<T>();
        if (size != 0) {
            preHelper(preOrderList, root);
            return preOrderList;
        } else {
            return new ArrayList<T>();
        }
    }

    /**
     * The private helper method of preorder that traverse the AVL
     *
     * @param list the list it uses to store traversal
     * @param node the current node in the recursive call
     */
    private void preHelper(ArrayList<T> list, AVLNode<T> node) {
        list.add(node.getData());
        if (node.getLeft() != null) {
            preHelper(list, node.getLeft());
        }
        if (node.getRight() != null) {
            preHelper(list, node.getRight());
        }
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> postOrderList = new ArrayList<T>();
        if (size != 0) {
            postHelper(postOrderList, root);
            return postOrderList;
        } else {
            return new ArrayList<T>();
        }
    }

    /**
     * The private helper method of the postorder method
     *
     * @param list the list that stores the traversal
     * @param node the current node in the recursive call
     */
    private void postHelper(ArrayList<T> list, AVLNode<T> node) {
        if (node.getLeft() != null) {
            postHelper(list, node.getLeft());
        }
        if (node.getRight() != null) {
            postHelper(list, node.getRight());
        }
        list.add(node.getData());
    }


    @Override
    public Set<T> threshold(T lower, T upper) {
        if (lower == null) {
            throw new IllegalArgumentException("Lower is null");
        } else if (upper == null) {
            throw new IllegalArgumentException("Upper is null");
        } 
        HashSet<T> set = new HashSet<>();
        pathHelper(lower, upper, root, set);
        return set;
    }

    /**
     * The private helper method for the threshold method, similar to the
     * find path but with NEW TECHNOLOGY.
     *
     * @param lower the lower bound of the Set
     * @param upper the upper bound of the Set
     * @param node the node in current traversal
     * @param set the set of the elements within the bounds(exclusive)
     */
    private void pathHelper(T lower, T upper,
                            AVLNode<T> node, HashSet<T> set) {
        if (node == null) {
            return;
        }

        int lowerCom = lower.compareTo(node.getData());
        int upperCom = upper.compareTo(node.getData());
        if (lowerCom > 0 && upperCom > 0) {
            pathHelper(lower, upper, node.getRight(), set);
        } else if (lowerCom < 0 && upperCom < 0) {
            pathHelper(lower, upper, node.getLeft(), set);
        } else if (lowerCom < 0 && upperCom > 0) {
            set.add(node.getData());
            pathHelper(lower, upper, node.getLeft(), set);
            pathHelper(lower, upper, node.getRight(), set);
        } else if (lowerCom <= 0 && upperCom > 0) {
            pathHelper(lower, upper, node.getRight(), set);
        } else if (lowerCom < 0 && upperCom >= 0) {
            pathHelper(lower, upper, node.getLeft(), set);
        }
    }
    
    @Override
    public List<T> levelorder() {
        if (size == 0) {
            return new ArrayList<>();
        } else {
            ArrayList<T> list = new ArrayList<>();
            if (root != null) {
                Queue<AVLNode<T>> queue = new LinkedList<>();
                queue.add(root);
                while (!queue.isEmpty()) {
                    AVLNode<T> node = queue.remove();
                    list.add(node.getData());
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                    }
                }
            }
            return list;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
