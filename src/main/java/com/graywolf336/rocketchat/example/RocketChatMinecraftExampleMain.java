package com.graywolf336.rocketchat.example;

import org.bukkit.plugin.java.JavaPlugin;

import com.graywolf336.rocketchat.RocketChatAPI;
import com.graywolf336.rocketchat.example.features.RandomNumberSender;
import com.graywolf336.rocketchat.example.subscriptions.ActiveUsersSubscription;

public class RocketChatMinecraftExampleMain extends JavaPlugin {
    private ActiveUsersSubscription activeSub;
    
    public void onLoad() {
        RocketChatAPI.getFeatureRegistry().addFeature(new RandomNumberSender());
    }
    
    public void onEnable() {
        this.activeSub = new ActiveUsersSubscription(this, RocketChatAPI.getChatClient());
        RocketChatAPI.getChatClient().queueSubscription(this.activeSub);
    }
    
    public void onDisable() {
        RocketChatAPI.getChatClient().removeSubscription(this.activeSub);
    }
}
