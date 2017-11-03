# JScheme - Scheme interpreter written in Java

<br/>

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

Current branch: _master_ (continuation passing **disabled**)

<br>

## CI status

Travis CI: [![Build Status](https://travis-ci.org/apophis90/JScheme.svg?branch=master)](https://travis-ci.org/PaddySmalls/JScheme)

<br>

## Getting started with *JScheme*

Currently, there're several ways to get *JScheme* up and running on your local machine. Before you
can start using your preferred method, make sure you performed the following steps:

#### 1) Install Apache Maven 3
JScheme uses [Maven 3](https://maven.apache.org/) for build management. Ensure it's installed on your machine
and `MVN_HOME` is set properly, pointing to the installation directory.

#### 2) Clone repository
Checkout the latest version of JScheme:   

```$ git clone https://github.com/PaddySmalls/JScheme```

<br/>

### Running *JScheme* with Maven

Make sure Maven has been added to your _PATH_ and run the following goals:

```$ mvn compile exec:java```

<br>

### Running *JScheme* from JAR file
Instead of starting JScheme using Maven directly, you can also build a JAR and run it:

```
$ make
$ cd target/
$ java -jar JScheme-1.0-jar-with-dependencies.jar
```

<br>

### Running *JScheme* in Docker
Alternatively, you can also build a Docker image and launch *JScheme* from a Docker container.
That approach requires Docker to be installed on your machine:

```
$ make docker
$ docker run -i -t pkleindienst/jscheme
```

Running JScheme from Docker hub is even more easier and preserves you from having to install
and setup any other tooling except Docker:

```
$ docker run -i -t pkleindienst/jscheme
```

<br/>

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
