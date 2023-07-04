package org.example.threadedQueuingSystem.ThreadTask.Threads;

import org.example.threadedQueuingSystem.ThreadTask.ThreadAction;
import java.util.ArrayDeque;
import java.util.Queue;

public class ResourceThreadAction<T> extends Thread {
    /**
     <code>type</code> is the type that is going to be executed on.
     */
    private T type;
    /**
     <code>processedElements</code> the number if elements processed.
     */
    private int processedElements = 0;
    /**
     <code>SyncThreadQueue</code> is the queue that the thread needs to process.
     */
    private final Queue<T> SyncThreadQueue;
    /**
     <code>isExecuting</code> if the task is executing.
     */
    private boolean isExecuting = false;
    /**
     <code>threadTask</code> the task to execute.
     */
    private ThreadAction<T> threadAction;
    public ResourceThreadAction(){
        SyncThreadQueue = new ArrayDeque<>();
    }
    public ResourceThreadAction(int size){
        SyncThreadQueue = new ArrayDeque<>(size);
    }
    public void register(ThreadAction<T> threadAction){
        this.threadAction = threadAction;
    }
    @Override
    public void run() {
        synchronized (this){
            processedElements = 0;
            isExecuting = true;
         while (SyncThreadQueue.size() > 0){
             T element = SyncThreadQueue.poll();
             if (element == null) continue;
             threadAction.execute(element);
             processedElements++;
          }
            isExecuting = false;
        }
    }
    public void addToSyncQueue(T element){
        SyncThreadQueue.add(element);
    }
    public T pollSyncQueue(){
        return SyncThreadQueue.poll();
    }
    public void clearSyncQueue(){
        SyncThreadQueue.clear();
    }
    public int syncQueueSize(){
        return SyncThreadQueue.size();
    }
    public T getType() {
        return type;
    }
    public boolean isExecuting() {
        return isExecuting;
    }

    public int getProcessedElements() {
        return processedElements;
    }

    @Override
    public ClassLoader getContextClassLoader() {
        return super.getContextClassLoader();
    }
}
