# pokerHandRanker
#### A mini project I wrote in java!

# This is from project euler: https://projecteuler.net/problem=54

#### It takes input from a file containg various Hands
### ex: 7D 7S 5D 7C 5H 7S 8S 5S AS 6S  (full house vs. flush - full house should win)
#### 7C 4H 7D 7H 7S AS JC JH JD JS  (both four of a kind; p2 wins as jack is higher)

#### Then from that file the two hands are ranked and a winner is determined!

# Card Class ranks a card and gives it a value:
#### ex of ranking: 
#### Name, Symbol, IntegerValue
#### Nine, 9, 9
#### Ten, T, 10
#### Jack, J, 11

# Hand Class
#### A hand is made up of 5 cards, and that Hand is assigned a value:
#### ex of ranking: 
#### HandRank, HandName, Def./Example
#### 8, Straight Flush, Cards of the same suit and consecutive ranks: J♣, T♣, 9♣, 8♣, 7♣
#### 7, Four of a Kind, Four cards of the same rank: 9♠, 9♢, 9♣, 9♡, 5♠
#### 1, Pair, Two cards of the same rank (others – distinct): J♡, J♡, T♢, 4♠, 2♡


# CompareDistinctCategoryHands
##### Compares the hands from the file and determines the winner!
