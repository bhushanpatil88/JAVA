import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;



public class Main
{
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void solve() {
            int n = in.nextInt();
            String s = in.nextLine();
            int [] arr = in.readArr(n);
            print(arr);
            out.print(s);
    }
    
    public static void main(String args[]) {
        try {
            
            int T=in.nextInt();
            while(T-- > 0){
                solve();
            }
        } catch (Exception e) {
        }
        finally{
            out.close();
        }
        
    }

    static class FastReader{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String next(){
            while(st==null || !st.hasMoreTokens()){
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }
        int nextInt(){
            return Integer.parseInt(next());
        }
        long nextLong(){
            return Long.parseLong(next());
        }
        double nextDouble(){
            return Double.parseDouble(next());
        }
        String nextLine(){
            try {
                return br.readLine().trim();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int[] readArr(int N){
            int [] arr = new int[N];
            for(int i = 0;i<N;i++)
                arr[i] = in.nextInt();
            return arr;
        }
    }

    public static void print(int[] arr)
    {
        for(int x: arr)
            out.print(x+" ");
        out.println();
    }
    public static boolean isPrime(long n)
    {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }
    public static long gcd(long a, long b)
    {
        return (b == 0) ? a : gcd(b, a % b);
    }
    public static long totient(long n)
    {
        long result = n;
        for (int p = 2; p*p <= n; ++p)
            if (n % p == 0)
            {
                while(n%p == 0)
                    n /= p;
                result -= result/p;
            }
        if (n > 1)
            result -= result/n;
        return result;
    }
    public static List<Integer> findDiv(int N)
    {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i * i <= N; i++) {
            if (N % i == 0) {
                divisors.add(i);
                if (i != N / i) divisors.add(N / i);
            }
        }
        Collections.sort(divisors);
        return divisors;
    }
    public static void sort(int[] arr)
    {
        ArrayList<Integer> ls = new ArrayList<Integer>();
        for(int x: arr)
            ls.add(x);
        Collections.sort(ls);
        for(int i=0; i < arr.length; i++)
            arr[i] = ls.get(i);
    }
    public static long power(long x, long y, long p)
    {
        long res = 1L;
        x = x%p;
        while(y > 0)
        {
            if((y&1)==1)
                res = (res*x)%p;
            y >>= 1;
            x = (x*x)%p;
        }
        return res;
    }
       
}


