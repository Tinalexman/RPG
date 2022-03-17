package rpg;

public class Player
{
    public static final int MAX_PLAYERS = 2;

    private final String name;
    private Weapon defenseWeapon, attackWeapon;
    private int muscle;
    private int brains;
    private int speed;
    private int charm;
    private int gold;
    private int magic;
    private int health;
    private int resistance;

    public int getResistance()
    {
        return resistance;
    }

    public void setResistance(int resistance)
    {
        this.resistance = resistance;
    }

    public Player(String name)
    {
        this.name = name;
    }

    public Weapon getDefenseWeapon()
    {
        return defenseWeapon;
    }

    public void setDefenseWeapon(Weapon defenseWeapon)
    {
        this.defenseWeapon = defenseWeapon;
    }

    public Weapon getAttackWeapon()
    {
        return attackWeapon;
    }

    public void setAttackWeapon(Weapon attackWeapon)
    {
        this.attackWeapon = attackWeapon;
    }

    public String getName()
    {
        return name;
    }

    public int getMagic()
    {
        return magic;
    }

    public void setMagic(int magic)
    {
        this.magic = magic;
    }

    public int getMuscle()
    {
        return muscle;
    }

    public void setMuscle(int muscle)
    {
        this.muscle = muscle;
    }

    public int getBrains()
    {
        return brains;
    }

    public void setBrains(int brains)
    {
        this.brains = brains;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getCharm()
    {
        return charm;
    }

    public void setCharm(int charm)
    {
        this.charm = charm;
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = Math.min(health, 100);
    }



}
