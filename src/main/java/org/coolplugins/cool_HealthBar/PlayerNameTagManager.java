package org.coolplugins.cool_HealthBar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerNameTagManager {

    private static final String TEAM_NAME = "chb_hidden_tags";
    private Team hideTeam;

    public void setup() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        this.hideTeam = scoreboard.getTeam(TEAM_NAME);

        if (this.hideTeam == null) {
            this.hideTeam = scoreboard.registerNewTeam(TEAM_NAME);
        }

        this.hideTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

        // Se il plugin viene ricaricato mentre ci sono già giocatori online
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            hide(onlinePlayer);
        }
    }

    public void hide(Player player) {
        if (hideTeam != null && !hideTeam.hasPlayer(player)) {
            hideTeam.addPlayer(player);
        }
    }


    public void restore(Player player) {
        if (hideTeam != null && hideTeam.hasPlayer(player)) {
            hideTeam.removePlayer(player);
        }
    }


    public void cleanup() {
        if (hideTeam != null) {
            hideTeam.unregister();
            hideTeam = null;
        }
    }
}
