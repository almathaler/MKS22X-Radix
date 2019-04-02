import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
public class Radix{
  public static void main(String[] args){
    System.out.println("The 3rd digit of 4321: " + getDigit(4321, 3));
    int[] data = {13, 14, 42, 31, 5, 4, 2, 23, 90, 6, 5, 4, 31};
    System.out.println("data: " + Arrays.toString(data));
    radixsort(data);
    System.out.println("sorted: " + Arrays.toString(data));
  }
  @SuppressWarnings("unchecked")
  public static void radixsort(int[]data){
      //MyLinkedList<Integer>[] digFreq = new MyLinkedList<Integer>[20]; //20 so that we can do negatives
      MyLinkedList<Integer>[] digFreq = new MyLinkedList[20];
      for (int a = 0; a<digFreq.length; a++){
        digFreq[a] = new MyLinkedList<Integer>();
      }
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
          int placer = getDigit(processing, place); //change getDigit to return 0 if u can't get that dig
          System.out.println("placer: " + placer);
          if (processing >= 0){
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
      //throw new IllegalArgumentException("place is greater than num digits");
      return 0; //say you need 3rd of 8, that's 0
    }
    Integer toReturn = 0;
    for (int i = 0; i<place; i++){
      toReturn = num%10;
      num/=10;
    }
    return toReturn;
  }
}
