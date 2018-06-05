package kim.present.shortcut.lang;

import cn.nukkit.lang.BaseLang;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;

public final class PluginLang extends BaseLang {
    private static ArrayList<String> langList = new ArrayList<>();
    private final PluginBase plugin;

    public PluginLang(String lang, String path, PluginBase plugin) {
        super(lang, path);

        this.plugin = plugin;
    }

    public static ArrayList<String> getLangList() {
        return langList;
    }

    public static void addLang(String langName) {
        PluginLang.langList.add(langName.toLowerCase());
    }

    public static Boolean availableLang(String langName) {
        return PluginLang.langList.contains(langName.toLowerCase());
    }

    public PluginBase getPlugin() {
        return plugin;
    }
}