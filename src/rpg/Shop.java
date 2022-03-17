package rpg;

import java.util.*;

public class Shop
{

    private static final List<Weapon> weapons = new ArrayList<>();
    private static final Set<Weapon> armorTypes = new HashSet<>();

    public static int WEAPON_INDEX = 0;

    public static void init()
    {
        Weapon dagger = new Weapon("Dagger", 60, 60, 40);
        Weapon halberd = new Weapon("Halberd", 25, 20, 50);
        Weapon club = new Weapon("Club", 15, 30, 30);
        Weapon flail = new Weapon("Flail", 50, 70, 45);
        Weapon hammer = new Weapon("Hammer", 90, 100, 20);
        Weapon cuirass = new Weapon("Cuirass", 30, 45, 20);
        Weapon shield = new Weapon("Shield", 15, 20, 50);
        Weapon armor = new Weapon("Armor", 101, 100, 0);
        Weapon lantern = new Weapon("Lantern", 10, 5, 30);
        Weapon pole = new Weapon("Pole", 10, 5, 50);
        Weapon rope = new Weapon("Rope", 10, 5, 70);

        Collections.addAll(weapons, dagger, halberd, cuirass, club, flail, hammer, shield, lantern, pole, rope, armor);
        Collections.addAll(armorTypes, shield, cuirass, armor);
    }

    public static List<Weapon> getWeapons()
    {
        return weapons;
    }

    public static Set<Weapon> getArmorTypes()
    {
        return armorTypes;
    }

    public static int getNumberOfWeapons()
    {
        return weapons.size();
    }

    public static Weapon getWeapon(int index)
    {
        return weapons.get(index);
    }
}
