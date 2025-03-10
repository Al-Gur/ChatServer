package telran.chat.server.mediation;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    private final LinkedList<T> queue;
    private final int maxSize;
    private final Lock mutex = new ReentrantLock();
    private final Condition senderWaitingCondition = mutex.newCondition();
    private final Condition receiverWaitingCondition = mutex.newCondition();

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        queue = new LinkedList<>();
    }

    @Override
    public void push(T message) {
        mutex.lock();
        try {
            while (queue.size() >= maxSize) {
                try {
                    senderWaitingCondition.await();
                } catch (InterruptedException e) {
                    System.out.println("thread was interrupted");
                }
            }
            queue.add(message);
            receiverWaitingCondition.signal();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public T pop() {
        mutex.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    receiverWaitingCondition.await();
                } catch (InterruptedException e) {
                    System.out.println("thread was interrupted");
                }
            }
            T msg = queue.poll();
            senderWaitingCondition.signal();
            return msg;
        } finally {
            mutex.unlock();
        }
    }
}
