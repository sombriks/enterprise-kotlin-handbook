# Samples

Projects to see some concepts discussed on [main documentation][0054]

## What makes any code _enterprise code_ anyway?

Whenever the piece of code goes fast to production it can be called enterprise.
This is important because it indicates that we're not coding to explore a new
concept or to sample something in a academic paper. We're making money, not art.

To achieve speed, a high degree of code reuse is advised. The [DRY][0050] is
paramount here.

Thanks to [Open Source][0051], mostly in the form of [Apache Foundation][0052],
enterprise java flourished and thrived, making it one of the most prominent
ecosystem able to support this kind of solution.

One would argue that enterprise code is also testable code. Although this is
highly desirable, it's not a rule easily seen out there. In tis guide we'll
cover tests because it builds up our trust in the delivered code.

## How to execute the sample projects

Every project folder has a [README.md][0053] with minimal instructions.
Depending on the project, it can be just a few terminal commands using the
interpreter or the compiler directly, or detailed instructions on how to spin a
container or compose IaC file.

The README file will often explain:

- dependencies
- how to build/compile
- how to execute
- present a small noteworthy list of interesting trivia

## What is the proper order to study those projects

Go from the first project to the last one, they are numbered to help on that.
Each project will introduce a new concept which might or might not appear again
in the next one, but will be useful when building other things.

[0050]: https://en.wikipedia.org/wiki/Don%27t_repeat_yourself
[0051]: https://en.wikipedia.org/wiki/Open_source
[0052]: https://www.apache.org/
[0053]: project-001-hello-world/README.md
[0054]: ../docs/README.md
