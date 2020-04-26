package net.wytrem.spigot.tictactoe;

import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.commands.Command;
import net.wytrem.spigot.utils.commands.args.CommonArguments;
import net.wytrem.spigot.utils.offers.OffersManager;
import org.bukkit.entity.Player;

public class TicTacToeOffersManager extends OffersManager<TicTacToeOffer> {
    public TicTacToeOffersManager(WyPlugin plugin) {
        super(plugin);
    }

    @Override
    protected TicTacToeOffer createOffer(Player proposer, Player proposed) {
        return new TicTacToeOffer(proposer, proposed);
    }

    @Override
    public Command buildProposeCommand() {
        return this.plugin.getCommands().builder()
                .requireSenderToBePlayer()
                .argument(CommonArguments.onlinePlayer("player"))
                .argument(CommonArguments.optional(CommonArguments.integerInRange("width", 3, 6)))
//                .argument(CommonArguments.optional(CommonArguments.integerInRange("height", 3, 6)))
                .performer((context) -> {
                    Player proposer = (Player) context.source;
                    Player proposed = context.args.requireOne("player");
                    int width = context.args.<Integer>getOne("width").orElse(3);
//                    int height = context.args.<Integer>getOne("height").orElse(width);
                    TicTacToeOffer offer = new TicTacToeOffer(proposer, proposed, width, width);
                    this.post(offer);
                }).build();
    }
}
