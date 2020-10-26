package ch3_jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 打印线程
 * 86150
 * DumpStack
 * 2020/2/22 0:58
 */
public class DumpStack {
    public static void main(String[] args) {
        //虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + "==" + threadInfo.getThreadName());
        }

    }
}
