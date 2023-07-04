package org.example.threadedQueuingSystem.ThreadTask;

public interface ThreadAction<T> {
   void execute(T type);
}
