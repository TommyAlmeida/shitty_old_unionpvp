package eu.union.dev.engine.handlers;

import org.bukkit.entity.Player;

import java.util.*;

/**
 * EOS'S NetworkEXP
 */
public class ExpHandler {

    private int intitalexp;
    private int starterlevel;

    private int baseCurve;
    private int difficulty;

    private int maximumlevelCapacity;

    private Map<UUID, Integer> users = new HashMap<>();
    private ArrayList<Integer> levelcurve = new ArrayList<>();


    public ExpHandler()
    {
        intitalexp = 0;
        starterlevel = 0;
        baseCurve = 30;
        difficulty = 5;
        maximumlevelCapacity = 101;
    }

    /**
     * Retrieve exp from a specific player!
     * </br>
     * This function is designed for API usage.
     *
     * @param uuid The player you're wanting to grab the exp from.
     */
    public Integer getExp(UUID uuid) {
        Integer value = users.get(uuid);
        int expAmount = 0;

        if ( value != null ) {
            expAmount = value;
        } else {
            if ( users.containsKey(uuid) && users.containsValue(uuid) ) {
                expAmount = value;
            } else {
                users.put(uuid, intitalexp);
                expAmount = intitalexp;
                return expAmount;
            }
        }

        return expAmount;

    }

    /**
     * Add a X amount of exp to the player!
     * </br>
     * This function is designed for API usage.
     *
     * @param amount The amount of exp to give to player.
     */

    public void addExp(UUID uuid, int amount) {

        users.computeIfPresent(uuid, (num, val) -> val + amount);

        users.computeIfAbsent(uuid, num -> intitalexp);

    }

    /**
     * Set a X amount of Exp to a specific player!
     * </br>
     * This function is designed for API usage.
     *
     * @param amount The amount of exp you're wanting to set to the player!.
     */

    public void setExp(UUID uuid, int amount) {
        users.computeIfPresent(uuid, (num, val) -> amount);

        users.computeIfAbsent(uuid, num -> intitalexp);
    }

    /**
     * Subtract a X amount from a specific player!
     * </br>
     * This function is designed for API usage.
     *
     * @param amount The amount of exp you're wanting to subtract from the player!.
     */
    public void subtractExp(UUID uuid, int amount) {
        Integer value = users.get(uuid);

        if ( value != null ) {
            if (users.get(uuid) <= 0) {

                users.put(uuid, intitalexp);

            } else {
                users.computeIfPresent(uuid, (num, val) -> getExp(uuid) - amount);

                users.computeIfAbsent(uuid, num -> intitalexp);
            }
        }

    }

    /**
     * Grab the amount of exp till the next level
     * </br>
     * This function is designed for API usage.
     *
     */
    public Integer getneededXP(UUID uuid)
    {
        int level = getLevel(uuid);

        if ( getLevel(uuid) == maximumlevelCapacity - 1 )
        {
            return 0;
        } else {
            int formula = levelcurve.get(level + 1) - getExp(uuid);

            return formula;
        }
    }

    /**
     * Loads experience for a specific user!
     * </br>
     * This function is designed for Config Usage
     *
     * @param key The players unique ID
     * @param value The amount of exp
     */
    public void loadExp(UUID key, int value)
    {
        users.put(key, value);
    }

    /**
     * Level Manager
     */
    /**
     * Generates the level Stricture!
     * </br>
     * This function is designed for API usage.
     *
     */
    public void initializeLevels() {
        for ( int i = starterlevel; i < maximumlevelCapacity - 1; i++ ) {

            int formula = i * difficulty;

            levelcurve.add(baseCurve * formula);
        }
    }


    /**
     * Retrieve the specific player's level!
     * </br>
     * This function is designed for API usage.
     *
     */

    public Integer getLevel(UUID uuid) {
        int exp = getExp(uuid);

        int returnValue = starterlevel;

        for ( int i = starterlevel; i < maximumlevelCapacity - 1; i++) {

            int formula = (maximumlevelCapacity - 1) * difficulty;

            if (getExp(uuid) >= baseCurve * formula  ) {

                setExp(uuid, baseCurve * formula);

                return maximumlevelCapacity - 1;
            }
            if ( exp < levelcurve.get(i) && exp >= levelcurve.get(i - 1)  ) {
                returnValue=(i - 1);
                return returnValue;
            }

        }

        return returnValue;

    }

    /**
     * Set a player to a specific level
     * </br>
     * This function is designed for API usage.
     *
     * @param level The level you would like to set the player to!
     */

    public void setLevel(UUID uuid, int level) {
        int exp = getExp(uuid);
        int needed = getneededXP(uuid);


        if ( getLevel(uuid) == maximumlevelCapacity - 1 )
        {
            // Do whatever
        } else {

            setExp(uuid, needed * level);

        }
    }
}