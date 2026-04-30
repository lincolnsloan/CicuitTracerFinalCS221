****************
* Final Project: Circuit Tracer
* CS-221
* 05/01/26
* Lincoln Sloan
**************** 

OVERVIEW:

 Concisely explain what the program does. If this exceeds a couple
 of sentences, you're going too far. The details go in other
 sections.


INCLUDED FILES:

 List the files required for the project with a brief
 explanation of why each is included.

 e.g.
 * Class1.java - source file
 * Class2.java - source file
 * README - this file


COMPILING AND RUNNING:

 Give the command for compiling the program, the command
 for running the program, and any usage instructions the
 user needs.
 
 These are command-line instructions for a system like onyx.
 They have nothing to do with Eclipse or any other IDE. They
 must be specific - assume the user has Java installed, but
 has no idea how to compile or run a Java program from the
 command-line.
 
 e.g.
 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac Class1.java

 Run the compiled class file with the command:
 $ java Class1

 Console output will give the results after the program finishes.


ANALYSIS:
 i.
 The order in which paths are retrieved and built upon is completely different when using the search algorithm with a stack vs. a queue. When using a stack the algorithm finds the most recent path, moves it further in all possible directions, then takes one of these new paths created (whichever was made last) and repeats the process. The program basically rushes to target before going back and altering its path from the path's end to its beginning until it's searched through every possible path for the best one.
 When using the queue the program instead finds all possible paths from a given path end and stores them. Then it goes back to the oldest path which hasn't been explored further and does the same thing. The queue version essentially branches out (mostly) simultaneously until reaching either a dead end or the target.
 
 ii. 
 The total number of search states for both stack and queue is the same due to the algorithm not stopping the search until all paths are searched ("while (!stateStore.isEmpty())"). So, unless optimized they will both search every search state.

 iii. 
 Because the stack algorithm ignores branching paths and keeps going until it can't, it is more likely to spend time exploring a dead-end path than queue. This isn't always the case though as stack and queue may move more frequently in different directions (If you store branches from your path in a certain order, stack will continue the path from the last added, and queue will continue from the first added), so if stack is lucky it might be sent in the right direction while queue is left exploring every possible path.

 iv. 
 Since the queue explores all possible paths of a certain length before increasing the length, the first solution found is guarenteed to have the least length.

 v. 
 The queue needs to store all branches that haven't been expanded on at the same time. The stack only needs to store the unexplored branches which it skipped over getting to its current path. This means that the queue requires more memory.

 vi. 
 In the case of this algorithm, n would be the number of open spaces on the board since it needs to get through every path over these spaces before ending. After every step, you have anywhere from 0 to 3 next steps to take, and the same from each of those. In a worst case scenario you have about 3 choices on n number of open spaces, so the number of possible paths would be around 3^n. This doesn't take into account paths smaller than n or having less path options on edges, but the order would be 
 O(3^n).


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

 Explain the main concepts and organization of your program so that
 the reader can understand how your program works. This is not a repeat
 of javadoc comments or an exhaustive listing of all methods, but an
 explanation of the critical algorithms and object interactions that make
 up the program.

 Explain the main responsibilities of the classes and interfaces that make
 up the program. Explain how the classes work together to achieve the program
 goals. If there are critical algorithms that a user should understand, 
 explain them as well.
 
 If you were responsible for designing the program's classes and choosing
 how they work together, why did you design the program this way? What, if 
 anything, could be improved? 


TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?

 I created a java file called MethodTester.java. For the CircuitBoard.java constructor used the valid and invalid test files provided to us in the assignment overview to loop through all 12 invalid files to make sure they threw the correct exceptions, then loop through all 10 valid files along with CircuitBoard.toString() to make sure they created the board correctly. When I started CircuitTracer.java I used the same MethodTester.java file to test my usage statement print out. I also used this file as well as the main file itself to find out how to validate the command line arguments. When I built the algorithm in CircuitTracer I started using the CircuitTracerTester nearly entirely. The files and file outputs pairs that it put in my workspace were helpful in figuring out exactly what was happening. 
 While testing the algorithm I kept failing a lot of tests and wasn't sure why for a long time until I studied my code and found that in the while loop in the algorithm in CircuitTracer I was initializing new TraceStates using the original board instead of the previous TraceState. This is because I copy/pasted the if statements from earlier in the code and accidentally didn't change that part. I also had the x and y assignments to startRow and startCol in that method flipped. Once I fixed these two things and my output format I went from 60% of tests passed to 97.7%.


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
