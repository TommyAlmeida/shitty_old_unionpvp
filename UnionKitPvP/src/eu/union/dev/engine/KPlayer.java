package eu.union.dev.engine;

import java.util.UUID;

public class KPlayer {

    private UUID uuid;
    private int deaths,kills,level,kdr;
    private long coins;

    public KPlayer(UUID uuid, int kills, int deaths, long coins, int level, int kdr){
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.level = level;
        this.kdr = kdr;
        this.coins = coins;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int level){
        this.level += level;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public void addCoins(long coins){
        this.coins += coins;
    }

    public int getKDR(){
        kdr = kills / deaths;
        return kdr;
    }
}
