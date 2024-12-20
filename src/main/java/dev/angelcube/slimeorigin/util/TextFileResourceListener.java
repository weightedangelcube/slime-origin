package dev.angelcube.slimeorigin.util;

import dev.angelcube.slimeorigin.Slimeorigin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class TextFileResourceListener implements SimpleSynchronousResourceReloadListener {
    public static String textFileLine; 

    /**
     * Gives Fabric our resource listener Identifier.
     *
     * @return The given Identifier
     */
    @Override
    public Identifier getFabricId() {
        return Slimeorigin.identifier("resource_listener");
    }

    /**
     * Reads the file "action_on_text_file.txt;" if not found, logs an error.
     *
     * @param manager The resource manager
     */
    @Override
    public void reload(ResourceManager manager) {
        Resource textFile;
        textFile = manager.getResource(Slimeorigin.identifier("action_on_text_file.txt")).orElse(null);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(textFile.getInputStream()))) {
            textFileLine = reader.readLine();
            Slimeorigin.LOGGER.info(textFileLine);
        } catch (Exception e) {
            Slimeorigin.LOGGER.error("Error occured while reading action_on_text_file.txt: ", e);
        }
    }
}