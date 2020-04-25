package net.wytrem.spigot.tictactoe;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import net.wytrem.spigot.utils.transactions.TransactionDetails;

public class GameDetails extends TransactionDetails {
    public final int width, height;

    public GameDetails() {
        this(3, 3);
    }

    public GameDetails(int width, int height) {
        Preconditions.checkArgument(width <= 6 && width >= 3);
        Preconditions.checkArgument(height <= 6 && height >= 3);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("width", width)
                .add("height", height)
                .toString();
    }
}
