package kim.present.shortcut;

import java.io.*;

import cn.nukkit.utils.Utils;
import cn.nukkit.plugin.PluginBase;
import kim.present.shortcut.lang.PluginLang;

public final class Shortcut extends PluginBase {

    private PluginLang language;

    @Override
    public void onLoad() {
        //Create default data folders
        File dataFolder = this.getDataFolder();
        File langFolder = new File(dataFolder, "lang/");
        langFolder.mkdirs();

        //Save default lang files (according to nukkit language setting)
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getResource("lang/languageList.ini")));
            String langName;
            while ((langName = br.readLine()) != null) {
                if (!(new File(langFolder, langName + "/lang.ini")).exists()) {
                    this.saveResource("lang/" + langName + "/lang.ini", false);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Load lang list
        try {
            File languageListFile = new File(this.getDataFolder(), "lang/languageList.ini");
            if (!languageListFile.exists()) {
                this.saveResource("lang/languageList.ini", false);
            }
            for (String line : Utils.readFile(languageListFile).split("\n")) {
                PluginLang.addLang(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        //Load language
        String langName = this.getConfig().getString("settings.language");
        if (!PluginLang.availableLang(langName)) {
            this.getLogger().alert(langName + " is not available lang.");
            langName = PluginLang.FALLBACK_LANGUAGE;
        }
        this.language = new PluginLang(langName, this.getDataFolder() + "/lang/", this);
        this.getLogger().info(this.getLanguage().translateString("language.selected", new String[]{this.language.getName(), this.language.getLang()}));
    }

    /**
     * Save default config file according to nukkit language setting
     */
    @Override
    public void saveDefaultConfig() {
        String langName = this.getServer().getLanguage().getLang();
        if (!PluginLang.availableLang(langName)) {
            langName = PluginLang.FALLBACK_LANGUAGE;
        }
        this.saveResource("lang/" + langName + "/config.yml", "config.yml", false);
    }

    public PluginLang getLanguage() {
        return language;
    }
}
