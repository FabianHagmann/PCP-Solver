# PCP Solver (Post Correspondence Problem)
For a description of the problem see [Wikipedia](https://en.wikipedia.org/wiki/Post_correspondence_problem).

## Description of the Implementation
The solver is implemented in Java and is able to find all vaild combinations of pairs up to a length of 20 pairs.
Pairs can be __inputed via command line arguments__. There is also an option to __display the shortest invalid arguments__
for proving non-existence of a valid solution.

## Usage
1. Pairs can be inputed via command line arguments in the format like:
__(<Upper_Argument>,<Lower_Argument>)__
2. With the option "-f" or "--fails" the shortest invalid solution will be printed
   
   By default the 10 shortest invalid solutions will be printed. 
   
   If the flag is followed by a number, the n shortest solutions will be printed.
3. With the option "-l" or "--length" the maximal length up to which solution will be searched can be 
   configured.
   
   By default solutions up to a length of 20 will be searched.
   
   If the flag is followed by a number, solutions up to a length of n will be searched
4. All found valid solutions will be printed to the command line