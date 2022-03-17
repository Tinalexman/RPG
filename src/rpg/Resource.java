package rpg;

import java.util.Arrays;

public class Resource
{
    public static final String[] hits = {"hits", "bashes", "smites", "whacks", "shreds", "mutilates", "lacerates", "annihilates"};

    public static final String[] misses = {"misses", "nearly hits", "fails to connect with", "swipes wildly at",
            "flails ineffectively at", "gets nowhere near", "nearly decapitated self instead of",
            "hits self on foot, to the amusement of"};

    public static final String[] damageReport = {"small insult", "flesh wound", "deep slash", "ragged gash", "savage laceration",
        "fractured rib-cage", "smashed-up face", "split skull"};

    public static final String[] lifeChanging = {"a scar.", "bruising.", "serious blood-loss.", "total debilitation.",
        "chronic concussion.", "a severed limb.", "multiple fractures.", "an amputated head"};

    public static final String[] botNames = {"Ranak The Goblin", "Milsa The Mage", "Gelf The Troll", "Adwin The Elf", "Wallie The Pixie"};

    public static final Race[] botRaces = {Race.GOBLIN, Race.MAGE, Race.TROLL, Race.ELF, Race.PIXIE};

    public static Path[] paths = new Path[Path.TOTAL_PATHS];


    public static void createPaths()
    {
        Player hero = Game.getHero();

        for(int position = 0; position < Path.TOTAL_PATHS; ++position)
            paths[position] = new Path();


        paths[0].setPathScript("Welcome, " + hero.getName() + "!");
        paths[0].setRunnable(() ->
        {
            Controller.optionTwo.setVisible(false);
            Controller.optionThree.setVisible(false);
            Controller.optionOne.setText("Proceed, " + hero.getName());
            Controller.optionOne.setOnMouseClicked(event ->
            {
                Game.CURRENT_PATH_INDEX = 1;
                Controller.loadPath();
            });
            Controller.messageBox.setText(paths[0].getPathScript());
        });

        paths[1].setPathScript("Hello Again");
        paths[1].setRunnable(() ->
        {
            Controller.optionOne.setVisible(false);
            Controller.optionTwo.setVisible(true);
            Controller.optionThree.setVisible(false);
            Controller.optionTwo.setOnMouseClicked(event ->
            {
                Game.CURRENT_PATH_INDEX = 2;
                Controller.loadPath();
            });
            Controller.messageBox.setText(paths[1].getPathScript());
        });

    }




}
