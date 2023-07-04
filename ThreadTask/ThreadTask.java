package org.example.threadedQueuingSystem.ThreadTask;

public interface ThreadTask<T,R> {
     R task(T value);
}
