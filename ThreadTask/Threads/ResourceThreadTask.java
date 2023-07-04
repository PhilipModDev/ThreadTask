package org.example.threadedQueuingSystem.ThreadTask.Threads;

import org.example.threadedQueuingSystem.ThreadTask.ThreadTask;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <code>ResourceThreadTask</code> operates on a queue that
 * the invoking thread will execute. Note that the <code><ProcessedThreadQueue/code>
 *  is the result of the task provided by <cod>ThreadTask</cod>.
  */

/*
 Published by PhilipModDev.
 Date: Tuesday, July 4,2023.
 */

public class ResourceThreadTask<T,R> extends Thread {
    /**
    <code>result</code> is the task execution value the is returned.
     */
    private R result;
    /**
     <code>type</code> is the type that is going to be executed on.
     */
    private T type;
    /**
     <code>SyncThreadQueue</code> is the queue that the thread needs to process.
     */
    private final Queue<T> SyncThreadQueue;
    /**
     <code>ProcessedThreadQueue</code> is the queue that the thread already processed.
     *Thus, the result is within it.
     */
    private final Queue<R> ProcessedThreadQueue;
    /**
     <code>isExecuting</code> if the task is executing.
     */
    private boolean isExecuting = false;
    /**
     <code>threadTask</code> the task to execute.
     */
    private ThreadTask<T,R> threadTask;
    /**
     <code>processedElements</code> the number if elements processed.
     */
    private int processedElements = 0;
    public ResourceThreadTask(){
        SyncThreadQueue = new ArrayDeque<>();
        ProcessedThreadQueue = new ArrayDeque<>();
    }
    public ResourceThreadTask(int numElements){
        SyncThreadQueue = new ArrayDeque<>(numElements);
        ProcessedThreadQueue = new ArrayDeque<>(numElements);
    }
    public void register(ThreadTask<T,R> threadTask){
        this.threadTask = threadTask;
    }


    @Override
    public void run() {
      synchronized (this) {
          isExecuting = true;
          while (!SyncThreadQueue.isEmpty()) {
              final T element = SyncThreadQueue.poll();
              if (element == null) continue;
              type = element;
              result = threadTask.task(element);
              ProcessedThreadQueue.add(result);
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
    public R pollProcessedQueue(){
        return ProcessedThreadQueue.poll();
    }
    public void clearProcessedQueue(){
        ProcessedThreadQueue.clear();
    }
    public int processedQueueSize(){
        return ProcessedThreadQueue.size();
    }

    public R getResult() {
        return result;
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
