package com.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 21:02
 */
public class RpcClientFrame {
    public static <T> T getRemoteProxyObj(final Class objInterface, String host, int port) {
        InetSocketAddress address = new InetSocketAddress(host, port);
        return (T) Proxy.newProxyInstance(objInterface.getClassLoader(), new Class[]{objInterface}, new DynProxy(objInterface, address));
    }

    private static class DynProxy implements InvocationHandler {
        private final Class objInterface;
        private final InetSocketAddress address;

        public DynProxy(Class objInterface, InetSocketAddress address) {
            this.objInterface = objInterface;
            this.address = address;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectOutputStream os = null;
            ObjectInputStream is = null;

            socket = new Socket();
            socket.connect(address);
            try {
                os = new ObjectOutputStream(socket.getOutputStream());
                os.writeUTF(objInterface.getName());
                os.writeUTF(method.getName());
                os.writeObject(method.getParameterTypes());
                os.writeObject(args);
                os.flush();
                is = new ObjectInputStream(socket.getInputStream());
                return is.readObject();

            } finally {
                SafeClose.closeInput(is);
                SafeClose.closeOutput(os);
            }
        }
    }

}
