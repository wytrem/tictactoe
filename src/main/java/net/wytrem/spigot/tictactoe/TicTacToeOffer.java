package net.wytrem.spigot.tictactoe;

import com.google.common.base.MoreObjects;
import net.wytrem.spigot.utils.offers.Offer;
import net.wytrem.spigot.utils.text.Formats;
import org.bukkit.entity.Player;

public class TicTacToeOffer extends Offer {
    private final int width, height;

    public TicTacToeOffer(Player proposer, Player proposed) {
        this(proposer, proposed, 3, 3);
    }

    public TicTacToeOffer(Player proposer, Player proposed, int width, int height) {
        super(proposer, proposed);
        this.width = width;
        this.height = height;
    }

    @Override
    public void accepted() {
        TicTacToe.instance.startGame(this.getSender(), this.getRecipient(), this.width, this.height);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean isDuplicate(Offer offer) {
        return super.isDuplicate(offer) && offer instanceof TicTacToeOffer && ((TicTacToeOffer) offer).width == this.width && ((TicTacToeOffer) offer).height == this.height;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("width", width)
                .add("height", height)
                .toString();
    }

    @Override
    public void fields(Formats formats) {
        super.fields(formats);
        formats.add("width", this.width);
        formats.add("height", this.height);
    }
}

