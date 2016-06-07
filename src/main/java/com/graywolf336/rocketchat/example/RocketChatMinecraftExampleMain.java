package com.graywolf336.rocketchat.example;

import org.bukkit.plugin.java.JavaPlugin;

import com.graywolf336.rocketchat.RocketChatAPI;
import com.graywolf336.rocketchat.example.features.RandomNumberSender;

public class RocketChatMinecraftExampleMain extends JavaPlugin {
    public void onLoad() {
        RocketChatAPI.getFeatureRegistry().addFeature(new RandomNumberSender());
    }
}
