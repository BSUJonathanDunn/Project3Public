# Project 3: Experiments with Hashing

* Author: Jonathan Dunn
* Class: CS321 Section #4001
* Semester: Fall 2026

## Overview

This program uses a hash table with open addressing and one of three types 
of data: random integers, date objects, or strings from a word list; to 
study how the load factor can affect the average number of probes required
for linear probing and double hashing.

## Reflection

Overall, this project wasn't the hardest to write or conceptualize. The
difficulty came from trying to remember how to properly utilize inheritance,
therefore there was a lot of second guessing of my own implementation. This
resulted in inefficient decomposition and a lot of similar code between the
the table using linear probing and the table using double hashing. This
required a bit of additional research to really remember how inheritance
works.

At first I was very hesitant to pass a generic Hashtable object into my methods.
I did remember that if you assign a variable with the parent class and then
assign the variable with a child object, that the variable would only be
able to access the methods of the parent, not of the child. I had to take
a long look at my code and the requriements and it hit me that the only method
that linearprobing and doublehashing actually utilize is their own definition
of an abstract method that is provided by their parent: hashtable. So, there
was no reason as to why I couldn't just use one method to work with both tables
if I pass them as a Hashtable object, this significantly helped me with the
decompisition required by this project.

## Compiling and Using

To utilize this program, first compile the program in the terminal with this
command:

```
javac HashtableExperiment.java
```

Once compilation is done, the program can be utilized in the terminal in this
format:

```
java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]
```

Where:

```
       <dataSource>: 1 -> utilize random integers for the experiment
                     2 ==> Utilize a date object as a long
                     3 ==> a string from a given word list
       <loadFactor>: The ratio of objects to table size, 
                       denoted by alpha = n/m
       <debugLevel>: Optional argument. Defaults to a value of 0 if not provided.
                     0 ==> Prints out the results of each table:
                            The twin prime used to create the size of the hash table
                            The data type populating the table
                            The loadfactor
                            The size of each hashtable
                            The number of elements inserted during the experiment
                            The number of duplicates discovered during the experiment
                            The average number of probes for each table.
                     1 ==> Gives the results from level 0 and prints the elements in
                           each table to it's own txt file.
                     2 ==> Prints the result of each insert attempt for both tables
                           during the runtime of the experiment.
```


## Results 
<br>
Testing for this experiment was run through the finished run-tests.sh script and
generated for these tables with generate-results.sh.

The three following tables give the results of each run for the liner probing
table and the double hashing table. Each table represents one of the three 
data types represented in this experiment: random integers, date objects and
strings. Each table provides the load factor and the average number of probes
resulting from the load factor used.

<br>

### Dates

| Load Factor | Linear Probing (Avg. Probes) | Double Hashing (Avg. Probes) |
|:-----------:|:----------------------------:|:----------------------------:|
| 0.50        | 1.49                         | 1.54                         |
| 0.60        | 1.55                         | 1.68                         |
| 0.70        | 1.60                         | 1.86                         |
| 0.80        | 1.80                         | 2.23                         |
| 0.90        | 2.33                         | 2.89                         |
| 0.95        | 2.84                         | 3.59                         |
| 0.99        | 3.98                         | 5.20                         |

<br>


### Random Numbers

| Load Factor | Linear Probing (Avg. Probes) | Double Hashing (Avg. Probes) |
|:-----------:|:----------------------------:|:----------------------------:|
| 0.50        | 1.50                         | 1.38                         |
| 0.60        | 1.75                         | 1.53                         |
| 0.70        | 2.16                         | 1.72                         |
| 0.80        | 3.03                         | 2.01                         |
| 0.90        | 5.76                         | 2.55                         |
| 0.95        | 11.01                        | 3.14                         |
| 0.99        | 40.55                        | 4.71                         |

<br>


### Word List

| Load Factor | Linear Probing (Avg. Probes) | Double Hashing (Avg. Probes) |
|:-----------:|:----------------------------:|:----------------------------:|
| 0.50        | 1.60                         | 1.39                         |
| 0.60        | 2.15                         | 1.53                         |
| 0.70        | 3.60                         | 1.72                         |
| 0.80        | 6.71                         | 2.02                         |
| 0.90        | 19.81                        | 2.57                         |
| 0.95        | 110.59                       | 3.19                         |
| 0.99        | 471.67                       | 4.70                         |

<br>

We can notice that in general, that linear probing will produce higher average probing attempts when
the load factor is higher. This is expected as the larger load factor specifies that more table positions
will be filled. As linear probing probes the table in sequence, then depending on where the initial probe
lands, the number of probing attempts per element can grow substantially. The dates table showed the opposite
trend, but not by as large a marging as the other two tables. This could be due to the date elements favoring
the linear probing function once the keys were transfered to a hash code. However, we can safely assume
that as the load factor increases, the double hashing probing method can be more more efficient in filling
up the hash table.




## Sources used

Outside of any in-class material, I checked a bit more about using hashCode().
<br>
https://www.baeldung.com/java-hashcode

<br>
<br>
And a refersher on polymorphism to remember about passing a child object as a parent
object within a parameter for a function.
<br>
https://www.w3schools.com/java/java_polymorphism.asp

----------
