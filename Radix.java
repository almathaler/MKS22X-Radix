import java.util.ArrayList;
import java.lang.IllegalArgumentException;
public class Radix{
  public static void main(String[] args){
    System.out.println("The 5th digit of 4321: " + getDigit(4321, 5));
  }
  public static void radixsort(int[]data){
      MyLinkedList<Integer>[] digFreq = new MyLinkedList<Integer>[20]; //20 so that we can do negatives
      int longestNum = 0; //find this, get it's size, that's how many iterations we use
      MyLinkedList<Integer> myData = new MyLinkedList<Integer>(); //put data into a linkedlist as variable to be updated each iteration
      for (Integer i : data){
        myData.add(i); //fill it while finding the longest num
        if (i > longestNum){
          longestNum = i;
        }
      } //this is how many times thru the loop that we will do it's ok if numIters is 6 and most nums are two digits -- they  will stay sorted in the 0s list
      int numIters = Integer.toString(longestNum).length();
      for (int place = 1; place<=numIters; place++){
        //fill out the digFreq board
        for (int c = 0; c<myData.size(); c++){
          Integer processing = myData.removeFirst();
          int placer = getDigit(processing, place);
          if (placer >= 0){
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
