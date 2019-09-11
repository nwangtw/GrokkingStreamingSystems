package com.gss.ch03.engine;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public abstract class ComponentExecutor<I, O> implements IComponentExecutor<I, O> {
}
