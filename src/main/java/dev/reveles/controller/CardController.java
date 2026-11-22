package dev.reveles.controller;

import dev.reveles.model.Card;
import dev.reveles.model.CircularList;
import dev.reveles.model.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CardController
{
    private CircularList<Card> allCards;

    // ------------------------------
    public CardController()
    {
        this.allCards = new CircularList<>();
    }
    // ------------------------------

    public void loadCardsFromCsv(String filePath)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            br.readLine();
            while((line = br.readLine()) != null)
            {
                String[] data = line.split(",");
                if(data.length >= 9)
                {
                    String name = data[0];
                    String attribute = data[1];
                    int level = 0;
                    try
                    {
                        level = Integer.parseInt(data[2]);
                    }
                    catch(NumberFormatException e)
                    {
                        level = 0;
                    }
                    String type = data[3];
                    int atk = 0;
                    try
                    {
                        atk = Integer.parseInt(data[4]);
                    }
                    catch(NumberFormatException e)
                    {
                        atk = 0;
                    }
                    int def = 0;
                    try
                    {
                        def = Integer.parseInt(data[5]);
                    }
                    catch(NumberFormatException e)
                    {
                        def = 0;
                    }
                    String rarity = data[6];
                    String description = data[7];
                    String id = data[8];

                    Card card = new Card(name, attribute, level, type, atk, def, rarity, description, id);
                    allCards.insertAtEnd(card);
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public CircularList<Card> getAllCards()
    {
        return allCards;
    }

    public CircularList<Card> getCardsByLevel(int level)
    {
        CircularList<Card> filteredList = new CircularList<>();
        if(allCards.isEmpty())
        {
            return filteredList;
        }

        Node<Card> current = allCards.getStart();
        for(int i = 0; i < allCards.getSize(); i ++)
        {
            if(current.getInfo().getLevel() == level)
            {
                filteredList.insertAtEnd(current.getInfo());
            }
            current = current.getNext();
        }
        return filteredList;
    }

    public CircularList<Card> getCardsByAttribute(String attribute)
    {
        CircularList<Card> filteredList = new CircularList<>();
        if(allCards.isEmpty())
        {
            return filteredList;
        }

        Node<Card> current = allCards.getStart();
        for(int i = 0; i < allCards.getSize(); i ++)
        {
            if(current.getInfo().getAttribute().equalsIgnoreCase(attribute))
            {
                filteredList.insertAtEnd(current.getInfo());
            }
            current = current.getNext();
        }
        return filteredList;
    }

    public CircularList<Card> getCardsSortedByAtk(boolean lowToHigh)
    {
        ArrayList<Card> tempList = new ArrayList<>();
        if(allCards.isEmpty())
        {
            return new CircularList<>();
        }

        Node<Card> current = allCards.getStart();
        for(int i = 0; i < allCards.getSize(); i ++)
        {
            tempList.add(current.getInfo());
            current = current.getNext();
        }

        if(lowToHigh)
        {
            tempList.sort((c1, c2) -> Integer.compare(c1.getAtk(), c2.getAtk()));
        }
        else
        {
            tempList.sort((c1, c2) -> Integer.compare(c2.getAtk(), c1.getAtk()));
        }

        CircularList<Card> sortedList = new CircularList<>();
        for(Card card : tempList)
        {
            sortedList.insertAtEnd(card);
        }
        return sortedList;
    }

    public CircularList<Card> getCardsSortedByDef(boolean lowToHigh)
    {
        ArrayList<Card> tempList = new ArrayList<>();
        if(allCards.isEmpty())
        {
            return new CircularList<>();
        }

        Node<Card> current = allCards.getStart();
        for(int i = 0; i < allCards.getSize(); i ++)
        {
            tempList.add(current.getInfo());
            current = current.getNext();
        }

        if(lowToHigh)
        {
            tempList.sort((c1, c2) -> Integer.compare(c1.getDef(), c2.getDef()));
        }
        else
        {
            tempList.sort((c1, c2) -> Integer.compare(c2.getDef(), c1.getDef()));
        }

        CircularList<Card> sortedList = new CircularList<>();
        for(Card card : tempList)
        {
            sortedList.insertAtEnd(card);
        }
        return sortedList;
    }
}