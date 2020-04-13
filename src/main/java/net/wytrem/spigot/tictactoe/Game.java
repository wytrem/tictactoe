package net.wytrem.spigot.tictactoe;

import com.google.common.base.Preconditions;
import net.wytrem.spigot.tictactoe.board.Board;
import net.wytrem.spigot.utils.i18n.Text;
import net.wytrem.spigot.utils.transactions.InventoryTransaction;
import net.wytrem.spigot.utils.sharedinventory.SharedInventory;
import net.wytrem.spigot.utils.transactions.Transactions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;

public class Game extends InventoryTransaction<GameDetails, Game> {
    private Board board;
    private Player first, second;
    private int turn = 0;
    private int padding;

    public Game(Transactions<GameDetails, Game> service, GameDetails details) {
        super(service, details);
        this.board = new Board(details.size);
    }

    @Override
    protected SharedInventory createInventory() {
        SharedInventory inventory = new SharedInventory(this.details.size * 9, Text.of("Board"));
        this.padding = (9 - this.details.size) / 2;

        int x = 0, y = 0;

        inventory.fillRect(x, y, this.padding, this.details.size);

        x = this.padding + this.details.size;
        y = 0;

        inventory.fillRect(x, y, 9 - x, this.details.size);

        return inventory;
    }

    @Override
    public void click(Player whoClicked, InventoryClickEvent event) {
        if ((turn == 0 && whoClicked == second) || (turn == 1 && whoClicked == first)) {
            event.setCancelled(true);
            return;
        }

        if (event.getClick() != ClickType.LEFT) {
            event.setCancelled(true);
            return;
        }

        int slot = event.getSlot();

        if (this.inventory.getItem(slot) != null) {
            event.setCancelled(true);
            return;
        }

        int x = slot % 9 - padding;
        int y = slot / 9;

        String ch = whoClicked == first ? "x" : "o";
        ItemStack itemStack = new ItemStack(whoClicked == first ? Material.BLACK_WOOL : Material.WHITE_WOOL);
        Board.PlayResult result = this.board.play(x, y, ch);


        if (result == Board.PlayResult.CONTINUE) {
            this.inventory.setItem(slot, itemStack);
            event.setCancelled(true);
            this.turn = (this.turn + 1) % 2;
        } else if (result == Board.PlayResult.PAT) {
            this.first.sendMessage("PAT");
            this.second.sendMessage("PAT");
            this.terminate();
        } else if (result instanceof Board.PlayerWon) {
            Board.PlayerWon playerWon = (Board.PlayerWon) result;
            if (turn == 0) {
                this.first.sendMessage("you win");
                this.second.sendMessage("you loose");
            } else {
                this.second.sendMessage("you win");
                this.first.sendMessage("you loose");
            }

            this.terminate();
        }
    }

    @Override
    public void initiate(Collection<Player> players) {
        super.initiate(players);
        Preconditions.checkArgument(players.size() == 2);
        Iterator<Player> playerIterator = players.iterator();
        this.first = playerIterator.next();
        this.second = playerIterator.next();
        this.first.sendMessage("Initiating game with details = " + this.details);
    }

    public Player getFirst() {
        return first;
    }

    public Player getSecond() {
        return second;
    }
}
