package com.miniStreaming.ch02.job;

/**
 * The interface for the operators.
 * An operation reads events from the incoming stream, apply logic on them,
 * and emits result to the outgoing stream.
 * @param <I> The data type of the events in the incoming stream
 * @param <O> The data type of the events in the outgoing stream
 */
public interface IOperator<I, O> extends IComponent<O> {
}
