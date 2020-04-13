package net.wytrem.spigot.tictactoe;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import net.wytrem.spigot.utils.transactions.TransactionDetails;

public class GameDetails extends TransactionDetails {
    public final int size;

    public GameDetails() {
        this(5);
    }

    public GameDetails(int size) {
        Preconditions.checkArgument(size < 10 && size >= 3);
        this.size = size;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .toString();
    }
}
