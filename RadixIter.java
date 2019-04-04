import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
public class RadixIter{
  public static void main(String[]args){
  System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
  int[]MAX_LIST = {10, 500, 1000000000};
  for(int MAX : MAX_LIST){
    for(int size = 31250; size < 500001; size*=2){
      long qtime=0;
      long btime=0;
      //average of 5 sorts.
      for(int trial = 0 ; trial <=30; trial++){
        int []data1 = new int[size];
        int []data2 = new int[size];
        for(int i = 0; i < data1.length; i++){
          data1[i] = (int)(Math.random()*MAX);
          data2[i] = data1[i];
        }
        long t1,t2;
        t1 = System.currentTimeMillis();
        radixsort(data2);
        t2 = System.currentTimeMillis();
        qtime += t2 - t1;
        t1 = System.currentTimeMillis();
        Arrays.sort(data1);
        t2 = System.currentTimeMillis();
        btime+= t2 - t1;
        if(!Arrays.equals(data1,data2)){
          System.out.println("FAIL TO SORT!");
          System.exit(0);
        }
      }
      System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
    }
    System.out.println();
  }
}
  @SuppressWarnings("unchecked")
  public static void radixsort(int[]data){
     //System.out.println("data: " + Arrays.toString(data));
      //create the buckets and fill it w LLs
      MyLinkedList<Integer>[] digFreq = new MyLinkedList[20];
      for (int a = 0; a<digFreq.length; a++){
        digFreq[a] = new MyLinkedList<Integer>();
      }
      //go through all the data and
      //a) find the longest num to determine num of iterations and
      //b) move array data to linked list variable
      int longestNum = 0;
      MyLinkedList<Integer> myData = new MyLinkedList<Integer>();
      //while finding the longest number place the data into the freqDig
      for (Integer i : data){
        Integer processing = i;
        //placer tells you which bucket to move to
        int placer = getDigit(processing, 1);
        //if the number is positive, use this rule
        //if (processing >= 0){
        //  digFreq[placer+10].add(processing);
          //System.out.println("added " + processing + " to digFreq[" + (placer+10) +"] which now looks like: " + digFreq[placer+10].toString());
        //}else{ //otherwise make sure to add it before all the positives
          digFreq[9+placer].add(processing);
          //System.out.println("added " + processing + " to digFreq[" + (placer+9) +"] which now looks like: " + digFreq[placer+9].toString());
        //}
        if (Math.abs(i) > longestNum){ //longest numbers hould include negatives
          longestNum = i;
        }
      }
      //now build my data from digFreq
      if (myData.size() == 0){ //this should always be true, just a check
        for (int d = 0; d<20; d++){
          myData.extend(digFreq[d]); //add all the lists back to myData for next processing
        }
      }
      //System.out.println("myData: " + myData.toString());

      //now determine how many digits are in this longest num
      int numIters = Integer.toString(longestNum).length();
      if (longestNum < 0){
        numIters--; //accounts for the '-'
      }
      //go through data numIters time
      //System.out.println("longestNum: " + longestNum + "\tnumIters: " + numIters);
      for (int place = 2; place<=numIters; place++){
        //so that size won't update in the loop below
        //go through all of myData
        while (myData.hasNext()){
          Integer processing = myData.next();
          //placer tells you which bucket to move to
          int placer = getDigit(processing, place);
          //if the number is positive, use this rule
          //if (processing >= 0){
          //  digFreq[placer+10].add(processing);
          //}else{ //otherwise make sure to add it before all the positives
            digFreq[9+placer].add(processing);
          //}
          //note: there are 20 buckets. bc getDigit returns 0 if
          //no nth place exists for that number, no many how many iterations it will stay
          //in the 0th buckets (which for negative numbers is 9 and for positive numbers is 10)
        }
        //now that you've processed data, rebuild it for next iteration:
        myData.clear(); //make room for new vals
        for (int d = 0; d<20; d++){
          myData.extend(digFreq[d]);
        }
        myData.resetIter();
      }
      //rebuild data array to modify
      for (int e = 0; e<data.length; e++){
        data[e] = myData.next();
      }
      //System.out.println("Data: " + Arrays.toString(data));
  }
  //% in java will give negative number -- be aware of this -- this is why it is digFreq[9+placer]
  public static Integer getDigit(Integer num, int place){
    Integer toReturn = 0;
    int check = Integer.toString(num).length(); //if num is negative, check will be 1+ number of digits
    if (place > (check) || (num < 0 && place == check)){
      return toReturn; //say you need 3rd of 8, that's 0. 8 = 008
    }
    for (int i = 0; i<place; i++){
      toReturn = num%10;
      num/=10;
    }
    return toReturn;
  }
}
