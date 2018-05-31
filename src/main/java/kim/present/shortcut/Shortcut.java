package kim.present.shortcut;

import java.io.*;

import cn.nukkit.utils.Utils;
import cn.nukkit.plugin.PluginBase;
import kim.present.shortcut.lang.PluginLang;

public final class Shortcut extends PluginBase {
    public static final String resourceNamespace = Shortcut.class.getPackage().getName().replace('.', '/') + '/';

    private static Shortcut instance;

    public static Shortcut getInstance() {
        return Shortcut.instance;
    }

    private PluginLang language;

    @Override
    public void onLoad() {
        Shortcut.instance = this;

        //Create default data folders
        File dataFolder = this.getDataFolder();
        File langFolder = new File(dataFolder, "lang/");
        langFolder.mkdirs();

        //Save default lang files (according to nukkit language setting)
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getResource(resourceNamespace + "lang/languageList.ini")));
            String langName;
            while ((langName = br.readLine()) != null) {
                if (!(new File(langFolder, langName + "/lang.ini")).exists()) {
                    String langFileName = "lang/" + langName + "/lang.ini";
                    this.saveResource(resourceNamespace + langFileName, langFileName, false);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Load lang list
        try {
            File languageListFile = new File(this.getDataFolder(), "lang/languageList.ini");
            if (!languageListFile.exists()) {
                this.saveResource(resourceNamespace + "lang/languageList.ini", "lang/languageList.ini", false);
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
        this.saveResource(resourceNamespace + "lang/" + langName + "/config.yml", "config.yml", false);
    }

    public PluginLang getLanguage() {
        return language;
    }
}
