package space.thenathan.discordplayerlist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ConfigManager
{
    public boolean error = false;
    
    public SimpleConfig CONFIG;
    
    public String DISCORD_BOT_TOKEN;
    public String MESSAGE_CHANNEL_ID;
    public String GUILD_ID;
    
    public boolean DO_JOIN;
    public boolean DO_LEAVE;
    
    public String JOIN_MESSAGE_FORMAT;
    public String LEAVE_MESSAGE_FORMAT;
    
    public ConfigManager()
    {
        loadConfig();
    }
    
    public void loadConfig()
    {
        CONFIG = SimpleConfig.of("discordstatus").provider(this::provider).request();
        
        DISCORD_BOT_TOKEN = CONFIG.getOrDefault("discord-bot-token", "none");
        MESSAGE_CHANNEL_ID = CONFIG.getOrDefault("message-channel-id", "none");
        GUILD_ID = CONFIG.getOrDefault("guild-id", "none");
        JOIN_MESSAGE_FORMAT = CONFIG.getOrDefault("join-message-format", "\uD83D\uDFE2  %s is online!");
        LEAVE_MESSAGE_FORMAT = CONFIG.getOrDefault("leave-message-format", "\uD83D\uDD34  %s is offline.");
    
        if (Arrays.asList(DISCORD_BOT_TOKEN, MESSAGE_CHANNEL_ID, GUILD_ID).contains("none"))
        {
            error = true;
        }
        else
        {
            error = false;
        }
        
        DO_JOIN = CONFIG.getOrDefault("send-join-messages", true);
        DO_LEAVE = CONFIG.getOrDefault("send-leave-messages", true);
    }
    
    private String provider(String filename)
    {
        InputStream in = DiscordPlayerList.class.getClassLoader().getResourceAsStream("assets/discordplayerlist/discordstatus.properties");
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        
        return input.lines().collect(Collectors.joining("\n"));
    }
}
