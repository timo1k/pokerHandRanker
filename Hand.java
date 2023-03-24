import java.util.*;

public class Hand {
   /*************************** Public static field ***************************/
   // the name of this hand
   public static Map<Integer, String> nameMap = Map.of(
         8, "straight flush",
         7, "four of a kind",
         6, "full house",
         5, "flush",
         4, "straight",
         3, "three of a kind",
         2, "two pair",
         1, "pair",
         0, "nothing");

   /****************************** Private fields ******************************/
   // A list of cards in this hand. E.g., [8C, TS, KC, 9H, 4S] is a list of cards.
   private ArrayList<Card> hand = new ArrayList<>();

   // A map of each card's rank to the number of times it occurs in the hand. E.g,
   // "7D 7S 5D 7C 5H" has two 5s and three 7s, so its frequency map is {5=2, 7=3}.
   private Map<Integer, Integer> rankFrequency = new HashMap<>();

   // Ranks of the cards in this hand in reverse-sorted order. E.g., the hand
   // "8H KC 2S 3S QD" has ranks [13, 12, 8, 3, 2].
   private Integer[] cardRanks = new Integer[5];

   // The integer value of this poker hand in the range from 0 to 8. 8 corresponds
   // to a straight flush and 0 to a nothing hand.
   // (See the instructions or the Wikipedia article for poker hand rankings.)
   private int handValue;

   /********************************  Constructor ********************************/
   // Creates a Hand object from an input string representing a hand.
   public Hand(String cards) {
      // hand.removeAll(hand); // clear this hand to create a new one
      for (String token : cards.split("\\s+")) {
         Card card = new Card(token);
         hand.add(card);
      }
      check();                 // make sure this is a valid 5-card hand
      buildRankFrequencyMap(); // generate a map of card-rank frequencies
      buildRankArray();        // generate a card-rank array in reverse-sorted order
      determineHandRank();     // the rank of this hand on the scale 0 to 8
   }

   /*******************************  Private Methods *******************************/
   // How many times does each rank repeat in this hand? Maps each rank to the number
   // of times it occurs in this hand and returns the map. E.g., "KD KS 9H JC 9S" has
   // two Ks, two 9s, and one J, so its map is {13=2, 11=1, 9=2}.
   private void buildRankFrequencyMap() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */

