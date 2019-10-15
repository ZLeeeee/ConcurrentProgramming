package lock;

public class TestTryLock {
    public void transferMoney(Account fromAccount,Account toAccount,int money){
        while (true){
            if(fromAccount.getLock().tryLock()){
                try{
                    if(toAccount.getLock().tryLock()){
                        try{
                            if (fromAccount.getMoney() - money > toAccount.getMoney()) {
                                fromAccount.setMoney(fromAccount.getMoney() - money);
                                toAccount.setMoney(toAccount.getMoney() + money);
                            }else {
                                throw new IllegalStateException();
                            }
                        }finally {
                            toAccount.getLock().unlock();
                        }
                    }
                }finally {
                    fromAccount.getLock().unlock();
                }
            }
        }
    }
}
