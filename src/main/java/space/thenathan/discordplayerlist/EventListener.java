package space.thenathan.discordplayerlist;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

//import static space.thenathan.discordplayerlist.DiscordPlayerList.JDA;

public class EventListener // extends ListenerAdapter
{
    
    public static void onConnect(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server)
    {
        ServerPlayerEntity player = handler.getPlayer();
        Process proc = null;
        try
        {
            proc = Runtime.getRuntime().exec("sh join.sh " + player.getName().getString());
            proc.waitFor();
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        //        TextChannel textChannel = JDA.getTextChannelById(DiscordPlayerList.configManager.MESSAGE_CHANNEL_ID);
        //        if (textChannel != null && DiscordPlayerList.configManager.DO_JOIN)
        //        {
        //            textChannel.sendMessage(DiscordPlayerList.configManager.JOIN_MESSAGE_FORMAT.formatted(player.getEntityName())).queue();
        //        }
    }
    
    public static void onDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server)
    {
        ServerPlayerEntity player = handler.getPlayer();
        Process proc = null;
        try
        {
            proc = Runtime.getRuntime().exec("sh leave.sh " + player.getName().getString());
            proc.waitFor();
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        //        TextChannel textChannel = JDA.getTextChannelById(DiscordPlayerList.configManager.MESSAGE_CHANNEL_ID);
        //        if (textChannel != null && DiscordPlayerList.configManager.DO_LEAVE)
        //        {
        //            textChannel.sendMessage(DiscordPlayerList.configManager.LEAVE_MESSAGE_FORMAT.formatted(player.getEntityName())).queue();
        //        }
    }
    
    //    @Override
    //    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    //    {
    //
    //        //        for (ServerPlayerEntity serverPlayerEntity : DiscordPlayerList.server.getPlayerManager().getPlayerList())
    //        if (!event.getName().equals("list")) return;
    //        Map<String, Boolean> playerNames = new HashMap<>();
    //
    //        if (DiscordPlayerList.server.getPlayerManager().getPlayerList().size() == 0)
    //        {
    //            event.reply("\uD83E\uDD21\uD83D\uDCBB  No players online!").setEphemeral(false).queue();
    //            return;
    //        }
    //
    //        for (ServerPlayerEntity serverPlayerEntity : DiscordPlayerList.server.getPlayerManager().getPlayerList())
    //        {
    //            playerNames.put(sanitizeFormatting(serverPlayerEntity.getName().getString()), false);
    //        }
    //
    //        TreeMap<String, Boolean> alphabeticalOrder = new TreeMap<>(playerNames);
    //        TreeMap<String, Boolean> sortedNameMap = new TreeMap<>();
    //        TreeMap<String, Boolean> sortedAFKMap = new TreeMap<>();
    //
    //        for (String name : alphabeticalOrder.keySet())
    //        {
    //            if (alphabeticalOrder.get(name)) // If AFK
    //            {
    //                sortedAFKMap.put(name, alphabeticalOrder.get(name));
    //            }
    //            else
    //            {
    //                sortedNameMap.put(name, alphabeticalOrder.get(name));
    //            }
    //        }
    //
    //        Map<String, Boolean> finalMap = new HashMap<>();
    //        finalMap.putAll(sortedNameMap);
    //        finalMap.putAll(sortedAFKMap);
    //
    //        String response = "**Online Players:**  \uD83E\uDD21\uD83D\uDCBB";
    //        for (String name : finalMap.keySet())
    //        {
    //            response += "\n - %s".formatted(name);
    //
    //            if (finalMap.get(name))
    //            {
    //                response += " (AFK)";
    //            }
    //        }
    //
    //        event.reply(response).setEphemeral(false).queue();
    //    }
    
    private static String sanitizeFormatting(String string)
    {
        string = string.replace("_", "\\_");
        return string;
    }
    
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-ORX]");
    
    private static String stripColor(@Nullable final String input)
    {
        if (input == null)
        {
            return null;
        }
        
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
    
}