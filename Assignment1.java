public class Assignment1 {


    public static boolean isOverlappedOrNot(int x1,int x2, int x3, int x4){
    
        return (x2>=x3 && x3>=x1 ) ||  (x2 >=x3 && x2 <=x4);
    }



    public static void main(String[] args){
    
        System.out.println(isOverlappedOrNot(1,5,2,6)); //true
        System.out.println(isOverlappedOrNot(1,3,6,8)); //false
        System.out.println(isOverlappedOrNot(1,5,4,4)); //true
        System.out.println(isOverlappedOrNot(3,3,1,5)); //true
        System.out.println(isOverlappedOrNot(1,1,1,1)); //true;
        System.out.println(isOverlappedOrNot(1,1,2,2)); //false;


    }

}

