package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.offers.OffersManager;
import org.bukkit.entity.Player;

public class TicTacToeOffersManager extends OffersManager<TicTacToeOffer> {
    public TicTacToeOffersManager(WyPlugin plugin) {
        super(plugin);
    }

    @Override
    protected TicTacToeOffer createProposal(Player proposer, Player proposed) {
        return new TicTacToeOffer(proposer, proposed);
    }
}
