## ComputerOrganisationProject
We used Java programming language for our project. 
The Project consists of 3 Java classes :
### 1) simulator :
  This is the class from which the project is run. The text file to be simulated is passed inside the main method of this class.
  It has all the code needed to run the project programs successfully with the other classes connected to it.
  
  
 ### 2) Register :
  The Register class has data structures that depict the registers and memory. 
  It also has methods used to understand the instructions , register names of the program .
  This class evaluates these instructions and displays the registers and the memory in the terminal. 
  There are 32 registers in this project as in RISC V.
  The Register class also has code to print the registers and memory elements in the terminal. 
  The registers with their corresponding numbers and the Instructions with their corresponding numbers are given in the comment, these numbers are used to recognize the instructions and the registers. 
  
 ### 3) Parser :
  This class mainly takes the file and stores the instructions for further processing on it.
  It also removes the comments and blank lines in the program.
  This project only supports single-line comments.
  
 ### 4) PipeLine :
  This class contains the methods to print the pipeline with forwarding and without forwarding. It also contains the checkInstruction function that checks the register dependency of two instructions needed to print the stalls in the pipeline.
  
 ### We print the register, memory, and pipeline within the terminal itself.
  
 ### Other things to do :
 As of now, we have not worked on multiline comments, taking user Input in the program, and storing strings in the memory.
 
 
 ### Constraints for running the program :
1) The program must start with _start: on the first code line.
2) The program must include .data and .text sections every time.

 
  
