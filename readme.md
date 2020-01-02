## FuzzUnit
FuzzUnit is a [JUnit 5 value source](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources)
intended to be used in parameterized tests. Its purpose is to execute fuzz testing during unit or
integration testing.

### What is JUnit
"JUnit is a unit testing framework for the Java programming language. JUnit has been important in
the development of test-driven development, and is one of a family of unit testing frameworks which
is collectively known as xUnit that originated with SUnit." *Source: Wikipedia*

FuzzUnit, as a value source of JUnit, brings fuzzing to the unit and integration phase.

### What is fuzzing?
According to Wikipedia: "Fuzzing or fuzz testing is an automated software testing technique that
involves providing invalid, unexpected, or random data as inputs to a computer program. The program
is then monitored for exceptions such as crashes, failing built-in code assertions, or potential
memory leaks. Typically, fuzzers are used to test programs that take structured inputs."

Fuzzing is also used by bad guys for finding weaknesses in software code. Therefore, every piece of
software should be fuzzed in order to find bugs before bad guys do. Usually, fuzzing is executed
by security researchers during application security testing (DAST), that is when the application
is tested in a preprod environment. However, this is inefficient and time consuming, for several
reasons:
* bugs are found late in the SDLC
* DAST tools are difficult to put in place
* Fuzz testing with DAST tools is not efficient

FuzzUnit brings the power of fuzzing early in the development process, during the unit or integration
tests. It requires no more than JUnit, which is already in place in most software projects.

### FuzzDB: the source of fuzzing data
The fuzzing data used in FuzzUnit come from [FuzzDB](https://github.com/fuzzdb-project/fuzzdb), "the
first and most comprehensive open dictionary of fault injection patterns, predictable resource
locations, and regex for matching server responses. FuzzDB was created to increase the likelihood of
finding application security vulnerabilities through dynamic application security testing." 

FuzzUnit brings the power of fuzz testing to the unit or integration phase, when developers mostly
feel responsible for the quality of their software code.


### Links
[User Guide](./docs/user-guide.md)
[Developer Guide](./docs/developer-guide.md)
[License](./LICENSE.md)

[JUnit 5](https://junit.org/junit5/)
[FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
