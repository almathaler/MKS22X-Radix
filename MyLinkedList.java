import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
public class MyLinkedList<E>{
  public class Node{
   private E data;
   private Node next,prev;
   public Node(E val, Node next_, Node prev_){
     data = val;
     next = next_;
     prev = prev_;
   }
   /**
      Node next()
      Node prev()
      void setNext(Node other)
      void setPrev(Node other)
      Integer getData()
      Integer setData(Integer i)
      String toString()
   **/
   //for our iterator
   public Node next(){
     return next;
   }
   public boolean hasNext(){
     if (next != null){
       return true;
     }
     return false;
   }

   public Node prev(){
     return prev;
   }

   public void setNext(Node newNext){
     next = newNext;
   }

   public void setPrev(Node newPrev){
     prev = newPrev;
   }

   public E getData(){
     return data;
   }

   public E setData(E i){
     E oldData = data;
     data = i;
     return oldData;
   }

   public String toString(){
     String toReturn;
     if (this == null){
       toReturn = "";
     }
     else{
       toReturn = "" + data;
     }
     return toReturn;
   }

   public String debugToString(){
    String toReturn;
    toReturn = "(" + prev + ")" + data + "(" + next + ")";
    return toReturn;
   }
  }
 private int size;
 private Node start,end;
 private Node currentIter; //this is a node, tells us where we are for iterator methods

 public static void main(String[] args){
   try{
     MyLinkedList<Integer> list = new MyLinkedList<Integer>();
     for (int i = 0; i<15; i++){
       list.add(Integer.valueOf(i));
     }
     System.out.println("This is list: " + list.toString());
     System.out.println("Let's iterate over list");
     while (list.hasNext()){
       Integer curElement = list.next();
       System.out.println("curElmement: " + curElement);
     }
     System.out.println("reseting currentIter and iterating agains: ");
     list.resetIter();
     while (list.hasNext()){
       Integer curElement = list.next();
       System.out.println("curElmement: " + curElement);
     }
   }catch(IndexOutOfBoundsException e){
     System.out.println(e);
   }catch(NoSuchElementException e){
     System.out.println(e);
   }
 }

 public MyLinkedList(){
   size = 0;
   start = null;
   end = null;
 }
 //return current place of iterator methods
 //public Integer current(){
 //   return current;
 //}
 //iterator method next() which returns value at node of curent+1
 public E next(){
   if (currentIter == null){ //make current start
     currentIter = start;
     return currentIter.getData();
   }else if (currentIter == end){
     throw new NoSuchElementException("end of linkedList currentIter == end");
   }else{
     currentIter = currentIter.next(); //move currentIter node up one
     return currentIter.getData();
   }
 }
 //iterator method hasNext() which returns boolean of whether there is node after
 public boolean hasNext(){
   if (currentIter == null || currentIter.next() != null){ //if it's not at the end or it hasn't yet started
     return true;
   }
   return false;
 }
 //reset where iterator is
 public void resetIter(){
   currentIter = null; //make it so next time next() is called, returns val of start
 }

 public int size(){
   return size;
 }
//for radix
 public boolean add(E value){
   Node toAdd = new Node(value, null, null); //make a new node with the value we want, but don't yet link it to list
   if (start == null && end == null){ //if you're adding to an empty list, make toAdd both start and end
     start = toAdd; //but don't make toAdd point to itself as next and prev, that's bad
     end = toAdd;
   }
   else{ //however we will add a next to the hypothetical only element here where we:
     end.setNext(toAdd); //make whatever is the current end point to Node we are adding
     //toAdd.setNext(null); //make toAdd's next null(kinda irrelevant because it was already null -- test if this can be removed) -- seems like it can be
     toAdd.setPrev(end); //make whatever was added point back to what was before the end
     end = toAdd; //give the title of end to the node that was added
   }
   size+=1; //don't forget to increase size
   return true; //since we did it, true
 }
 public E removeFirst() throws IndexOutOfBoundsException{
   E toReturn;
   if (size == 0){
     throw new IndexOutOfBoundsException("size 0 no front");
   }else if (size == 1){
     toReturn = this.start.getData();
     this.clear(); //don't have a size-- at the end cuz clear sets size = 0
   }else{
     toReturn = this.start.getData();
     start = start.next();
     start.setPrev(null);
     size--;
   }
   return toReturn;
 }
 public void clear(){ //just make it no longer point to start and end
    start = null;
    end = null;
    size = 0;
 }
 public void extend(MyLinkedList other){
   //if this is an empty list, just set it to be the other on
   if (this.size == 0){
     start = other.start;
     end = other.end;
     size += other.size();
     other.clear();
   }else if (other.size == 0){
     //don't do anything to this
   }else{
     end.setNext(other.start);
     other.start.setPrev(end);
     end = other.end;
     size += other.size;
     other.clear();
   }
        //in O(1) runtime, move the elements from other onto the end of this
        //The size of other is reduced to 0
        //The size of this is now the combined sizes of both original lists
 }





