package ru.s3v3nice.ezworldedit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;

public class Messages {
    private final static Config languageConfig;

    static {
        String language = Server.getInstance().getLanguage().getLang();
        String path = "lang/" + language + ".yml";
        PluginBase plugin = EzWorldEdit.getInstance();
        if (plugin.getResource(path) == null) path = "lang/eng.yml";

        plugin.saveResource(path, true);
        languageConfig = new Config(new File(plugin.getDataFolder(), path));
    }

    public static String get(String key, Object... vars) {
        String message = languageConfig.getString(key, key);
        for (Object var : vars) {
            message = message.replaceFirst("%var", var.toString());
        }

        return message;
    }
}
