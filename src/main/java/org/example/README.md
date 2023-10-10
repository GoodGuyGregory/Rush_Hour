# Bug Rush Instances
Bart Massey 2023-10

These are instances of "Bug Rush", a name for the special
case of [Rush
Hour](https://en.wikipedia.org/wiki/Rush_Hour_%28puzzle%29)
with vehicles of length one only.

## Format

The instances are in an ASCII format described by and
intended for the homework in my AI class. As a brief
example:

    ---
    | -
    >||
     ||

is a 3×3 Bug Rush instance. The top row indicates the width
of the puzzle and can be otherwise ignored. In the remaining
rows, `|` indicates a vertically-moving vehicle and `-` a
horizontally-moving one. The target car is represented by
`>` and must be moved to the right edge of the lot: it can
move left or right.

## Solutions

The number of moves for a shortest solution for each
instance given here is as follows:

    dumb2x2.bugs: 3
    first5x5.bugs: 17
    fun3x3.bugs: 12
    fun3x4.bugs: 11
    hardest3x3.bugs: 15
    hardest3x4.bugs: 34
    some5x7.bugs: 42
    unsat3x4.bugs: unsat
    unsat5x7.bugs: unsat



# License

This work is licensed under the "MIT License". Please see the file
`LICENSE.txt` in this distribution for license terms.
