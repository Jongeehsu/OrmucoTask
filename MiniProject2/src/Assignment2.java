import java.util.regex.Pattern;

public class Assignment2 {


    private static boolean isValidInput(String version){
        return Pattern.matches("^[0-9.-]+$", version);
    }


    private static int compareAbsValue(String input1, String input2){
            String[] breakdowns1 = input1.split("\\.");
            String[] breakdowns2 = input2.split("\\.");

            int len = Math.max(breakdowns1.length,breakdowns2.length);

            if(breakdowns1.length >=3 || breakdowns2.length >=3) {
                System.out.println("Invalid input");
                return -999;
            }

            for(int i=0;i<len;i++){

                int num1 = (i<breakdowns1.length) ? Math.abs(Integer.parseInt(breakdowns1[i])) : 0;
                int num2 = (i<breakdowns2.length) ? Math.abs(Integer.parseInt(breakdowns2[i])) : 0;

                if(num1<num2){
                    return  -1;
                }else if(num1 > num2) {
                    return 1;
                }
            }

            return 0;
    }


    public static boolean isPositive(String input){
        if(input.startsWith("-")){
            return false;
        }else{
            return true;
        }
    }

    public static int compareProcess(String input1, String input2){

        if(!isValidInput(input1) || !isValidInput(input2)){
            throw new IllegalArgumentException("Invalid input,be aware");
        }

        if(isPositive(input1) && isPositive(input2))  return  compareAbsValue(input1,input2);

        if(isPositive(input1) && !isPositive(input2)) return  1;

        if(isPositive(input2) && !isPositive(input1)) return  -1;

        return  -1*compareAbsValue(input1,input2);

    }


    public static void main(String[] args){


        System.out.println(compareProcess("1.2","1.1"));
        System.out.println(compareProcess("1.2.1","1.1.1"));
        System.out.println(compareProcess("1.2","1.2"));
        System.out.println(compareProcess("2.0","4.9"));
        System.out.println(compareProcess("1","1.1"));
        System.out.println(compareProcess("1.2","1.1"));

        System.out.println("__________negative test________________");
        System.out.println(compareProcess("-1.2","1.1")); //-1
        System.out.println(compareProcess("1.2","-8")); //1
        System.out.println(compareProcess("-1.2","0")); // -1
        System.out.println(compareProcess("0","-98.1")); //1

        System.out.println("__________invalie input test________________");
        System.out.println(compareProcess("-1.2a","1.1")); //-1

    }

}
