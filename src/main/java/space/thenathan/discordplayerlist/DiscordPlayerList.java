package space.thenathan.discordplayerlist;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscordPlayerList implements ModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger();
    
    //    public static JDA JDA;
    
    public static ConfigManager configManager;
    
    public static MinecraftDedicatedServer server;
    
    @Override
    public void onInitialize()
    {
        configManager = new ConfigManager();
        
        if (configManager.error)
        {
            LOGGER.error("discordstatus.properties doesn't have the right information! Fill out the correct Discord IDs.");
        }
        
        ServerPlayConnectionEvents.JOIN.register(EventListener::onConnect);
        ServerPlayConnectionEvents.DISCONNECT.register(EventListener::onDisconnect);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> DiscordPlayerList.server = (MinecraftDedicatedServer) server);
        
        //        try
        //        {
        ////            JDABuilder builder = JDABuilder.createDefault(configManager.DISCORD_BOT_TOKEN);
        ////            builder.setActivity(Activity.watching("you"));
        ////            JDA = builder.build();
        ////
        ////            JDA.addEventListener(new EventListener());
        ////
        ////            String guildID = configManager.GUILD_ID;
        ////            if (guildID == null)
        ////            {
        ////                LOGGER.error("guild-id invalid!");
        ////                throw new Exception();
        ////            }
        ////            LOGGER.info("discordplayerlist waiting for JDA to be ready...");
        ////            JDA.awaitReady();
        ////            Guild guild = JDA.getGuildById(guildID);
        ////            guild.upsertCommand("list", "List players online").queue();
        ////
        ////            LOGGER.info(Class.forName("net.minecraft.server.network.ServerPlayNetworkHandler").getName());
        ////            ServerPlayConnectionEvents.JOIN.register(EventListener::onConnect);
        ////            ServerPlayConnectionEvents.DISCONNECT.register(EventListener::onDisconnect);
        ////            ServerLifecycleEvents.SERVER_STARTED.register(server -> DiscordPlayerList.server = (MinecraftDedicatedServer) server);
        ////
        ////            JDA.addEventListener(new EventListener());
        //        }
        //        catch (Exception e)
        //        {
        //            LOGGER.warn("Couldn't login to Discord bot.");
        //            e.printStackTrace();
        //        }
    }
}
