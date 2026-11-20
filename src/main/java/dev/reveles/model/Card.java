package dev.reveles.model;

public class Card
{
    private String name;
    private String attribute;
    private int level;
    private String type;
    private int attack;
    private int defense;
    private String description;
    private String imageUrl;

    // ------------------------------

    public Card
    (
        String name,
        String attribute,
        int level,
        String type,
        int attack,
        int defense,
        String description,
        String imageUrl
    )
    {
        this.name = name;
        this.attribute = attribute;
        this.level = level;
        this.type = type;
        this.attack = attack;
        this.defense = defense;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // ------------------------------

    public String getName()
    {
        return name;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public int getLevel()
    {
        return level;
    }

    public String getType()
    {
        return type;
    }

    public int getAttack()
    {
        return attack;
    }

    public int getDefense()
    {
        return defense;
    }

    public String getDescription()
    {
        return description;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
}
