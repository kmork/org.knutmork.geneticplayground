Title: 
Gomoku

Description:
A game for testing out genetic algorithms using Scala


Functionality:
* Possible to play human to computer
* Various computer players - manually coded and using genetic algorithms
* Possible to play computer to computer
* Dynamic size of board - but always keep playing as efficient as possible
* Able to stop generations after a while, then pick it up again later
on - since it takes very long time to produce good players


Genetic model:
* Maximum matrix size which can be matched is 9*9
* This will require 80 digits per gene from 0 to 3 where 0 = don't
care, 1 = not marked, 2 = my mark, 3 = opponent mark
* Automated random chosen pos if several is possible
* The order of genes specifies the priority for choosing one solution
above another. First gene is 1. pri and so on.
* The number of genes will have an impact of the performance, high
number => slow performance. We start off with "only" 30 genes
* The last gene of every individual at all times are a default
configured one, which guarantees a legal move - in case none other
matches.
 This will randomly pick a possible cell.

* What is optimal mutation rate ? A wild guess is between 0.07 and
0.25 % (maybe it should be dynamic?)
* What is the optimal number of initial individuals ? Let's start with
100 or 200
* What is the minimum number of generations ? Till it is good enough ?
* How to calculate the efficiency while producing new generations - i.e. benchmark ?
 - They currently only compete against each other in the same generation
 - Maybe to see number of hits within the real genes - not the default gene ?

Note to self:
Is it possible to start off with a small number of genes, then expand
for later generations ?
Then the individual will gradually become more complex in a natural way.