package net.wytrem.spigot.tictactoe;

import com.google.common.base.Preconditions;
import net.wytrem.spigot.tictactoe.board.Board;
import net.wytrem.spigot.utils.transactions.InventoryTransaction;
import net.wytrem.spigot.utils.sharedinventory.SharedInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;

public class Game extends InventoryTransaction<GameDetails, Game, Games> {
    private Board board;
    private Player first, second;
    private int turn = 0;
    private int inventoryWidth;
    private int padding;

    public Game(Games games, GameDetails details) {
        super(games, details);
        // TODO: create winning count param
        this.board = new Board(details.width, details.height, details.width);
    }

    @Override
    protected SharedInventory createInventory() {
        if (this.details.width == 3 && this.details.height == 3) {
            this.padding = 0;
            this.inventoryWidth = 3;
            return SharedInventory.dispenser(this.service.texts.boardTitle);
        }
        else {
            SharedInventory inventory = SharedInventory.basic(this.details.height * 9, this.service.texts.boardTitle);
            this.inventoryWidth = 9;
            this.padding = (9 - this.details.width) / 2;

            int x = 0, y = 0;

            ItemStack filler = new ItemStack(Material.STICK);

            inventory.fillRect(x, y, this.padding, this.details.height, filler);

            x = this.padding + this.details.width;
            y = 0;

            inventory.fillRect(x, y, 9 - x, this.details.height, filler);

            return inventory;
        }
    }

    @Override
    public void click(Player whoClicked, InventoryClickEvent event) {
        int slot = event.getSlot();

        if (slot == -999) {
            return;
        }

        // TODO: check clicked inventory

        event.setCancelled(true);
        if ((turn == 0 && whoClicked == second) || (turn == 1 && whoClicked == first)) {
            return;
        }

        if (event.getClick() != ClickType.LEFT) {
            return;
        }

        if (this.inventory.getItem(slot) != null) {
            return;
        }

        int x = slot % this.inventoryWidth - this.padding;
        int y = slot / this.inventoryWidth;

        String ch = whoClicked == first ? "x" : "o";
        ItemStack itemStack = new ItemStack(whoClicked == first ? Material.BLACK_WOOL : Material.WHITE_WOOL);
        Board.PlayResult result = this.board.play(x, y, ch);


        if (result == Board.PlayResult.CONTINUE) {
            this.inventory.setItem(slot, itemStack);
            this.turn = (this.turn + 1) % 2;
        } else if (result == Board.PlayResult.DRAW) {
            this.service.texts.draw.send(this.first);
            this.service.texts.draw.send(this.second);
            this.terminate();
        } else if (result instanceof Board.PlayerWon) {
            Board.PlayerWon playerWon = (Board.PlayerWon) result;
            if (playerWon.player.equals("x")) {
                this.service.texts.youWon.send(this.first);
                this.service.texts.otherWon.send(this.second, "player", this.first.getDisplayName());
            } else {
                this.service.texts.youWon.send(this.second);
                this.service.texts.otherWon.send(this.first, "player", this.second.getDisplayName());
            }

            this.terminate();
        }
    }

    @Override
    public void initiate(Collection<Player> players) {
        Preconditions.checkArgument(players.size() == 2);
        super.initiate(players);
        Iterator<Player> playerIterator = players.iterator();
        this.first = playerIterator.next();
        this.second = playerIterator.next();
    }

    public Player getFirst() {
        return first;
    }

    public Player getSecond() {
        return second;
    }
}
