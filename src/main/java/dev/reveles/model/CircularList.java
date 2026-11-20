package dev.reveles.model;

public class CircularList<T>
{
    private Node<T> first;
    private Node<T> last;
    private int size;

    // ------------------------------

    public CircularList()
    {
        first = null;
        last = null;
        size = 0;
    }

    // ------------------------------

    public void add(T data)
    {
        Node<T> newNode = new Node<>(data);

        if (first == null)
        {
            first = newNode;
            last = newNode;

            first.next = first;
            first.previous = first;
        }
        else
        {
            newNode.previous = last;
            newNode.next = first;

            last.next = newNode;
            first.previous = newNode;

            last = newNode;
        }

        size ++;
    }

    public T get(int index)
    {
        if (index < 0 || index >= size)
            return null;

        Node<T> current;

        if (index < size / 2)
        {
            current = first;

            for (int i = 0; i < index; i++)
                current = current.next;
        }
        else
        {
            current = last;

            for (int i = size - 1; i > index; i--)
                current = current.previous;
        }

        return current.data;
    }

    public T getNext(int index)
    {
        if (isEmpty())
            return null;

        return get((index + 1) % size);
    }

    public T getPrevious(int index)
    {
        if (isEmpty())
            return null;

        return get((index - 1 + size) % size);
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}