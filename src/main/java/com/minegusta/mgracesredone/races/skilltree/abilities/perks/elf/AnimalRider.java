package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.listeners.racelisteners.ElfListener;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;

public class AnimalRider implements IAbility {
    @Override
    public void run(Event event) {
        PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;

        Entity clicked = e.getRightClicked();
        MGPlayer mgp = Races.getMGPlayer(e.getPlayer());

        int level = mgp.getAbilityLevel(AbilityType.ANIMALRIDER);

        if (level > 1 || clicked instanceof Animals) {
            if (e.getRightClicked().getType() == EntityType.ENDERMAN || (e.getRightClicked().getType() == EntityType.WOLF)) {
                if (e.getRightClicked().getType() == EntityType.WOLF) {
                    Wolf wolf = (Wolf) e.getRightClicked();
                    if (wolf.getOwner() != null && !wolf.getOwner().getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
                        ChatUtil.sendString(e.getPlayer(), "You cannot ride wolves you do not own.");
                        return;
                    }
                } else {
                    ChatUtil.sendString(e.getPlayer(), "You cannot ride endermen because of teleportation reasons.");
                    return;
                }
            }
            e.getRightClicked().setPassenger(e.getPlayer());
            ElfListener.riders.put(e.getPlayer().getUniqueId().toString(), (LivingEntity) e.getRightClicked());
        }
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Animal Rider";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ANIMALRIDER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.MONSTER_EGG;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Allows you to ride animals."};
                break;
            case 2:
                desc = new String[]{"Allows you to ride hostile mobs."};
                break;
            default:
                desc = new String[]{"ERROR BEEP BEEP", "REPORT TO JAN"};
                break;
        }
        return desc;
    }
}
