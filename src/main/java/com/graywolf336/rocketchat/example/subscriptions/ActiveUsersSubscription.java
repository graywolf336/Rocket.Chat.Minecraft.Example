package com.graywolf336.rocketchat.example.subscriptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.internal.LinkedTreeMap;
import com.graywolf336.rocketchat.RocketChatClient;
import com.graywolf336.rocketchat.example.RocketChatMinecraftExampleMain;
import com.graywolf336.rocketchat.info.SubscriptionErrorInfo;
import com.graywolf336.rocketchat.info.SubscriptionUpdateInfo;
import com.graywolf336.rocketchat.objects.RocketChatSubscription;

public class ActiveUsersSubscription extends RocketChatSubscription {
    private RocketChatMinecraftExampleMain plugin;
    private List<User> users;

    public ActiveUsersSubscription(RocketChatMinecraftExampleMain plugin, RocketChatClient client) {
        super(client);
        this.plugin = plugin;
        this.users = new ArrayList<User>();
    }

    public String getName() {
        return "activeUsers";
    }

    public String getCollection() {
        return "users";
    }

    public void gotErrorResults(SubscriptionErrorInfo error) {
        this.plugin.getLogger().warning("An error occured in the activeUsers subscription: '" + error.toString() + "'");
    }

    public void gotSuccessResults(SubscriptionUpdateInfo info) {
        Map<String, Object> item = info.getFields();
        LinkedTreeMap<?, ?> fields = (LinkedTreeMap<?, ?>) item.get("fields");

        switch (info.getUpdateType()) {
            case ADDED:
                User u = new User(item.get("id"), fields.get("username"), fields.get("status"));
                this.users.add(u);
                this.plugin.getServer().broadcastMessage(u.getUsername() + " is now " + u.getStatus() + " on Rocket.Chat.");
                break;
            case CHANGED:
                this.users.stream().filter(user -> user.getId().equalsIgnoreCase((String)item.get("id"))).forEach(user -> {
                    user.setStatus((String)fields.get("status"));
                    this.plugin.getServer().broadcastMessage(user.getUsername() + " is now " + user.getStatus() + " on Rocket.Chat");
                });
                break;
            case REMOVED:
                System.out.println(info.getCollection() + " ~~~ " + info.getFields().toString());
                this.plugin.getServer().broadcastMessage("Removed.");
                break;
            default:
                break;
        }
    }
    
    private class User {
        private String id, username, status;
        
        public User(Object id, Object username, Object status) {
            this.id = (String) id;
            this.username = username == null ? this.id : (String) username;
            this.status = (String) status;
        }
        
        public String getId() {
            return this.id;
        }
        
        public String getUsername() {
            return this.username;
        }
        
        public String getStatus() {
            return this.status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
