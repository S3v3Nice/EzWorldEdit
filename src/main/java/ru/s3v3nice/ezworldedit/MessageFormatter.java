package ru.s3v3nice.ezworldedit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;

public class MessageFormatter {
    public static final String DEFAULT_LANG = "eng";
    private final Config langConfig;

    public MessageFormatter() {
        String lang = Server.getInstance().getLanguage().getLang();
        String path = getLangFilePath(lang);
        PluginBase plugin = EzWorldEdit.getInstance();

        boolean langFileExists = plugin.getResource(path) != null;
        if (!langFileExists) {
            path = getLangFilePath(DEFAULT_LANG);
        }

        plugin.saveResource(path, true);
        langConfig = new Config(new File(plugin.getDataFolder(), path));
    }

    private String getLangFilePath(String lang) {
        return "lang/" + lang + ".yml";
    }

    public String formatMessage(String key, Object... vars) {
        String message = langConfig.getString(key, key);
        for (Object var : vars) {
            message = message.replaceFirst("%var", var.toString());
        }

        return message;
    }
}
