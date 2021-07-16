package space.thenathan.discordplayerlist;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static space.thenathan.discordplayerlist.DiscordPlayerList.jda;

public class EventListener extends ListenerAdapter
{
    
    public static void onConnect(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server)
    {
        ServerPlayerEntity player = handler.getPlayer();
        TextChannel textChannel = jda.getTextChannelById(DiscordPlayerList.configManager.MESSAGE_CHANNEL_ID);
        if (textChannel != null && DiscordPlayerList.configManager.DO_JOIN)
        {
            textChannel.sendMessage(DiscordPlayerList.configManager.JOIN_MESSAGE_FORMAT.formatted(player.getEntityName())).queue();
        }
    }
    
    public static void onDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server)
    {
        ServerPlayerEntity player = handler.getPlayer();
        TextChannel textChannel = jda.getTextChannelById(DiscordPlayerList.configManager.MESSAGE_CHANNEL_ID);
        if (textChannel != null && DiscordPlayerList.configManager.DO_LEAVE)
        {
            textChannel.sendMessage(DiscordPlayerList.configManager.LEAVE_MESSAGE_FORMAT.formatted(player.getEntityName())).queue();
        }
    }
    
    @Override
    public void onSlashCommand(SlashCommandEvent event)
    {
        if (!event.getName().equals("list")) return;
        Map<String, Boolean> playerNames = new HashMap<>();
        
        if (DiscordPlayerList.server.getPlayerManager().getPlayerList().size() == 0)
        {
            event.reply("\uD83E\uDD21\uD83D\uDCBB  No players online!").setEphemeral(false).queue();
            return;
        }
        
        for (ServerPlayerEntity serverPlayerEntity : DiscordPlayerList.server.getPlayerManager().getPlayerList())
        {
            AbstractTeam scoreboardTeam = serverPlayerEntity.getScoreboardTeam();
            if (scoreboardTeam != null)
            {
                if (scoreboardTeam.getName().equalsIgnoreCase("afkDis.afk"))
                {
                    playerNames.put(serverPlayerEntity.getEntityName(), true);
                    continue;
                }
            }
            
            playerNames.put(serverPlayerEntity.getEntityName(), false);
        }
        
        TreeMap<String, Boolean> alphabeticalOrder = new TreeMap<>(playerNames);
        TreeMap<String, Boolean> sortedNameMap = new TreeMap<>();
        TreeMap<String, Boolean> sortedAFKMap = new TreeMap<>();
        
        for (String name : alphabeticalOrder.keySet())
        {
            if (!alphabeticalOrder.get(name))
            {
                sortedNameMap.put(name, alphabeticalOrder.get(name));
            }
            else
            {
                sortedAFKMap.put(name, alphabeticalOrder.get(name));
            }
        }
        
        sortedNameMap.putAll(sortedAFKMap);
        
        String response = "**Online Players:**  \uD83E\uDD21\uD83D\uDCBB";
        for (String name : sortedNameMap.keySet())
        {
            response += "\n - %s".formatted(name);
            
            if (sortedNameMap.get(name))
            {
                response += " (AFK)";
            }
        }
        
        event.reply(response).setEphemeral(false).queue();
    }
}
