package kim.present.shortcut.lang;

import java.util.ArrayList;

import cn.nukkit.lang.BaseLang;
import cn.nukkit.plugin.PluginBase;

public final class PluginLang extends BaseLang {
    private static ArrayList<String> langList = new ArrayList<>();

    public static ArrayList<String> getLangList() {
        return langList;
    }

    public static void addLang(String langName) {
        PluginLang.langList.add(langName.toLowerCase());
    }

    public static Boolean availableLang(String langName) {
        return PluginLang.langList.contains(langName.toLowerCase());
    }

    private final PluginBase plugin;

    public PluginLang(String lang, String path, PluginBase plugin) {
        super(lang, path);

        this.plugin = plugin;
    }

    public PluginBase getPlugin() {
        return plugin;
    }
}