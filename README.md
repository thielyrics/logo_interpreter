# logo_interpreter

## Introduction ##

The initial code was written by Thierry and Kaleigh for an assignment for the CS250 Spring 2012 at Hendrix College taught by Dr. Ferrer. 

## Overview of the assignment ##

The goal of this project is to develop an IDE for the Logo programming language. The Logo language is designed as an introductory programming language for novice programmers. Logo implementations incorporate what is known as a Turtle Graphics system. In such a system, the user controls a graphical icon (the "turtle"; typically a triangle) that leaves a trail as it moves. In our implementation, the turtle can be controlled either by direct graphical controls or by statements in the Logo programming language.

## The Graphical Interface ##

The turtle GUI should have the following features: 
* A canvas upon which the turtle will follow its instructions to draw lines
* Clickable controls for the turtle:
    * Forward, turn left/right (all are fixed increments)
    * Pen up/down
    * Clear screen (moving turtle to the center of the drawing area)
    * Show/hide turtle
* A command-line interpreter that allows the user to type commands and receive immediate results
* Some means of communicating error messages to the user
* An editor that allows a user to type in a longer program
* The user should be able to stop a program while it is running
* This list of features is deliberately vague. Feel free to add other features that would be helpful to a user. Here are a few ideas:
    * An ability to control the speed of the turtle
      * The fastest speed should be instantaneous
    * An ability to change the turtle's pen color
    * A way to save drawings (perhaps even animated drawings) in addition to programs
    
## The Logo Programming Language ##

The Logo language includes the following standalone commands:

* Instructions that do not require any arguments:
    * pendown (pd): (Default) When the turtle moves, it leaves a trail
    * penup (pu): When the turtle moves, it does not leave a trail
    * home: Moves the turtle to the center of the screen
    * clearscreen (cs): Performs a home and clears the turtle's canvas
    * showturtle (st): (Default) Causes the turtle to appear
    * hideturtle (ht): Causes the turtle to disappear
* Instructions that require a single integer argument:
    * forward (fd): Moves the turtle forward by the given number of pixels
    * back (bk): Moves the turtle backward by the given number of pixels
    * left (lt): Turns the turtle to the left by the given number of degrees
    * right (rt): Turns the turtle to the right by the given number of degrees
    
The language also includes the following elements for aggregating commands:

  * Commands can be grouped into a bracketed list; for example, [fd 50 rt 90]. These bracketed lists will be used for grouping a block of commands to associate with the new language features below. Bracketed lists can be nested.
  * The repeat command allows the writing of definite loops. The word repeat is followed by a numerical expression, followed by a bracketed list. It will do the commands in the bracketed expression a number of times equal to the value of the numerical expression. Here are some examples of how repeat might be used:
    * repeat 4 [fd 50 rt 90]
    * repeat 3 [fd 50 rt 120]
    * repeat 2 [fd 30 rt 90 fd 60 rt 90]
  
  * The if and ifelse commands allow the writing of conditional statements. Each is followed by a boolean-valued condition. The if command is then followed by a single bracketed list, which is executed only if the condition is true. The ifelse command is followed by an additional bracketed list, to be executed if the condition is false. Here are some examples:
    * if 5 > 4 [fd 20]
    * ifelse 10 <= 5 [fd 20] [back 20]
    * if (5 = 4) or (3 < 7) [fd 20 rt 30]
    
* Implicit in the support of conditional statements is the implementation of boolean-valued comparisons. The keywords and, or, and not should all be supported with the standard precedence and semantics. Parentheses should also be available for grouping. The only boolean primitives are numerical comparison operators (<=, <, >=, >, =).

* Procedures can be added using the to command. The command to is followed by the procedure name, followed by zero or more formal parameters. Each formal parameter is an alphabetic string prefixed with a colon (:). The parameter list is followed by a bracketed list. Anywhere a number can be used as a parameter, a formal parameter may be used as well. Recursion is permitted. Here are some examples:
    * to triangle :size [repeat 3 [fd :size rt 120]]
    * to fractal :size [repeat 3 [fracside :size / 3 rt 120]]
    * to fracside :size [ifelse :size <= 1 [fd 1] [fd :size lt 60 fracside :size / 3 rt 120 fracside :size / 3 lt 60 fd :size]]
    
* As the preceding examples suggest, integer arithmetic should also be supported.
