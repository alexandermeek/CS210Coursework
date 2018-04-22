public class Main {

    private static final int m = 4;
    private static final int n = 4*m;
    private static final int k = 2;

    public static void main(String[] args) {
        Thread threadA = new ThreadA(m,n);
        Thread threadB = new ThreadB();
        Thread threadC = new ThreadC();
    }
}
