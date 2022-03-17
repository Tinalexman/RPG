package rpg;

import java.util.*;

public class Game
{
    private static Player hero;
    private static final Die firstDie = new Die(6), secondDie = new Die(6);
    private static final Random randomGenerator = new Random(System.currentTimeMillis());

    public static int CURRENT_PATH_INDEX = 0;

    public static boolean isRunning()
    {
        return hero != null;
    }

    public static int rollDice()
    {
        return (firstDie.roll() * 10) + secondDie.roll();
    }

    public static void setHero(Player player)
    {
        if(player != null)
        {
            int muscle = getRandomWithLimits(3, 33) + getRandomWithLimits(3, 33) +
                    getRandomWithLimits(3, 33);
            player.setMuscle(muscle);

            int brains = getRandomWithLimits(3, 33) + getRandomWithLimits(3, 33) +
                    getRandomWithLimits(3, 33);
            player.setBrains(brains);

            int speed = getRandomWithLimits(3, 33) + getRandomWithLimits(3, 33) +
                    getRandomWithLimits(3, 33);
            player.setSpeed(speed);

            int charm = getRandomWithLimits(3, 33) + getRandomWithLimits(3, 33) +
                    getRandomWithLimits(3, 33);
            player.setCharm(charm);

            int life = (muscle + (speed / 2) + getRandomWithLimits(9, 49)) / 2;
            player.setHealth(life);

            int magic = (brains + (charm / 2) + getRandomWithLimits(9, 49)) / 2;
            player.setMagic(magic);

            int gold = getRandomWithLimits(9, 49) + getRandomWithLimits(9, 49) +
                    getRandomWithLimits(9, 49);
            player.setGold(gold);

            int resistance = (speed + (brains / 2) + getRandomWithLimits(9, 49)) / 2;
            player.setResistance(resistance);
        }
        hero = player;
    }

    private static int getRandomWithLimits(int lowerLimit, int upperLimit)
    {
        int returnValue = Math.abs(randomGenerator.nextInt() % upperLimit) + 1;
        if(returnValue < lowerLimit)
            returnValue = lowerLimit;
        return returnValue;
    }

    public static Player getHero()
    {
        return hero;
    }

//    public static void createBot()
//    {
//        int randomRace = getRandomWithLimits(0, Resource.botNames.length - 1);
//        String botName = Resource.botNames[randomRace];
//        Player bot = new Player(botName);
//        setPlayer(bot, 1);
//        buyBotWeapon(bot);
//    }

    private static Collection<Weapon> getComplement(Collection<Weapon> parentList, Collection<Weapon> childList)
    {
        List<Weapon> complement = new ArrayList<>();
        for(Weapon weapon : parentList)
        {
            if(!childList.contains(weapon))
                complement.add(weapon);
        }
        return complement;
    }

//    private static void buyBotWeapon(Player bot)
//    {
//        int botGold = bot.getGold();
//        Set<Weapon> defenseWeapons = Shop.getArmorTypes();
//        List<Weapon> attackWeapons = (List<Weapon>) getComplement(Shop.getWeapons(), defenseWeapons);
//
//        int weaponFactor = 0;
//        Weapon weaponToBePurchased = null;
//
//       for(Weapon weapon : attackWeapons)
//       {
//           if(weapon.getWeaponCost() <= botGold)
//           {
//               int newFactor = weapon.getWeaponSpeed() * weapon.getWeaponDamage();
//               if(newFactor > weaponFactor)
//               {
//                   weaponFactor = newFactor;
//                   weaponToBePurchased = weapon;
//               }
//           }
//       }
//
//       if(weaponToBePurchased != null)
//       {
//           bot.setAttackWeapon(weaponToBePurchased);
//           bot.setGold(botGold - weaponToBePurchased.getWeaponCost());
//           weaponToBePurchased.isPurchased(true);
//       }
//
//       weaponFactor = 0;
//       weaponToBePurchased = null;
//       botGold = bot.getGold();
//
//       for(Weapon weapon : defenseWeapons)
//       {
//           if(weapon.getWeaponCost() <= botGold)
//           {
//               int newFactor = weapon.getWeaponSpeed() * weapon.getWeaponDamage();
//               if(newFactor > weaponFactor)
//               {
//                   weaponFactor = newFactor;
//                   weaponToBePurchased = weapon;
//               }
//           }
//       }
//
//        if(weaponToBePurchased != null)
//        {
//            bot.setDefenseWeapon(weaponToBePurchased);
//            bot.setGold(botGold - weaponToBePurchased.getWeaponCost());
//            weaponToBePurchased.isPurchased(true);
//        }
//
//    }

    public static String calculateDamage(Player attacker, Player defender)
    {
        int MAX_VELOCITY = 23, MIN_VELOCITY = 1, MAX_DAMAGE = 23;
        StringBuilder report = new StringBuilder();
        if(attacker.getHealth() > 0 && defender.getHealth() > 0)
        {
            int attackSpeed = attacker.getSpeed();
            int attackWeaponSpeed = attacker.getAttackWeapon().getWeaponSpeed();
            int attackChance = getRandomWithLimits(1, attacker.getBrains());
            int attackVelocity = attackSpeed + attackWeaponSpeed + attackChance;

            int resistance = defender.getResistance();
            int defenseWeaponSpeed = defender.getDefenseWeapon().getWeaponSpeed();
            int defendVelocity = resistance + defenseWeaponSpeed;

            int velocity = (attackVelocity - defendVelocity) / 2;
            if(velocity > 0)
            {
                if(velocity > MAX_VELOCITY)
                    MAX_VELOCITY = velocity;
                int hitType = (7 * velocity / MAX_VELOCITY);
                if(hitType > 7)
                    hitType = 7;
                report.append(attacker.getName()).append(" ").append(Resource.hits[hitType])
                        .append(" ").append(defender.getName());
            }
            else
            {
                if(velocity < MIN_VELOCITY)
                    MIN_VELOCITY = velocity;
                int missType = (velocity / MAX_VELOCITY);
                if(missType > 7)
                    missType = 7;
                report.append(attacker.getName()).append(" ").append(Resource.misses[missType])
                        .append(" ").append(defender.getName());
            }

            int attackStrength = attacker.getMuscle();
            int attackWeaponDamage = attacker.getAttackWeapon().getWeaponDamage();
            int attackDamage = attackStrength + attackWeaponDamage;

            int defenseStrength = defender.getMuscle();
            int defenseWeaponDamage = defender.getDefenseWeapon().getWeaponDamage();
            int defenseChance = getRandomWithLimits(9, defender.getBrains());
            int defenseResistance = defenseStrength + defenseWeaponDamage + defenseChance;

            int potentialDamage = attackDamage - defenseResistance;
            if(potentialDamage < 1)
                potentialDamage = 2;

            int damage = getRandomWithLimits(1, potentialDamage);
            if(damage > MAX_DAMAGE)
                damage = MAX_DAMAGE;

            int damageType = (7 * damage / MAX_DAMAGE);
            if(damageType > 7)
                damageType = 7;

            int changeType = (5 * damage / defender.getHealth());
            if(changeType > 7)
                changeType = 7;

            report.append(", inflicting a ").append(Resource.damageReport[damageType]).append(" and ");
            report.append(Resource.lifeChanging[changeType]);

            defender.setHealth(defender.getHealth() - damage);
            if(defender.getHealth() <= 0)
            {
                report.append(" with ").append(defender.getName()).append(" collapsing in a pool of blood.");
                report.append(attacker.getName()).append(" wins the fight!");
            }
        }
        return report.toString();
    }

}
