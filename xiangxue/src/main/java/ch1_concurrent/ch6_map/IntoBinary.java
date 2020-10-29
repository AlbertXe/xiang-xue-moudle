package ch1_concurrent.ch6_map;

/**
 * 位运算
 *
 * @author HASEE
 */
public class IntoBinary {
    public static void main(String[] args) {
        System.out.println("the 4 is " + Integer.toBinaryString(4));
        System.out.println("the 6 is " + Integer.toBinaryString(6));
        System.out.println("the 4&6 is " + Integer.toBinaryString(4 & 6));
        System.out.println("the 4|6 is " + Integer.toBinaryString(4 | 6));
        System.out.println("the ~4 is " + Integer.toBinaryString(~4));
        //位异或   1^1=0 1^0=1 0^0=0
        System.out.println("the 4^6 is " + Integer.toBinaryString(4 ^ 6));

        // << 有符号的左移   >> 有符号的右移   >>>无符号的右移。

        // 取模  a%2^n 等价 于  a&(2^n-1)
        System.out.println("345 % 16 is " + (345 % 16) + " or " + (345 & (16 - 1)));
    }
}
