package dev.reveles.model;

public class Card
{
    private String name;
    private String attribute;
    private int level;
    private String type;
    private int atk;
    private int def;
    private String rarity;
    private String description;
    private String id;

    // ------------------------------
    public Card
    (
        String name,
        String attribute,
        int level,
        String type,
        int atk,
        int def,
        String rarity,
        String description,
        String id
    )
    {
        this.name = name;
        this.attribute = attribute;
        this.level = level;
        this.type = type;
        this.atk = atk;
        this.def = def;
        this.rarity = rarity;
        this.description = description;
        this.id = id;
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

    public int getAtk()
    {
        return atk;
    }

    public int getDef()
    {
        return def;
    }

    public String getRarity()
    {
        return rarity;
    }

    public String getDescription()
    {
        return description;
    }

    public String getId()
    {
        return id;
    }

    public String getImageUrl()
    {
        return "https://images.ygoprodeck.com/images/cards/" + id + ".jpg";
    }
}