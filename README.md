# OrmucoTask


## Hi,This is my sumitted answers for the tech test round.

The core file for each tasks are:
Assignment1.java (QuestionA)
Assignment2.java (QuestionB)
GeoRedisLruCache.java(QuestionC)

If you wanna check the the runnable code. Please download respective folders and find entry src/main/java at each folders.

##Explanation for each answer:

1.QuestionA
Using method called isOverlappedOrNot which takes four integer representing two intervals and use actual logic to check the possiblity of  intervals overlapping.

2.QuestionB
Assignment2 contains several methods for comparing and processing String  in a custom way, taking into account absolute values and signs.

As for each method:
- isValidInput Method uses regular expressions to check whether the input string consists of digits, dots. f the input string matches the pattern, it returns true; otherwise,it return false;

- compareAbsValue Method, it splits each input string using dots as separators. It would determine maximum length of the two arrays and checks if either of the input strings has more than two parts (indicating an invalid input) and returns -999.
  It then compares the absolute values of corresponding parts of the input strings (up to the length of len) and returns value(-1,1,0) according to comparing logic.

- isPositive method takes two version strings and checks if if the input string starts with a hyphen or not.

- compareProcess method is the one integrated other checking method.It would two version string,namely input1 and input2. First it would check if either of the input strings is not valid according to isValidInput method.Then it would check input string using compareAbsValue to compare absolute value. Finally, it would return the comparison result.

3.QuestionC

GeoRedisLruCache combine the functionality of a Local LRU cached and a distributed cache using Redis.  It handles caching and refreshing data in both the local cache and Redis while considering error handling for potential issues that may arise during cache operations.This LRU structure used LinkedHashMap to ensure the write and retrieve quickly as the time complexity of this algorithem is O(1). Some schedulers are called to remove the expired data periodically. we The explanations for each method are annotated in codes, please kindly check.

    