      for(Card card : hand){

         if(rankFrequency.containsKey(card.getRank())){ //check if map contains a card's key then add +1 to value
            rankFrequency.put(card.getRank(), (rankFrequency.get(card.getRank())) +1);
            //.put overwrites (key:rank of card, newValue:.get value of rank and add 1 to its value)
         }
         else{ //create new key pair
            rankFrequency.put(card.getRank(), 1);
         }
      }
   }

   // generate a list of ranks in decreasing order (the highest rank first, the lowest last).
   private void buildRankArray() {
      int i = 0;
      for (Card c : hand){
         cardRanks[i] = c.getRank();
         i++;
      }
      Arrays.sort(cardRanks, Collections.reverseOrder());

      // the only exception is a low straight (called a wheel) where Ace
      // counts as 1, not as 14. So if the hand is "Ace, 5, 4, 3, 2"
      // we want to return "5, 4, 3, 2, 1" instead of "14, 5, 4, 3, 2"
      if (Arrays.equals(cardRanks, new Integer[]{14, 5, 4, 3, 2}))
         cardRanks = new Integer[]{5, 4, 3, 2, 1};
   }

   // determine the ranking of this hand
   private void determineHandRank() {
      if      (this.isStraightFlush()) handValue = 8;
      else if (this.isFourOfaKind())   handValue = 7;
      else if (this.isFullHouse())     handValue = 6;
      else if (this.isFlush())         handValue = 5;
      else if (this.isStraight())      handValue = 4;
      else if (this.isThreeOfaKind())  handValue = 3;
      else if (this.isTwoPair())       handValue = 2;
      else if (this.isPair())          handValue = 1;
      else                             handValue = 0;
   }

   // Check if the hand has 5 cards and if each card is in the correct format.
   private void check() {
      if (hand.size() != 5)
         throw new RuntimeException("Not a 5-card hand. Try again.");
      Set<String> cardSet = new HashSet<>();
      for (Card c : hand) cardSet.add(c.toString());  // (card validity itself is checked in Card.java)
      if (cardSet.size() != 5)
         throw new RuntimeException("Duplicate card! Try again.");
   }

   /***********************************  Getters ***********************************/
   // returns and Integer array of the card ranks in this hand in descending order
   public Integer[] getCardRanks() { return cardRanks; }

   // returns the frequency map for this hand
   public Map<Integer, Integer> getRankFrequency() { return rankFrequency; }

   // returns the list of Cards in this hand
   public ArrayList<Card> getHand() { return hand; }

   // return the rank of this hand
   public int getHandValue() { return handValue; }

   /*********************************************************************************
                        Methods that check for the hand categories
    ********************************************************************************/
   // Is this hand a straight flush?
   public boolean isStraightFlush() { return isFlush() && isStraight(); }

   // Is this hand four of a kind?
   public boolean isFourOfaKind() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      int four =0;
      for(int freq : rankFrequency.values()){
         if(freq ==4)
            four++;
      }

      return four == 1;
   }

   // Is this hand a full house (3 cards of the same rank & 2 cards of the same rank)?
   public boolean isFullHouse() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      for(int freq : rankFrequency.values()){
         if(freq == 3 && isPair()){
            return true;
         }
      }
      return false;
   }

   // Is this hand a flush? (Can also be a straight.)
   public boolean isFlush() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      for(int i = 1; i < cardRanks.length; i++ ){
         if(hand.get(i-1).getSuit() != hand.get(i).getSuit()){
            return false;
         }
      }

      return true; // delete this line and substitute it with your code
   }

   // Is this hand a straight? (Can also be a flush.)
   public boolean isStraight() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      int temp = cardRanks[0];
      for(int i = 1; i < cardRanks.length; i++ ){
         if( (temp-1) == cardRanks[i] ){
            temp-=1;
         }else{
            return false;
         }
      }
      return true; // delete this line and substitute it with your code
   }

   // Is this hand three of a kind (also called a set or trips in poker)?
   public boolean isThreeOfaKind() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      for(int freq : rankFrequency.values()){
         if(freq == 3 && !isPair()){
            return true;
         }
      }
      return false;
   }

   // Does this hand contain exactly two pairs?
   public boolean isTwoPair() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      int pair = 0;
      for(int freq : rankFrequency.values()){
         if(freq == 2){
            pair++;
         }
      }
      return pair == 2;
   }

   // Does this hand contain exactly one pair?
   public boolean isPair() {
      /* YOUR CODE HERE - IMPLEMENT THIS METHOD */
      int pair = 0;
      for(int freq : rankFrequency.values()){
         if(freq == 2){
            pair++;
         }
      }
      return pair ==1;//if only 1 pair then true
   }
   public void checkHighCard(Hand hand2){//main "algo" otherwise special cases
      int hand1TopRank = this.cardRanks[0];
      int hand2TopRank = hand2.cardRanks[0];
      this.printStatement(hand1TopRank, hand2TopRank, hand2);
   }
   public void printStatement(int hand1TopRank, int hand2TopRank, Hand hand2){
      if(hand1TopRank>hand2TopRank){
         System.out.println("Player 1: " + this + "  (" + Hand.nameMap.get(this.getHandValue()) +
                 ")\nPlayer 2: " + hand2 +  "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                 ")\n\t\t\t    Player 1 wins.\n");

      }else if(hand2TopRank>hand1TopRank){
         System.out.println("Player 1: " + this + "  (" + Hand.nameMap.get(this.getHandValue()) +
                 ")\nPlayer 2: " + hand2 +  "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                 ")\n\t\t\t    Player 2 wins.\n");
      }
      else{
         System.out.println("wrong or yet to test this case");
      }
   }
   public void checkMultiples(Hand hand2, int howManyMultiples){
      int hand1TopRank =0;
      int hand2TopRank =0;
      for ( Map.Entry<Integer, Integer> entry : this.rankFrequency.entrySet() ){
         int key = entry.getKey();
         int value = entry.getValue();
         if(value == howManyMultiples){
            int i = 0;
            for (Card c : this.hand){
               if(this.cardRanks[i] == key){
                  hand1TopRank = c.getRank();
                  System.out.println(hand1TopRank);
                  break;
               }
               i++;
            }
            break;
         }
      }
      for ( Map.Entry<Integer, Integer> entry : hand2.rankFrequency.entrySet() ){
         int key = entry.getKey();
         int value = entry.getValue();
         if(value == howManyMultiples){
            int i = 0;
            for (Card c : hand2.hand){
               if(hand2.cardRanks[i] == key){
                  hand2TopRank = c.getRank();
                  System.out.println(hand2TopRank);
                  break;
               }
               i++;
            }
            break;
         }
      }
      this.printStatement(hand1TopRank, hand2TopRank, hand2);
   }

   public void ifIsTie(Hand hand2){
      //check which is higher rank
      if(this.isStraightFlush()){
         checkHighCard(hand2);
      }
      else if(this.isFourOfaKind()){
         checkMultiples(hand2, 4);
      }
      else if(this.isFullHouse()){
         checkMultiples(hand2,3);
      }
      else if(this.isFlush()){
         checkHighCard(hand2);
      }
      else if(this.isStraight()){
         checkHighCard(hand2);
      }
      else if(this.isThreeOfaKind()){
         checkMultiples(hand2, 3);
      }
      else if(this.isTwoPair()){
         checkMultiplesOnlyForTwoPair(hand2, 2);
      }
      else if(this.isPair()){
         checkMultiples(hand2, 2);
      }
      else if(this.isNothing()){
         checkHighCard(hand2);
      }
   }
   public void checkMultiplesOnlyForTwoPair(Hand hand2, int howManyMultiples){
      int hand1TopRank =0;
      int hand1TopRankPairB = 0;
      int hand2TopRank =0;
      int hand2TopRankPairB =0;
      int i = 0;
      int goToSecondPair = 0;
      for ( Map.Entry<Integer, Integer> entry : this.rankFrequency.entrySet() ){
         int key = entry.getKey();
         int value = entry.getValue();
         if (goToSecondPair == 0){
            if(value == howManyMultiples){
               for (Card c : this.hand){
                  if(this.cardRanks[i] == key){
                     hand1TopRank = c.getRank();
                     goToSecondPair+=1;
                     break;
                  }
                  i++;
               }
               break;
            }
         }
         else{
            if(value == howManyMultiples){
               for (Card c : this.hand){
                  if(this.cardRanks[i] == key){
                     hand1TopRankPairB = c.getRank();
                     break;
                  }
                  i++;
               }
               break;
            }
         }
      }
      if(hand1TopRank < hand1TopRankPairB){
         hand1TopRank = hand1TopRankPairB;
      }
      i = 0;
      goToSecondPair= 0;
      for ( Map.Entry<Integer, Integer> entry : hand2.rankFrequency.entrySet() ){
         int key = entry.getKey();
         int value = entry.getValue();
         if (goToSecondPair == 0){
            if(value == howManyMultiples){
               for (Card c : hand2.hand){
                  if(hand2.cardRanks[i] == key){
                     hand2TopRank = c.getRank();
                     break;
                  }
                  i++;
               }
               break;
            }
         }
         else{
            if(value == howManyMultiples){
               for (Card c : this.hand){
                  if(this.cardRanks[i] == key){
                     hand2TopRankPairB = c.getRank();
                     break;
                  }
                  i++;
               }
               break;
            }
         }
      }
      if(hand2TopRank < hand2TopRankPairB){
         hand2TopRank = hand2TopRankPairB;
      }
      this.printStatement(hand1TopRank, hand2TopRank, hand2);
   }

   // A nothing hand
   public boolean isNothing() {
      return !(isFlush() || isStraight() || isFullHouse()
              || isFourOfaKind() || isThreeOfaKind()
              || isPair() || isTwoPair());
   }

   @Override
   public String toString() { return hand.toString(); }
}
