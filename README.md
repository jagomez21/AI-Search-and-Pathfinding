# HW3: Search and Pathfinding
=================================

## Description:
This program performs 3 different algorithms which are, breadth-first search, iterative deepening search and a* search on a search space that is given in a txt file.

txt file format:

5 7            <- dimensions
1 2            <- start node
4 3            <- goal node
2 4 2 1 4 5 2  <- map
0 1 2 3 5 3 1
2 0 4 4 1 2 4
2 5 5 3 2 0 1
4 3 3 2 1 0 1

## Instructions:
In order to run the executable file, use the following command:

`java –jar hw3.jar <txt file> <algorithm>`

where txt file is the name of file being used to obtain the search space.

For the algorithm argument, you can use the following options:
  
`
"BFS" - (Breadth-first search)
"IDS" - (Iterative deepening search)
"AS" - (A* search)
`

## Notes:
* Txt file has to be in the same location as the hw3.jar
* When typing the name of the file, also include the extension .txt
