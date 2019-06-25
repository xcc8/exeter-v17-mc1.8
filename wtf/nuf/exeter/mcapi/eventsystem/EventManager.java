package wtf.nuf.exeter.mcapi.eventsystem;

import net.minecraft.client.Minecraft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * simplified darkmagician6 eventapi
 */
public final class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final Map<Class<? extends Event>, List<MethodData>> dataMap = new HashMap<>();

    private void register(Method method, Object object) {
        Class<? extends Event> event = (Class<? extends Event>) method.getParameterTypes()[0];
        MethodData methodData = new MethodData(method, object);

        if (!methodData.getMethod().isAccessible()) {
            methodData.getMethod().setAccessible(true);
        }

        if (!dataMap.containsKey(event)) {
            dataMap.put(event, new CopyOnWriteArrayList<>() {
                {
                    add(methodData);
                }
            });
        } else {
            if (!dataMap.get(event).contains(methodData)) {
                dataMap.get(event).add(methodData);
            }
        }
    }

    public void register(Object object) {
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(Listener.class)) {
                register(method, object);
            }
        }
    }

    public void unregister(Object object) {
        for (List<MethodData> methodDataList : dataMap.values()) {
            for (MethodData methodData : methodDataList) {
                if (methodData != null && methodData.getSource().equals(object) &&
                        methodData.getMethod().isAnnotationPresent(Listener.class)) {
                    methodDataList.remove(methodData);
                }
            }
        }
    }

    public Event call(Event event) {
        if (event != null && (Minecraft.getMinecraft().theWorld != null &&
                Minecraft.getMinecraft().thePlayer != null)) {
            List<MethodData> methodDataList = dataMap.get(event.getClass());
            if (methodDataList != null) {
                for (MethodData methodData : methodDataList) {
                    if (methodData != null) {
                        invoke(methodData.getMethod(), methodData.getSource(), event);
                    }
                }
            }
        }
        return event;
    }

    private void invoke(Method method, Object object, Event event) {
        try {
            method.invoke(object, event);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    public class MethodData {
        private Method method;
        private Object source;

        private MethodData(Method method, Object source) {
            this.method = method;
            this.source = source;
        }

        public Method getMethod() {
            return method;
        }

        public Object getSource() {
            return source;
        }
    }
}
