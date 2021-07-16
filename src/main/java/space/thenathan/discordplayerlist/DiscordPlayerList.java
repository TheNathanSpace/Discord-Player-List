package space.thenathan.discordplayerlist;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

public class DiscordPlayerList implements ModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger();
    
    public static JDA jda;
    
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
        
        try
        {
            JDABuilder builder = JDABuilder.createDefault(configManager.DISCORD_BOT_TOKEN);
            builder.setActivity(Activity.watching("you"));
            jda = builder.build();
            
            ServerPlayConnectionEvents.JOIN.register(EventListener::onConnect);
            ServerPlayConnectionEvents.DISCONNECT.register(EventListener::onDisconnect);
            ServerLifecycleEvents.SERVER_STARTED.register(server -> DiscordPlayerList.server = (MinecraftDedicatedServer) server);
            
            jda.addEventListener(new EventListener());
            Guild guild = jda.getGuildById(configManager.GUILD_ID);
            if (guild != null)
            {
                guild.upsertCommand("list", "List players online").queue();
            }
        }
        catch (LoginException e)
        {
            LOGGER.error("Couldn't login to Discord bot.");
            e.printStackTrace();
        }
    }
}
