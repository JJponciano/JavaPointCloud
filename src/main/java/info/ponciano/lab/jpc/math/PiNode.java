package info.ponciano.lab.jpc.math;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jean-Jacques Ponciano
 *
 */
public class PiNode<T extends Splitable> {

    protected T data;

    protected final List<PiNode<T>> children;

    protected PiNode<T> parent = null;

    public PiNode(T data) {
        this.children = new ArrayList<>();
        this.data = data;
    }

    public PiNode<T> addChild(PiNode<T> child) {
            if (child == null) {
            throw new InternalError("child is null");
        }
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<PiNode<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<PiNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(PiNode<T> parent) {
        this.parent = parent;
    }

    public PiNode<T> getParent() {
        return parent;
    }

    /**
     * Finding the root of tree from any node
     *
     * @return the root
     */
    public PiNode getRoot() {
        if (parent == null) {
            return this;
        }
        return parent.getRoot();
    }

    /**
     * delete the node and assigns the children of that node to be the parent of
     * the deleted node.
     */
    public void deleteNode() {
        if (parent != null) {
            int index = this.parent.getChildren().indexOf(this);
            this.parent.getChildren().remove(this);
            for (PiNode<T> each : getChildren()) {
                each.setParent(this.parent);
            }
            this.parent.getChildren().addAll(index, this.getChildren());
        } else {
            deleteRootNode();
        }
        this.getChildren().clear();
    }

    /**
     * Deletes the root node and assigns the first children as the new root.
     *
     * @return the new root node.
     */
    public PiNode<T> deleteRootNode() {
        if (parent != null) {
            throw new IllegalStateException("deleteRootNode not called on root");
        }
        PiNode<T> newParent = null;
        if (!getChildren().isEmpty()) {
            newParent = getChildren().get(0);
            newParent.setParent(null);
            getChildren().remove(0);
            for (PiNode<T> each : getChildren()) {
                each.setParent(newParent);
            }
            newParent.getChildren().addAll(getChildren());
        }
        this.getChildren().clear();
        return newParent;
    }
}
