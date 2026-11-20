package dev.reveles.model;

public class CircularList<T>
{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // ------------------------------
    public CircularList()
    {
        this.head = null;
        this.tail = null;
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

    public Node<T> getHead()
    {
        return head;
    }

    public void insertAtStart(T info)
    {
        Node<T> newNode = new Node<>(info);
        if(isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setNext(head);
            newNode.setPrev(tail);
            head.setPrev(newNode);
            tail.setNext(newNode);
            head = newNode;
        }
        size ++;
    }

    public void insertAtEnd(T info)
    {
        Node<T> newNode = new Node<>(info);
        if(isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setPrev(tail);
            newNode.setNext(head);
            tail.setNext(newNode);
            head.setPrev(newNode);
            tail = newNode;
        }
        size ++;
    }

    public T deleteAtStart()
    {
        if(isEmpty())
        {
            return null;
        }

        T removedInfo = head.getInfo();

        if(size == 1)
        {
            head = null;
            tail = null;
        }
        else
        {
            head = head.getNext();
            head.setPrev(tail);
            tail.setNext(head);
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

        T removedInfo = tail.getInfo();

        if(size == 1)
        {
            head = null;
            tail = null;
        }
        else
        {
            tail = tail.getPrev();
            tail.setNext(head);
            head.setPrev(tail);
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

        Node<T> current = head;
        for(int i = 0; i < index; i ++)
        {
            current = current.getNext();
        }

        return current.getInfo();
    }
}