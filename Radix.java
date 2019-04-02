import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
public class Radix{
  public static void main(String[] args){
    System.out.println("The 3rd digit of 4321: " + getDigit(4321, 3));
    int[] data = {3, 3, 3, 3, 3, 4, 1, 5, 2, 3, 1, 0, 0, 0};
    System.out.println("data: " + Arrays.toString(data));
    radixsort(data);
    System.out.println("sorted: " + Arrays.toString(data));
  }
  @SuppressWarnings("unchecked")
  public static void radixsort(int[]data){
      //MyLinkedList<Integer>[] digFreq = new MyLinkedList<Integer>[20]; //20 so that we can do negatives
      MyLinkedList<Integer> bucket0 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket1 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket2 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket3 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket4 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket5 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket6 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket7 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket8 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket9 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket10 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket11 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket12 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket13 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket14 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket15 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket16 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket17 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket18 = new MyLinkedList<Integer>();
      MyLinkedList<Integer> bucket19 = new MyLinkedList<Integer>();
      MyLinkedList<Integer>[] digFreq = {bucket0, bucket1, bucket2, bucket3, bucket4, bucket5, bucket6, bucket7,
                                         bucket8, bucket9, bucket10, bucket11, bucket12, bucket13, bucket14, bucket15,
                                         bucket16, bucket17, bucket18, bucket19};
      int longestNum = 0; //find this, get it's size, that's how many iterations we use
      MyLinkedList<Integer> myData = new MyLinkedList<Integer>(); //put data into a linkedlist as variable to be updated each iteration
      for (Integer i : data){
        myData.add(i); //fill it while finding the longest num
        if (i > longestNum){
          longestNum = i;
        }
      } //this is how many times thru the loop that we will do it's ok if numIters is 6 and most nums are two digits -- they  will stay sorted in the 0s list
      System.out.println("longestNum: " + longestNum);
      System.out.println("myData: " + myData.toString());
      int numIters = Integer.toString(longestNum).length();
      System.out.println("numIters: " + numIters);
      for (int place = 1; place<=numIters; place++){
        //fill out the digFreq board
        int numItersC = myData.size(); //so that size doesn't update while loop is going
        for (int c = 0; c<numItersC; c++){
          Integer processing = myData.removeFirst();
          System.out.println("processing: " + processing);
          int placer = getDigit(processing, place);
          System.out.println("placer: " + placer);
          if (placer >= 0){
            System.out.println("going to add to list " + (placer+10) + " which looks like: ");
            System.out.println(digFreq[placer+10].toString());
            digFreq[placer+10].add(processing);//if it is a positive number, add it to its section
          }else{ //means it's negative
            digFreq[9-placer].add(processing);
          }
        }
        //now that you've processed data, rebuild it for next iteration:
        if (myData.size() == 0){ //this should be true, just a check
          for (int d = 0; d<20; d++){
            myData.extend(digFreq[d]); //add all the lists back to myData for next processing
          }
        }
      }
      for (int e = 0; e<data.length; e++){
        data[e] = myData.removeFirst();
      }
  }
  //% in java will give negative number -- be aware of this
  public static Integer getDigit(Integer num, int place){
    if (place > (Integer.toString(num).length())){
      throw new IllegalArgumentException("place is greater than num digits");
    }
    Integer toReturn = 0;
    for (int i = 0; i<place; i++){
      toReturn = num%10;
      num/=10;
    }
    return toReturn;
  }
}
