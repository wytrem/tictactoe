package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.commands.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class TicTacToe extends WyPlugin implements Listener {

    public static TicTacToe instance;
    private Games games;

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        // Offers
        TicTacToeOffersManager offersManager = new TicTacToeOffersManager(this);
        this.enableService(offersManager);

        // Games
        this.games = new Games(this);
        this.enableService(this.games);

        // Commands
        Command tictactoeCommand = this.commands.builder()
                .child(offersManager.buildAcceptCommand(), "accept")
                .child(offersManager.buildDenyCommand(), "deny")
                .child(offersManager.buildTakeBackCommand(), "takeback")
                .child(offersManager.buildProposeCommand(), "offer")
                .child(offersManager.buildListCommand(), "pending")
                .build();

        this.commands.register(tictactoeCommand, "tictactoe");
    }

    @Override
    public String getCodeName() {
        return "tictactoe";
    }

    public void startGame(Player proposer, Player recipient, int width, int height) {
        this.games.initiate(new GameDetails(width, height), Arrays.asList(proposer, recipient));
    }
}
