package org.coolplugins.cool_HealthBar.customization;

import com.google.gson.*;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.coolplugins.cool_HealthBar.Coolhealthbar;
import org.coolplugins.cool_HealthBar.gui.component.HealthBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HealthBarConfigurationManager {

    private final static Path JSON_PATH = Bukkit.getWorldContainer().toPath().toAbsolutePath().normalize().resolve("config").resolve("health_bar_config.json");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Checks if the configuration file exists, if not It creates it and set up a default configuration
     */
    public static void ensureFileExists() {
        if (Files.isRegularFile(JSON_PATH)) {
            return;
        }

        try {
            // Create the directory if it doesn't exist
            Path parentDir = JSON_PATH.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // create the default object
            JsonObject defaultJson = new JsonObject();
            defaultJson.addProperty("symbol", "▰");
            defaultJson.addProperty("emptySymbol", "▱");
            defaultJson.addProperty("emptyCharColor", "gray");
            defaultJson.addProperty("bracketColor", "white");

            // Write on disk (File f = new File(path, name) only creates a pointer)
            try (BufferedWriter writer = Files.newBufferedWriter(JSON_PATH)) {
                GSON.toJson(defaultJson, writer);
            }

            Coolhealthbar.getServerLogger().info("Created default configuration in: " + JSON_PATH);

        } catch (IOException e) {
            Coolhealthbar.getServerLogger().severe("Error during the default configuration: " + e.getMessage());
        }
    }

    /**
     *
     * @return The content of the configuration file as a JsonObject
     */
    private static JsonObject getConfigFile() {

        if ((!Files.isRegularFile(JSON_PATH) ||!Files.isReadable(JSON_PATH))|| !JSON_PATH.toString().toLowerCase().endsWith(".json"))
            return null;

        try (BufferedReader reader = Files.newBufferedReader(JSON_PATH)) {
            JsonElement element = JsonParser.parseReader(reader);

            return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
        } catch (IOException e) {
            Coolhealthbar.getServerLogger().warning("Error during the json parsing: " + e.getMessage());
            return null;
        }
    }

    /**
     *
     * @return a HealthBar Component made with custom parameters
     */
    public static HealthBar getCustomHealthbar() {

        JsonObject obj = getConfigFile();

        if (obj == null) return null;

        char filledChar = parseChar(obj, "symbol", '▰');
        char emptyChar = parseChar(obj, "emptySymbol", '▱');


        TextColor emptyColor = parseColor(obj, "emptyCharColor", NamedTextColor.GRAY);
        TextColor bracketColor = parseColor(obj, "bracketColor", NamedTextColor.WHITE);

        return HealthBar.customHealthBar(filledChar, emptyChar, emptyColor, bracketColor);

    }

    /**
     *
     * @param obj JSON object from which getting the data
     * @param key property of the JSON object
     * @param defaultChar char to set if there isn't a valid property
     * @return the parsed char value of the element related to the property
     */
    private static char parseChar(JsonObject obj, String key, char defaultChar) {
        if (!obj.has(key) || obj.get(key).isJsonNull()) {
            return defaultChar;
        }
        String str = obj.get(key).getAsString();
        return str.isEmpty() ? defaultChar : str.charAt(0);
    }

    /**
     *
     * @param obj JSON object from which getting the data
     * @param key property of the JSON object
     * @param defaultColor TextColor to set if there isn't a valid property
     * @return the parsed TextColor of the element related to the property
     */
    private static TextColor parseColor(JsonObject obj, String key, TextColor defaultColor) {
        if (!obj.has(key) || obj.get(key).isJsonNull()) {
            return defaultColor;
        }
        String raw = obj.get(key).getAsString().trim().toLowerCase();

        // makes support for both hex code and string names
        if (raw.startsWith("#")) {
            TextColor hex = TextColor.fromHexString(raw);
            return hex != null ? hex : defaultColor;
        }

        NamedTextColor named = NamedTextColor.NAMES.value(raw.replace("-", "_"));
        return named != null ? named : defaultColor;
    }
}
