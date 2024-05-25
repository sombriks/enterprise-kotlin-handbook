# Samples

Projects to see some concepts discussed on [main documentation][0054]

## What makes any code _enterprise code_ anyway?

Whenever the piece of code goes fast to production, following previsible, boring
standards of quality and style, it can be called _enterprise_.

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

Quality and style are historically solved by someone's opinion transmuted into a
standard, like [SOLID][0056] (or [GRASP][0057] before that), and tooling like
style checkers, formatters and, of course, test and coverage tools.

## How to execute the sample projects

Every project folder has a [README.md][0053] with minimal instructions.
Depending on the project, it can be just a few terminal commands using the
interpreter or the compiler directly, or detailed instructions on how to spin a
container or compose IaC file.

The README file will often explain:

1. Dependencies
1. How to build/compile
1. How to execute/test
1. Present a small noteworthy list of interesting trivia
1. Sometimes there are [exercises to perform][0055]

## What is the proper order to study those projects

Go from the first project to the last one, they are numbered to help on that.
Each project will introduce a new concept which might or might not appear again
in the next one, but will be useful when building other things.

When reading the [docs][0054], there will be links to the sample projects, so
read de guide and it will kick you into the samples time to time.

[0050]: https://en.wikipedia.org/wiki/Don%27t_repeat_yourself
[0051]: https://en.wikipedia.org/wiki/Open_source
[0052]: https://www.apache.org/
[0053]: project-001-hello-world/README.md
[0054]: ../docs/README.md
[0055]: ./project-010-spring-example/README.md
[0056]: https://en.wikipedia.org/wiki/SOLID
[0057]: <https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)>
