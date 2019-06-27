package wtf.nuf.exeter.keybind;

import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.events.InputEvent;
import wtf.nuf.exeter.mcapi.eventsystem.EventManager;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mcapi.manager.SetManager;

public final class KeybindManager extends SetManager<Keybind> {
    public KeybindManager() {
        // register the listening of events
        EventManager.getInstance().register(this);
    }

    @Listener
    public void onInput(InputEvent inputEvent) {
        // make sure the input type is a key press
        if (inputEvent.getInputType() == InputEvent.InputType.KEY_PRESS) {
            // make sure the key isn't 0
            if (inputEvent.getKey() != Keyboard.KEY_NONE && Keyboard.getEventKeyState()) {
                // loop through they keybinds
                for (Keybind keybind : getSet()) {
                    // check if the key pressed is the same as the keybinds key
                    if (keybind.getKey() == inputEvent.getKey()) {
                        // if so then run onPressed which is the keys action
                        keybind.onPressed();
                        // stop looping
                        break;
                    }
                }
            }
        }
    }
}
