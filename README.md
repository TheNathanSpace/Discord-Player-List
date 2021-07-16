[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

# Discord Player List

This is a simple mod that uses a Discord bot to send Discord messages when a player joins/leaves your server. It also
adds a `/list` Discord command that lists online players.

This is probably most useful for small multiplayer servers, alerting other players when the boys are online.

## Config

```properties
discord-bot-token=none
guild-id=none
message-channel-id=none
send-join-messages=true
send-leave-messages=true
join-message-format=🟢  %s is online!
leave-message-format=🔴  %s is offline.
```

The first three should be replaced with strings. You can get the token
by [creating a Discord bot](https://discord.com/developers/applications), and the second two
by [enabling Developer Mode](https://www.howtogeek.com/714348/how-to-enable-or-disable-developer-mode-on-discord/) and
right-clicking.

The middle two determine whether or not the join/leave messages are sent.

The final two allow you to customize the join/leave message formats.

## Discord Bot Permissions

When creating the application/bot, make sure to enable these commands.

OAuth2:

- `applications.commands`

Bot:

- `Send Messages`

## License

This work is licensed under
a [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/

[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png

[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg