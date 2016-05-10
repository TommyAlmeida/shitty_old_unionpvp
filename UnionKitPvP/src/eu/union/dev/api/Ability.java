package eu.union.dev.api;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.Player;

public class Ability
{
    public static class Status
    {
        private int charges;
        private long cooldown;
        private boolean recharged;

        public Status(int charges)
        {
            this.charges = charges;
            this.cooldown = 0L;
            this.recharged = true;
        }

        public int getCharges()
        {
            return this.charges;
        }

        public void setCharges(int charges)
        {
            this.charges = charges;
        }

        public boolean isRecharged()
        {
            return this.recharged;
        }

        public void setRecharged(boolean recharged)
        {
            this.recharged = recharged;
        }

        public void setCooldown(long delay, TimeUnit unit)
        {
            this.cooldown = (System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delay, unit));
        }

        public boolean isExpired()
        {
            long rem = getRemainingTime(TimeUnit.MILLISECONDS);
            return rem < 0L;
        }

        public long getRemainingTime(TimeUnit unit)
        {
            return unit.convert(this.cooldown - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    private Map<String, Status> playerStatus = new HashMap();
    private final int defaultCharges;
    private final long defaultDelay;

    public Ability(int defaultCharges)
    {
        this(defaultCharges, 5, TimeUnit.SECONDS);
    }

    public Ability(int defaultCharges, int defaultDelay, TimeUnit unit)
    {
        this.defaultCharges = defaultCharges;
        this.defaultDelay = TimeUnit.MILLISECONDS.convert(defaultDelay, unit);
    }

    public Status getStatus(Player player)
    {
        Status status = (Status)this.playerStatus.get(player.getName());
        if (status == null)
        {
            status = createStatus(player);
            this.playerStatus.put(player.getName(), status);
        }
        else
        {
            checkStatus(player, status);
        }
        return status;
    }

    public boolean tryUse(Player player)
    {
        return tryUse(player, 1, this.defaultDelay, TimeUnit.MILLISECONDS);
    }

    public boolean tryUse(Player player, long delay, TimeUnit unit)
    {
        return tryUse(player, delay, unit);
    }

    public boolean tryUse(Player player, int charges, long delay, TimeUnit unit)
    {
        Status status = getStatus(player);
        int current = status.getCharges();
        if (!status.isExpired()) {
            return false;
        }
        if (current <= charges)
        {
            status.setRecharged(false);
            status.setCharges(0);
            status.setCooldown(delay, unit);
        }
        else
        {
            status.setCharges(current - charges);
        }
        return current > 0;
    }

    private void checkStatus(Player player, Status status)
    {
        if ((!status.isRecharged()) && (status.isExpired())) {
            rechargeStatus(player, status);
        }
    }

    protected Status rechargeStatus(Player player, Status status)
    {
        status.setRecharged(true);
        status.setCharges(this.defaultCharges);
        return status;
    }

    protected Status createStatus(Player player)
    {
        return new Status(this.defaultCharges);
    }
}