# JScheme - Scheme interpreter written in Java


```
  Welcome to
 *****************************************
   ####  ###   ### #   # #### #   # ####
      # #     #    #   # #    ## ## #
      # #### #     ##### #### # # # ####
      #    #  #    #   # #    #   # #
   #### ###    ### #   # #### #   # ####
 *****************************************
```


This is my implementation of a Scheme interpreter which has been developed for the lecture _Design und Implementierung fortgeschrittener Programmiersprachen_
offered by Claus Gittinger at [Stuttgart Media University](https://www.hdm-stuttgart.de/) (course of studies: Computer Science and Media). 
On the master branch, you will find a version that has been implemented in classical style, whereas the _cont\_passing_ branch offers tail call
optimization in the form of continuation passing style. 

<br>

## CI status

Travis CI: [![Build Status](https://travis-ci.org/PaddySmalls/JScheme.svg?branch=master)](https://travis-ci.org/PaddySmalls/JScheme)

<br>

## Getting started

### 1) Requirements
JScheme uses [Maven 3](https://maven.apache.org/) for build management. If Maven is not already present on your machine get it and
install it first.  

### 2) Clone repository
Checkout the latest version of JScheme:   

```$ git clone https://github.com/PaddySmalls/JScheme```

### 3) Start JScheme
Make sure that Maven has been added to your _PATH_ and run the following goals:

```$ mvn compile exec:java```

<br>

## Functional range

### 1) Data types
JScheme ships with a wide range of built-in data types:
* Strings
* Symbols
* Integers
* Floats
* Fractions
* Booleans
* Functions
* Lists

Special types:
* Void
* Nil 


---

### 2) Built-in functions
A great set of pre-defined functions are available out of the box. 

#### a) Arithmetic functions:

Addition:
```
>> (+ 1 2)
=> 3
```

Subtraction:
```
>> (- 3 2.0)
=> 1.0 
```

Multiplication:
```
>> (* 1 2)
=> 2
```

Division:
```
>> (/ 1 2)
=> 1/2
```

Absolute value:
```
>> (abs -42)
=> 42
```


#### b) Working with lists:

Create lists (1):
```
>> (cons 1 (cons (2 nil)))
=> '(1 2)
```

Create lists (2):
```
>> '(1 2)
=> '(1 2)
```

Get CAR of list:
```
>> (car '(1 2))
=> 1
```

Get CDR of list:
```
>> (cdr '(1 2))
=> '(2)
```

Check if any Scheme object is a list:
```
>> (cons? '(1 2))
=> #t
>> (cons? "not a list")
=> #f
```

#### c) Comparing Scheme objects:
```
>> (eq? 1 1)
=> #t
>> (eq? "not" "equal")
=> #f
```

---

### 3) Syntax

#### a) Defining variables:
```
>> (define foo 42)
>> foo
=> 42
```

#### b) Defining custom functions:
```
>> (define (add1 x) (+ x 1))
>> add1
=> <procedure:add1>
>> (add1 2)
=> 3
```

#### c) Using lambda and higher order functions:
```
>> (define (make-adder x) (lambda (y) (+ x y)))
>> (define add5 (make-adder 5))
>> add5
=> <procedure: anonymous lambda>
>> (add5 1)
=> 6
```

#### d) If statements:
```
>> (if #t "true" "false")
=> "true"
>> (if "" "true" "false")
=> "false"
```

#### e) Quoting your input:
```
>> (quote (+ 1 2 3 4 5))
=> '(+ 1 2 3 4 5)
>> 'foobar
=> foobar
```