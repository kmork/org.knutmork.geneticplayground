Gomoku

Description:
A game for testing out genetic algorithms using Scala

References:
* http://en.wikipedia.org/wiki/Gomoku
* http://www.unimaas.nl/games/files/phd/SearchingForSolutions.pdf

Functionality:
* Possible to play human to computer
* Various computer players - manually coded and using genetic algorithms
* Possible to play computer to computer
* Dynamic size of board - but always keep playing as efficient as possible. 
* Able to stop generations after a while, then pick it up again later
on - since it takes very long time to produce good players

Rules:
These differ from the official rules:
* Free-style gomoku
* No-limit to board size. Officially the board is 15x15, however it felt simpler to not have to deal with board boundaries
* Black (X) will always start at the center position - it is a consequence of a dynamic board size
* You can only place you piece on an empty neighboring cell to a non-empty cell (either diagonal, vertical, or horizontal)
This limits the possibilities by a major factor (performance boost), and besides that is how I always have played it

Todo:
* Must have a mechanism to verify the evolution for each generation or generation set
* Black (or first player) will necessarily have a separate strategy from white, since it is proved to always win with perfect play
Maybe the individuals should be divided between black and white colonies ? (No pun intended)
* Must implement concurrency and possibility to calculate fitness on machines in cluster
* Must implement transparency for symmetry (is it 8 variants ?)

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
matches. This will randomly pick a possible cell.
* The initial individual will start with only 0 values, and then gradually mutate. 
Otherwise it is nearly impossible to be able to match a pattern. 0 value should be weighted more than the other values (not implemented yet).
* Fitness score is based on game won when genes other than the default one has been in use.

* What is optimal mutation rate ? A wild guess is between 0.07 and
0.10 % (maybe it should be dynamic?)
* What is the optimal number of initial individuals ? Let's start with
100 or 200
* What is the minimum number of generations ? Till it is good enough ?
* How to calculate the efficiency while producing new generations - i.e. benchmark ?
 - They currently only compete against each other in the same generation
 - Maybe to see number of hits within the real genes - not the default gene ?

Note to self:
Is it possible to start off with a small number of genes, then expand for later generations ?
Then the individual will gradually become more complex in a natural way.