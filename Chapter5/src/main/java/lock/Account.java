package lock;

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Account {
    private final Lock lock = new ReentrantLock();
    private int money;
}
