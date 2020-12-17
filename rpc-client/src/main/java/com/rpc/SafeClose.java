package com.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-17 21:26
 */
public class SafeClose {

    public static void closeInput(InputStream... is) {
        if (is != null) {
            for (int i = 0; i < is.length; i++) {
                try {
                    is[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void closeOutput(OutputStream... os) {
        if (os != null) {
            for (int i = 0; i < os.length; i++) {
                try {
                    os[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
