package com.graywolf336.rocketchat.example.features;

import java.util.Random;

import com.graywolf336.rocketchat.RocketChatAPI;
import com.graywolf336.rocketchat.RocketChatClient;
import com.graywolf336.rocketchat.interfaces.Feature;
import com.graywolf336.rocketchat.objects.RocketChatMessage;
import com.graywolf336.rocketchat.objects.RocketChatRoom;

public class RandomNumberSender extends Feature {
    public String getName() {
        return "Random Number Sender";
    }
    
    public boolean onEnable(RocketChatClient client) {
        Random r = new Random(System.currentTimeMillis());
        
        RocketChatMessage m = new RocketChatMessage();
        m.setMessage("Random number of the enable is: " + (r.nextFloat() * 100));
        m.setRoom(client.getRoomManager().getRoomById("GENERAL").orElse(new RocketChatRoom("GENERAL")));

        return client.sendMessage(m) && RocketChatAPI.sendMessageToGeneral("Another random number for the enable is: " + (r.nextDouble() * 100));
    }
}