 private Node getNthNode(int nth) throws IndexOutOfBoundsException{
   if (nth >= size || nth < 0){ //if nth isn't an actual index, give exception
     throw new IndexOutOfBoundsException("requested index out of bounds: " + nth);
   }
   Node current = start; //starting from the start (0th index) node;
   for (int currentIndex = 0; currentIndex < nth; currentIndex++){ //cycle through n times, starting from 0th, setting current to one after current
     current = current.next(); //it's appropriate to make <n and not <=n cuz this block set's current to whatever's after, what's after the (n-1)th index is n
   }
   return current; //this method is LINEAR
 }

 public E get(int i) throws IndexOutOfBoundsException{
   if (i >= size || i < 0){ //the only reason we through exception here and not in getNthIndex is for clarity
     throw new IndexOutOfBoundsException("GETrequested index out of bounds: " + i);
   }
   return getNthNode(i).getData(); //use getNthNode and then return value -- user doesn't need to deal with node object
 }

 public E set(int i, E value) throws IndexOutOfBoundsException{
   if (i >= size || i < 0){ //same explanation for get
     throw new IndexOutOfBoundsException("SETrequested index out of bounds: " + i);
   }
   //Integer toReturn = this.get(i); //we return the old value, getting old value is 0(N)
   //getNthNode(i).setData(value); //another linear pass
   return getNthNode(i).setData(value); //return old value
 }

 public boolean contains(E value){
   Node current = start;
   for (int i = 0; i<size; i++){ //go through entire list
     if (current.getData().equals(value)){ //NOT GOOD THIS METHOD IS QUADRATIC!!! NO -- MAKE IT LINEAR
       return true; //made it linear by not using the get() method but by using currnet = start, current = current.next() instead of repeated gets
     }
     current = current.next();
   }
   return false;
 }

 public int indexOf(E value){ //AGAIN THIS IS QUADRATIC MAKE IT LINEAR BY USING CURRENT VARIABLE
   Node current = start;
   for (int i = 0; i<size; i++){
     if (current.getData().equals(value)){
       return i;
     }
     current = current.next();
   }
   return -1;
 }
//remember, you don't have to declare new Integers you just have to do Integer.valueOf(#youwant)
 public void add(int index,E value){
   if (index > size || index<0){ //we can add to size index, that's just adding to the end
     throw new IndexOutOfBoundsException("ADDrequested index out of bounds: " + index);
   }
   if (index == size){
     this.add(value);
   }
   else if (index == 0){
     Node toAdd = new Node(value, start, null);
     start.setPrev(toAdd);
     start = toAdd;
     size +=1;
   }
   else{ //check, adding to the beginning of the list will make error since index-1 is outofbounds
     Node addTo = getNthNode(index);
     Node toAdd = new Node(value, addTo, addTo.prev());
     addTo.prev().setNext(toAdd);
     addTo.setPrev(toAdd);
     size+=1;
   }
 }

 public E remove(int index) throws IndexOutOfBoundsException{
   if (index >= size || index<0){
     throw new IndexOutOfBoundsException("REMOVErequested index out of bounds: " + index);
   }
   E toReturn = this.get(index);
   if (index == size-1){
     end = end.prev();
     end.setNext(null); //if this wasn't done, toString would print out old ends
   }
   else if (index == 0){
     start = start.next();
     start.setPrev(null);
   }
   else{
     //you have to reset the prev first, because getNthNode depends on the setNext to get node. once you setNext from the previous
     //to pass over the current one, (index+1) is now out of bounds in some cases, like removing a middle element from a list of length 3
     Node toRemove = getNthNode(index);
     toRemove.next().setPrev(toRemove.prev());
     toRemove.prev().setNext(toRemove.next());
     //getNthNode(index+1).setPrev(getNthNode(index-1));
   }
   size-=1;
   return toReturn;
 }

 public boolean remove(E value){
   //this function is 0[n^2] !!! so slow
   try{
     remove(this.indexOf(value));
   }catch(IndexOutOfBoundsException e){
     return false;
   }
   //size-=1; if this was kept, removing by value would make size-=2
   return true;
 }




 public String toString(){
   String toReturn;
   if (start == null && end == null){
     toReturn = "[]";
     return toReturn;
   }
   if (start == end){
     toReturn = "[" + start.toString() + "]";
     return toReturn;
   }
   Node current = start.next();
   toReturn = "[" + start.toString();
   while (current != null){
     toReturn += ", " + current.toString();
     current = current.next();
   }
   toReturn += "]";
   return toReturn;
 }

 public String reverseToString(){
   String toReturn;
   if (start == null && end == null){
     toReturn = "[]";
     return toReturn;
   }
   if (start == end){
     toReturn = "[" + start.toString() + "]";
     return toReturn;
   }
   Node current = end.prev();
   toReturn = "[" + end.toString();
   while (current != null){
     toReturn += ", " + current.toString();
     current = current.prev();
   }
   toReturn += "]";
   return toReturn;
 }

 public String debugToString(){
   String toReturn;
   if (size == 0){
     toReturn = "[]";
     return toReturn;
   }
   Node current = start.next();
   toReturn = "[" + start.debugToString();
   while (current != null){
     toReturn += ", " + current.debugToString();
     current = current.next();
   }
   toReturn += "]";
   return toReturn;
 }
}
