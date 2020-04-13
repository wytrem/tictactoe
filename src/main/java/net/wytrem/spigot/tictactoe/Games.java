package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.transactions.Transactions;

public class Games extends Transactions<GameDetails, Game> {
    public Games(WyPlugin plugin) {
        super(plugin);
    }

    @Override
    public Game create(GameDetails details) {
        return new Game(this, details);
    }
}
