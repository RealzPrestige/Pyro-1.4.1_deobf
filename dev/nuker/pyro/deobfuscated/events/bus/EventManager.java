// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.bus;

import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import com.google.common.collect.Multimaps;
import java.util.concurrent.ConcurrentHashMap;
import com.google.common.collect.Multimap;

public class EventManager implements EventBus
{
    private final Multimap<Class<?>, Listener> listenerMultimap;
    private final Multimap<EventListener, Listener> parentListeners;
    
    public EventManager() {
        this.listenerMultimap = (Multimap<Class<?>, Listener>)Multimaps.newSetMultimap((Map)new ConcurrentHashMap(), ConcurrentHashMap::newKeySet);
        this.parentListeners = (Multimap<EventListener, Listener>)Multimaps.newSetMultimap((Map)new ConcurrentHashMap(), ConcurrentHashMap::newKeySet);
    }
    
    @Override
    public void post(final Object event) {
        try {
            this.listenerMultimap.get((Object)event.getClass()).forEach(listener -> listener.accept(event));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void subscribe(final EventListener eventListener) {
        Arrays.stream(eventListener.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(f.getType())).forEach(field -> this.registerAsListener(eventListener, field));
    }
    
    private void registerAsListener(final EventListener eventListener, final Field field) {
        try {
            final boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            final Listener listener = (Listener)field.get(eventListener);
            field.setAccessible(isAccessible);
            if (listener != null) {
                this.parentListeners.get((Object)eventListener).add(listener);
                this.listenerMultimap.get((Object)listener.getTarget()).add(listener);
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void unsubscribe(final EventListener eventListener) {
        final ArrayList<Class<?>> classList;
        final Iterator<Class<?>> iterator;
        Class<?> clazz;
        this.parentListeners.get((Object)eventListener).forEach(l -> {
            classList = new ArrayList<Class<?>>(this.listenerMultimap.keySet());
            classList.iterator();
            while (iterator.hasNext()) {
                clazz = iterator.next();
                this.listenerMultimap.get((Object)clazz).remove(l);
            }
        });
    }
}
