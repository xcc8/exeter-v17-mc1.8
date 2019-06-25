package wtf.nuf.exeter.keybind;

import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.events.InputEvent;
import wtf.nuf.exeter.mcapi.eventsystem.EventManager;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mcapi.manager.SetManager;

public final class KeybindManager extends SetManager<Keybind> {
    public KeybindManager() {
        EventManager.getInstance().register(this);
    }

    @Listener
    public void onInput(InputEvent inputEvent) {
        if (inputEvent.getInputType() == InputEvent.InputType.KEY_PRESS) {
            if (inputEvent.getKey() != Keyboard.KEY_NONE && Keyboard.getEventKeyState()) {
                for (Keybind keybind : getSet()) {
                    if (keybind.getKey() == inputEvent.getKey()) {
                        keybind.onPressed();
                        break;
                    }
                }
            }
        }
    }
}
