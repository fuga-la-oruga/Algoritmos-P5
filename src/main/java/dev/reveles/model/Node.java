package dev.reveles.model;

public class Node<T>
{
    private T info;
    private Node<T> next;
    private Node<T> prev;

    // ------------------------------
    public Node(T info)
    {
        this.info = info;
        this.next = this;
        this.prev = this;
    }
    // ------------------------------

    public T getInfo()
    {
        return info;
    }

    public void setInfo(T info)
    {
        this.info = info;
    }

    public Node<T> getNext()
    {
        return next;
    }

    public void setNext(Node<T> next)
    {
        this.next = next;
    }

    public Node<T> getPrev()
    {
        return prev;
    }

    public void setPrev(Node<T> prev)
    {
        this.prev = prev;
    }
}