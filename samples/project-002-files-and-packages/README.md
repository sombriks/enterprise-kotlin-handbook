# Files and packages

Sample on modularization

## Requirements

- java virtual machine 17 or newer properly installed
- kotlin compiler 1.9 or newer available in the command line

## How to compile and run the modules sample

```bash
kotlinc *.kt
kotlin File2Kt
```

## How to compile and run the package sample

```bash
kotlinc file3.kt
kotlin project003.File3Kt
```

## Noteworthy

- Unlike es6 modules, all symbols inside kotlin scripts are public by default.
- Packages _usually_ translates to folders. It depends on the operating system.
