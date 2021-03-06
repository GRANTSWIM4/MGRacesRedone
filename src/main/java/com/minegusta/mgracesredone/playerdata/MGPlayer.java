package com.minegusta.mgracesredone.playerdata;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.manager.AbilityFileManager;
import com.minegusta.mgracesredone.util.ScoreboardUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MGPlayer {

    private String uuid;
    private String name;
    private RaceType raceType;
    private FileConfiguration conf;
    private double health;
    private int perkpoints;
    private ConcurrentMap<AbilityType, Integer> abilities = Maps.newConcurrentMap();

    private void buildMGPlayer(String uuid, FileConfiguration f) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
        this.conf = f;
        this.raceType = RaceType.valueOf(conf.getString("racetype", "HUMAN"));
        this.perkpoints = conf.getInt("perkpoints", 0);
        this.health = conf.getDouble("health", getHealth());
        AbilityFileManager.loadAbilities(this);

        updateAttributes();
    }

    public MGPlayer(Player p, FileConfiguration f) {
        buildMGPlayer(p.getUniqueId().toString(), f);
    }

    public MGPlayer(UUID uuid, FileConfiguration f) {
        buildMGPlayer(uuid.toString(), f);
    }

    public MGPlayer(String uuid, FileConfiguration f) {
        buildMGPlayer(uuid, f);
    }


    //------------------------------------------------------------------------------//

    public void addAbility(AbilityType type, int level) {
        abilities.put(type, level);
    }

    public void removeAbility(AbilityType type) {
        if (abilities.containsKey(type)) ;
    }

    public void clearAbilities() {
        abilities.clear();
    }

    public boolean hasAbility(AbilityType ability) {
        return abilities.containsKey(ability);
    }

    public ConcurrentMap<AbilityType, Integer> getAbilities() {
        return abilities;
    }

    public int getAbilityLevel(AbilityType ability) {
        try {
            return abilities.get(ability);
        } catch (Exception Ignored) {
            return 0;
        }
    }

    public int getPerkPoints() {
        return perkpoints;
    }

    public void setPerkPoints(int newPoints) {
        this.perkpoints = newPoints;
        if (perkpoints > 1000) perkpoints = 1000;
    }

    public void addPerkPoints(int added) {
        perkpoints = perkpoints + added;
        if (perkpoints > 1000) perkpoints = 1000;
    }

    //Only removes points when you can afford it (wont drop below 0).
    public boolean removePerkPoints(int removed) {
        if (perkpoints - removed >= 0) {
            perkpoints = perkpoints - removed;
            if (perkpoints > 1000) perkpoints = 1000;
            return true;
        }
        return false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void setRaceType(RaceType raceType) {
        this.raceType = raceType;
        updateHealth();
        updateScoreboard();
        perkpoints = 0;
        abilities.clear();
        saveFile();
    }

    public UUID getUniqueId() {
        return UUID.fromString(uuid);
    }

    public String getUniqueIdAsString() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setHealth() {
        getRaceType().setHealth(getPlayer(), health);
    }

    public void restoreHealth() {
        health = getHealth();
        getPlayer().setHealthScaled(true);
        getPlayer().setHealthScale(20);
        getPlayer().setMaxHealth(20);
    }

    public double getStoredHealth() {
        return health;
    }

    public double getHealth() {
        return getPlayer().getHealth();
    }

    //Update all the values here
    public void updateConfig() {
        conf.set("racetype", raceType.name());
        conf.set("perkpoints", perkpoints);
        conf.set("health", getHealth());
        AbilityFileManager.saveAbilities(this);
    }

    public FileConfiguration getConfig() {
        return conf;
    }

    public void saveFile() {
        updateConfig();
        FileManager.save(uuid, conf);
    }

    public void updateHealth() {
        if (WorldCheck.isEnabled(getPlayer().getWorld())) {
            setHealth();
            return;
        }
        restoreHealth();
    }

    public boolean isRace(RaceType race) {
        return raceType == race;
    }

    public void updateAttributes() {
        updateHealth();
        updateScoreboard();
    }

    //Display above head

    public void updateScoreboard() {
        if (WorldCheck.isEnabled(getPlayer().getWorld())) {
            ScoreboardUtil.addScoreBoard(getPlayer(), getRaceType());
        } else {
            ScoreboardUtil.removeScoreBoard(getPlayer());
        }
    }
}
