package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class AbsIntegerEncoderTest {
    @Test
    public void test() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        channel.writeOutbound(buf);
        channel.finish();
        for (int i = 0; i < 10; i++) {
            int o = channel.readOutbound();
            System.out.println("0000000000000=" + o);
        }
    }
}