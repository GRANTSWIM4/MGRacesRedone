package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Nocturnal implements IAbility {


    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Nocturnal";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.NOCTURNAL;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.TORCH;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.WEREWOLF);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"At night, you gain a speed boost."};
                break;
            case 2:
                desc = new String[]{"At night, you gain night vision."};
                break;
            case 3:
                desc = new String[]{"At night you have a jump boost."};
                break;
            case 4:
                desc = new String[]{"At night you gain a strength boost."};
                break;
            case 5:
                desc = new String[]{"You no longer take fall damage at night"};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}