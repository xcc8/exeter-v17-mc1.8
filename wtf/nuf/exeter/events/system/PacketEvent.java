package wtf.nuf.exeter.events.system;

import wtf.nuf.exeter.mcapi.eventsystem.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    private Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
