package dev.reveles.model;

public class CircularList<T>
{
    private Node<T> start;
    private Node<T> end;
    private int size;

    // ------------------------------
    public CircularList()
    {
        this.start = null;
        this.end = null;
        this.size = 0;
    }
    // ------------------------------

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }

    public Node<T> getStart()
    {
        return start;
    }

    public void insertAtStart(T info)
    {
        Node<T> newNode = new Node<>(info);
        if(isEmpty())
        {
            start = newNode;
            end = newNode;
        }
        else
        {
            newNode.setNext(start);
            newNode.setPrev(end);
            start.setPrev(newNode);
            end.setNext(newNode);
            start = newNode;
        }
        size ++;
    }

    public void insertAtEnd(T info)
    {
        Node<T> newNode = new Node<>(info);
        if(isEmpty())
        {
            start = newNode;
            end = newNode;
        }
        else
        {
            newNode.setPrev(end);
            newNode.setNext(start);
            end.setNext(newNode);
            start.setPrev(newNode);
            end = newNode;
        }
        size ++;
    }

    public T deleteAtStart()
    {
        if(isEmpty())
        {
            return null;
        }

        T removedInfo = start.getInfo();

        if(size == 1)
        {
            start = null;
            end = null;
        }
        else
        {
            start = start.getNext();
            start.setPrev(end);
            end.setNext(start);
        }
        size --;
        return removedInfo;
    }

    public T deleteAtEnd()
    {
        if(isEmpty())
        {
            return null;
        }

        T removedInfo = end.getInfo();

        if(size == 1)
        {
            start = null;
            end = null;
        }
        else
        {
            end = end.getPrev();
            end.setNext(start);
            start.setPrev(end);
        }
        size --;
        return removedInfo;
    }

    public T get(int index)
    {
        if(isEmpty() || index < 0 || index >= size)
        {
            return null;
        }

        Node<T> current = start;
        for(int i = 0; i < index; i ++)
        {
            current = current.getNext();
        }

        return current.getInfo();
    }
}