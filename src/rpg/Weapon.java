package rpg;

public class Weapon
{
    private final int weaponDamage;
    private final String weaponName;
    private final int weaponCost;
    private final int weaponSpeed;
    private boolean isPurchased;

    public Weapon(String weaponName, int weaponCost, int weaponDamage, int weaponSpeed)
    {
        this.weaponName = weaponName;
        this.weaponDamage = weaponDamage;
        this.weaponCost = weaponCost;
        this.weaponSpeed = weaponSpeed;

        this.isPurchased = false;
    }

    public boolean isPurchased()
    {
        return this.isPurchased;
    }

    public void isPurchased(boolean purchased)
    {
        isPurchased = purchased;
    }

    public int getWeaponDamage()
    {
        return weaponDamage;
    }

    public String getWeaponName()
    {
        return weaponName;
    }

    public int getWeaponCost()
    {
        return weaponCost;
    }

    public int getWeaponSpeed()
    {
        return weaponSpeed;
    }
}
