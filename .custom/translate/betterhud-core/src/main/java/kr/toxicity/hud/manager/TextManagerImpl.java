package kr.toxicity.hud.manager;

import kr.toxicity.hud.api.manager.TextManager;
import kr.toxicity.hud.api.plugin.ReloadInfo;

import java.io.File;

/**
 * Singleton implementation of {@link TextManager}.
 * Responsible for loading bitmap and TrueType fonts and building character-width tables
 * used when computing pixel widths for rendered HUD text.
 */
public class TextManagerImpl implements BetterHudManager, TextManager {

    /** Singleton instance. */
    public static final TextManagerImpl INSTANCE = new TextManagerImpl();

    /** Private – use {@link #INSTANCE}. */
    private TextManagerImpl() {}

    /** {@inheritDoc} */
    @Override public void start() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void preReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void reload(File workingDir, ReloadInfo info) {
        // TODO: load font definitions from workingDir/fonts/
    }

    /** {@inheritDoc} */
    @Override public void postReload() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public void end() { /* nothing */ }

    /** {@inheritDoc} */
    @Override public String getManagerName() { return "Text"; }
}
