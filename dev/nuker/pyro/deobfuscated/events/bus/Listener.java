// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.bus;

import net.jodah.typetools.TypeResolver;
import java.util.function.Consumer;

public class Listener<E> implements Consumer<E>
{
    private final Consumer<E> consumer;
    private final Class<E> target;
    
    public Listener(final Consumer<E> consumer) {
        this.consumer = consumer;
        this.target = (Class<E>)TypeResolver.resolveRawArgument(Consumer.class, consumer.getClass());
    }
    
    @Override
    public void accept(final E e) {
        this.consumer.accept(e);
    }
    
    public Class<E> getTarget() {
        return this.target;
    }
}
