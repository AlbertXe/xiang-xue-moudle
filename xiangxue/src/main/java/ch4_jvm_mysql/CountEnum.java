package ch4_jvm_mysql;

/**
 * 86150
 * CountEnum
 * 2020/2/25 21:19
 */
public enum CountEnum {
    PLUS {
        @Override
        public double opr(double a, double b) {
            return a + b;
        }
    },
    MINUS {
        @Override
        public double opr(double a, double b) {
            return a - b;
        }
    },
    MULTI {
        @Override
        public double opr(double a, double b) {
            return a * b;
        }
    },
    DIVIDE {
        @Override
        public double opr(double a, double b) {
            return a / b;
        }
    };


    public abstract double opr(double a, double b);
}
