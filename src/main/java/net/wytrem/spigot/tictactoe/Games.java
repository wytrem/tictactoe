package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.i18n.I18n;
import net.wytrem.spigot.utils.text.Text;
import net.wytrem.spigot.utils.text.TextsRegistry;
import net.wytrem.spigot.utils.transactions.Transactions;

public class Games extends Transactions<GameDetails, Game> {
    public final Texts texts;

    public Games(WyPlugin plugin) {
        super(plugin);
        this.texts = new Texts(plugin.getI18n());
    }

    @Override
    public Game create(GameDetails details) {
        return new Game(this, details);
    }

    public static class Texts extends TextsRegistry {
        public Text youWon;
        public Text otherWon;
        public Text youLeft;
        public Text otherLeft;
        public Text draw;
        public Text boardTitle;

        public Texts(I18n i18n) {
            super(i18n, "texts");
        }

        @Override
        public void load() {
            this.youWon = this.get("youWon", "You won the game.").asInformation();
            this.otherWon = this.get("otherWon", "${player} won the game.").asInformation();
            this.youLeft = this.get("youLeft", "You left the game.").asInformation();
            this.otherLeft = this.get("otherLeft", "${player} left the game.").asInformation();
            this.draw = this.get("draw", "Draw.").asInformation();
            this.boardTitle = this.get("boardTitle", "TicTacToe").asInformation();
        }
    }
}
