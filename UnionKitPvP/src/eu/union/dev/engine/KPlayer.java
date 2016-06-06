package eu.union.dev.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Level formula: L = (25 + sqrt(25 * 25 - 4 * 25 * (-X) ))/ (2 * 25)
 * <p>
 * TODO: Transform level formula to actual exp system
 */
public class KPlayer {

    private UUID uuid;
    private int deaths, kills, level, kdr, current_exp;
    private long coins;

    private int baseCurve;
    private int difficulty;
    private int starterlevel = 0;

    private int maximumlevelCapacity;
    private ArrayList<Integer> levelcurve = new ArrayList<>();
    public HashMap<UUID, Integer> killstreak = new HashMap<>();


    public KPlayer(UUID uuid, int kills, int deaths, long coins, int level, int kdr, int current_exp) {
        this.uuid = uuid;
        this.current_exp = current_exp;
        this.kills = kills;
        this.deaths = deaths;
        this.level = level;
        this.kdr = kdr;
        this.coins = coins;
        this.baseCurve = 40;
        this.difficulty = 8;
        this.maximumlevelCapacity = 101; // = 1-100 YOU MUST ADD A EXTRA 1 for example 81 will give you 80

        //Initialize the level structure
        for(int i = starterlevel; i < this.maximumlevelCapacity - 1; i++){
            int formula = i * difficulty;
            levelcurve.add(baseCurve * formula);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Retrieve the player deaths
     * @return
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Set a new value to player deaths
     * @param deaths
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * Add a new value to player deaths
     * @param deaths
     */
    public void addDeaths(int deaths) {
        this.deaths += deaths;
    }

    /**
     * Retrieve the player kills
     * @return
     */
    public int getKills() {
        return kills;
    }

    /**
     * Add a new value to player kills
     * @param kills
     */
    public void addKills(int kills) {
        this.kills += kills;
    }

    /**
     * Set a new value to player kills
     * @param kills
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * Retrieve the player level
     * @return
     */
    public int getLevel() {
        int exp = getCurrentEXP();
        int returnValue = starterlevel;

        for (int i = 0; i < maximumlevelCapacity - 1; i++) {
            int formula = (maximumlevelCapacity - 1) * difficulty;

            if (getCurrentEXP() >= baseCurve * formula) {
                setCurrentEXP(baseCurve * formula);

                return maximumlevelCapacity - 1;
            }

            if (exp < levelcurve.get(i) && exp >= levelcurve.get(i - 1)) {
                returnValue = (i - 1);
                return returnValue;
            }
        }

        return returnValue;
    }


    public void setLevel(int level){
        int needed = getNeededXP();


        if (getLevel() == maximumlevelCapacity - 1) {
            //Something
        } else {
            setCurrentEXP(needed * level);
        }
    }

    /**
     * Add a new value to player current exp
     * @param exp
     */
    public void addCurrentEXP(int exp) {
        this.current_exp += exp;
    }

    /**
     * Set a new value to player current exp
     * @param exp
     */
    public void setCurrentEXP(int exp){
        this.current_exp = exp;
    }

    /**
     * Retrieve the player current exp
     * @return
     */
    public int getCurrentEXP(){
        return current_exp;
    }

    /**
     * Subtract(-) a value from the current exp
     * @param exp
     */
    public void subtractEXP(int exp){
        this.current_exp -= exp;
    }

    /**
     * Retrieve the exp needed to the next player level
     * @return
     */
    public int getNeededXP(){
        int level = getLevel();

        if (getLevel() == maximumlevelCapacity - 1 )
        {
            return 0;
        } else {
            int formula = levelcurve.get(level + 1) - getCurrentEXP();

            return formula;
        }
    }

    /**
     * Retrieve the player coins
     * @return
     */
    public long getCoins() {
        return coins;
    }

    /**
     * Set a new value to player coins
     * @param coins
     */
    public void setCoins(long coins) {
        this.coins = coins;
    }

    /**
     * Add a new value to player coins
     * @param coins
     */
    public void addCoins(long coins) {
        this.coins += coins;
    }

    /**
     * Remove a certain value of coins
     * @param coins
     */
    public void removeCoins(long coins){
        this.coins -= coins;
    }

    /**
     * Add a new value to player killstreak
     * @param amount
     */
    public void addKillstreak(int amount){
        killstreak.put(getUuid(), + amount);
    }

    /**
     * Set a new value to player killstreak
     * @param amount
     */
    public void setKillstreak(int amount){
        killstreak.put(getUuid(), amount);
    }


    /**
     * See if the player is in killstreak or if its not
     * @return
     */
    public boolean isInKillstreak(){
        if(killstreak.containsKey(getUuid())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Clear killstreak mode
     */
    public void clearKillstreak(){
        killstreak.clear();
    }

    /**
     * Remove a certain value of killstreak
     * @param amount
     */
    public void removeKillStreak(int amount){
        killstreak.remove(amount);
    }


    /**
     * Retrieve the player KDR, Formula: kills / deaths
     * @return
     */
    public int getKDR() {
        return deaths == 0 ? kills : kills / deaths;
    }

    /**
     * Reset all player stats to 0
     */
    public void clear() {
        deaths = 0;
        kills = 0;
        kdr = 0;
    }
}
