package rpg;

import java.util.HashMap;
import java.util.Map;

public class Path
{
    private String pathScript;
    public static final int TOTAL_PATHS = 100;
    private Runnable runnable;

    public Runnable getRunnable()
    {
        return this.runnable;
    }

    public void setRunnable(Runnable runnable)
    {
        this.runnable = runnable;
    }

    public String getPathScript()
    {
        return this.pathScript;
    }

    public void setPathScript(String pathScript)
    {
        this.pathScript = pathScript;
    }

}
