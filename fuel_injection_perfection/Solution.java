package fuel_injection_perfection;

import java.math.BigInteger;

public class Solution {
    public static int solution(String x) {
        // Your code here
        BigInteger num=new BigInteger(x);
        int step=0;
        while(!num.equals(BigInteger.ONE)){
            if(num.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
                num=num.divide(BigInteger.valueOf(2));
                //num==3||num%4==1
            }else if(num.equals(BigInteger.valueOf(3))||num.mod(BigInteger.valueOf(4)).equals(BigInteger.ONE)){
                num=num.subtract(BigInteger.ONE);
            }else{
                num=num.add(BigInteger.ONE);
            }
            step++;
        }
        return step;
    }
    public static void main(String[] arg){
        System.out.println(solution("4"));
    }
}