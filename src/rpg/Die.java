package rpg;

import java.util.Random;

public class Die
{
    private final int numberOfFaces;
    private final Random randomGenerator;

    public Die(int numberOfFaces)
    {
        this.numberOfFaces = numberOfFaces;
        this.randomGenerator = new Random(System.currentTimeMillis());
    }

    public int roll()
    {
        return Math.abs(this.randomGenerator.nextInt() % this.numberOfFaces) + 1;
    }
}
