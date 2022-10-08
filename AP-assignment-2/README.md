# Assignment 2

In this assignment, I had to simulate an oversimplified, dummy E-commerce platform called FlipZon.

## Design
The blueprint can be described as follows:

         User
        |    |
    Admin  Customer
           |   |   |
        Normal Prime Elite

The `Customer` class also implements the interface `CustomerStatus`.

The remaining classes, namely `Admin`, `Cart`, `Category`, `Customer`, `Deal`, `FlipZon`, `Product` are at the same hierarchy level.

The classes `MainCLI`, `AdminCLI`, `CustomerCLI` provide a CLI for the app.

The program's entry point is `Main`, which calls `MainCLI` to start the CLI interface.


## Spec Sheet
Please check out [`problem-statement.pdf`](https://github.com/Zynade/CSE201-assignments/tree/main/AP-assignment-2/problem-statement.pdf) for the detailed product requirement.