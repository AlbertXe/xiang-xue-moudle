package ch9_zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 86150
 * ZKtest
 * 2020/3/21 17:44
 */
public class ZKtest {
    private static String server = "192.168.1.101:2181";
    private final int time = 5000;

    @Test
    public void test() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(server, time, null);
        System.out.println(zooKeeper);
        System.out.println(zooKeeper.getState());

        zooKeeper.create("/xie", "value".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void testSession() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(server, time, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                    System.out.println("成功链接");
                }
            }
        });
        latch.await(); //等待拿到链接
        System.out.println(zooKeeper.getState());
        zooKeeper.create("/xie", "value".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }
}
