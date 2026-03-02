package kr.toxicity.hud.bukkit.player;

import kr.toxicity.hud.api.adapter.LocationWrapper;
import kr.toxicity.hud.api.adapter.WorldWrapper;
import kr.toxicity.hud.player.HudPlayerImpl;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Bukkit-specific {@link kr.toxicity.hud.api.player.HudPlayer} implementation.
 * Bridges BetterHud's player model with a live {@link Player} object.
 */
public class HudPlayerBukkit extends HudPlayerImpl {

    /** The underlying Bukkit player. */
    private final Player player;

    /** Pre-computed UUID for fast access. */
    private final UUID uuid;

    /**
     * Constructs a Bukkit HUD player.
     *
     * @param player   the Bukkit player to wrap
     * @param barColor the initial boss-bar colour from configuration
     */
    public HudPlayerBukkit(Player player, BossBar.Color barColor) {
        super(barColor);
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    // -------------------------------------------------------------------------
    // HudPlayer
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public UUID uuid() { return uuid; }

    /** {@inheritDoc} */
    @Override
    public String name() { return player.getName(); }

    /**
     * {@inheritDoc}
     * Returns the player's current location wrapped in a platform-agnostic record.
     */
    @Override
    public LocationWrapper location() {
        Location loc = player.getLocation();
        return new LocationWrapper(
                loc.getX(), loc.getY(), loc.getZ(),
                loc.getYaw(), loc.getPitch(),
                world()
        );
    }

    /**
     * {@inheritDoc}
     * Returns the player's current world wrapped in a platform-agnostic record.
     */
    @Override
    public WorldWrapper world() {
        org.bukkit.World w = player.getWorld();
        return new WorldWrapper(w.getName(), w.getUID());
    }

    /**
     * {@inheritDoc}
     * Returns the underlying Bukkit {@link Player} object.
     */
    @Override
    public Object handle() { return player; }

    /**
     * {@inheritDoc}
     * Persists this player's state to the active database.
     */
    @Override
    public void save() {
        kr.toxicity.hud.api.database.HudDatabase db =
                kr.toxicity.hud.api.BetterHudAPI.inst().getDatabaseManager().getCurrentDatabase();
        if (db != null && !db.isClosed()) {
            db.save(this);
        }
    }
}
