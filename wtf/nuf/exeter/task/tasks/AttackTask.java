package wtf.nuf.exeter.task.tasks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import wtf.nuf.exeter.task.Task;

public class AttackTask extends Task {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    public AttackTask(String label) {
        super(label);
    }

    public void attackEntity(Entity entity) {
        minecraft.thePlayer.swingItem();
        minecraft.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }
}
