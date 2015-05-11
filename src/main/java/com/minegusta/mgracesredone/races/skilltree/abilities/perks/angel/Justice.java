package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.Race;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class Justice implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        //level
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        //Getting the launch speed
        double speed = 2.5 + level / 3;
        boolean explode,push;

        explode = level > 3;
        push = level > 1;


        //Push
        if(push)
        {
            final Location l = player.getLocation();
            for(Entity ent : player.getNearbyEntities(7, 2, 7))
            {
                if(ent instanceof LivingEntity)
                {
                    double x = ent.getLocation().getX() - l.getX();
                    double y = ent.getLocation().getY() - l.getY();
                    double z = ent.getLocation().getZ() - l.getZ();

                    Vector v = new Vector(x, y, z);
                    v.normalize();

                    ent.setVelocity(ent.getVelocity().add(v.multiply(-1.6)));
                }
            }
        }

        //Explode
        if(explode)
        {
            final Location l = player.getLocation();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 4, false, false);
                }
            }, 15);
        }

        //Launch the player
        player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));

    }

    @Override
    public String getName() {
        return "Justice";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.JUSTICE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup()
    {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 90;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ANGEL);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"To activate justice, hit the ground under you.", "You will be launched into the air."};
                break;
            case 2:
                desc = new String[]{"When activating justice, you push back enemies."};
                break;
            case 3:
                desc = new String[]{"Justice launches you 50% faster."};
                break;
            case 4:
                desc = new String[]{"An explosion is caused when activating Justice."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}