package net.wytrem.spigot.tictactoe;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.wytrem.spigot.utils.WyPlugin;
import net.wytrem.spigot.utils.commands.Command;
import net.wytrem.spigot.utils.commands.args.CommonArguments;
import net.wytrem.spigot.utils.offers.Offer;
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

    @Override
    protected TextComponent buildOfferedYouText(Player proposed, TicTacToeOffer offer) {
        Player proposer = offer.getProposer();
        TextComponent message = new TextComponent(ChatColor.GRAY + this.texts.proposedYou.string(proposed, "player", proposer.getDisplayName(), "width", offer.getWidth(), "height", offer.getHeight()));
        message.addExtra(this.buildAcceptText(proposed, offer));
        message.addExtra(this.buildDenyText(proposed, offer));
        return message;
    }

    @Override
    public void sendAlreadyProposedText(TicTacToeOffer offer) {
        this.texts.alreadyProposed.send(offer.getProposer(), "player", offer.getRecipient().getDisplayName(), "width", offer.getWidth(), "height", offer.getHeight());
    }
}
