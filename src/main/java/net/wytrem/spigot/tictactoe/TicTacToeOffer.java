package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.offers.Offer;
import org.bukkit.entity.Player;

public class TicTacToeOffer extends Offer {


    public TicTacToeOffer(Player proposer, Player proposed) {
        super(proposer, proposed);
    }

    @Override
    public void accepted() {
        TicTacToe.instance.startGame(this.getProposer(), this.getRecipient());
    }
}
