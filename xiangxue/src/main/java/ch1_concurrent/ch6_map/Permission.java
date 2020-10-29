package ch1_concurrent.ch6_map;

/**
 * 权限  位运算的运用
 *
 * @author HASEE
 */
public class Permission {
    static final int ALLOW_SELECT = 1 << 0; // 0001   // 每个位置1 有权限 0 无权限
    static final int ALLOW_INSERT = 1 << 1; // 0010
    static final int ALLOW_UPDATE = 1 << 2; // 0100
    static final int ALLOW_DELETE = 1 << 3; // 1000

    int per;  // 目前权限状态

    public void setPer(int per) { // 设置
        this.per = per;
    }

    public void enable(int per) {// 增加
        this.per = this.per | per;
    }

    public void diable(int per) {// 删除权限
        this.per = this.per & ~per;
    }

    public boolean isAllow(int per) { // 判断有没有该权限
        return (this.per & per) == per;
    }

    public boolean isNotAllow(int per) {// 没有该权限
        return (this.per & per) == 0;
    }

    public static void main(String[] args) {
        int flag = 15;
        Permission permission = new Permission();
        permission.setPer(flag);
        permission.diable(ALLOW_DELETE | ALLOW_INSERT);

        System.out.println("select = " + permission.isAllow(ALLOW_SELECT));
        System.out.println("update = " + permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert = " + permission.isAllow(ALLOW_INSERT));
        System.out.println("delete = " + permission.isAllow(ALLOW_DELETE));


    }
}
