# Project 1: The IDLE Development Environment

#### Due date: Thursday, April 11 2024

## Foreword

One of primary objectives of this new experimental course is for students
to develop the ability to read documentations effectively. Therefore, in
guiding you through the setup of your development environment for IDLE, we
will not in this document be providing detailed step-by-step instructions,
as you may customarily have found in previous classes.

Instead, we will point you to different sections of various official
documentations, which we expect you to read, understand, and follow thoroughly.

## Submitting Your Project (Read This First!)

In addition to viewing this developer's guide on our web page, you should also
have received a version of this guide as a [Markdown file][7], which you will
be editing and submitting for credit. 

Once you accept the GitHub assignment (described in a later section), you will
see the markdown version of this guide as `SETUP.md` in the root directory of
the repository. Here's what to do with it for this project--

As you work through this guide, please add additional details and descriptions 
that you think will help future developers follow the setup process to the
markdown file.

Where we link to the official documentation, you should add in the markdown
file the steps that you followed in the official documentation, as well as any
additional clarifications upon the official text.

You should also include screenshots for each step as you do it on your machine. 
You may also wish to expand on any sections in this document with details that 
would be helpful.

We would also like you to create two new sections:

1. **Bugs Encountered**: Record any unexpected issues that you dealt with while
following the guide so that future developers know how to handle them if they
encounter the same issues. If you did not encounter any issue/bugs, please 
describe the relevant details of your process/machine for replication.

1. **What I Learned**: At the start of this project, you likely do not understand all there
is to know about setting up the code base for development. So you should reflect
on what new things you learned as you set up your environment, and record them
in this document.

Once you complete this project, your completed development environment setup is
what you will continue to use for the upcoming individual projects. So make sure
you get started early!

### How To Submit

**DO NOT** edit `SETUP.md` directly in your `main` branch! Instead, create a 
separate git branch to make your own edits and amendments, and 
[create a pull request][12] for that branch in your own repository. We will be 
reviewing your pull request for grading.

## Project Summary

The source code for IDLE is located within the official [CPython][1] repository,
which is the reference implementation of the Python programming language. Since
we would like to avoid working directly on our operating system's installation
of Python[^1], we will create our own working copy of Python as the foundation
of our development environment.

This guide was created with the author's experience from creating a development
environment on the following setup:

- Ubuntu 22.04 LTS
- gcc 11.3.0
- Visual Studio Code
- git 2.34.1

For the most part, we will be referring to [Python's own Developer's Guide][2].
We will highlight several most relevant sections of the official documentation,
as well as the order in which you should follow them.


### **IMPORTANT:** 
If you have a Windows machine, it is **highly recommended** that
you install [Windows Subsystem for Linux (WSL 2)][8] so you can work in a UNIX-
based environment. The staff members can only provide very limited support for
students working in a native Windows environment.

This document assumes reasonable familiarity with Python, git, and the command
line interface in a UNIX-bassed environment such as Linux or MacOS.

## Downloading the Codebase

The first step is to download the source code for CPython onto your machine
for local development.

Here, instead of following the official Python developer's guide, we have created
our own master copy of the CPython code base, which we will distribute through
GitHub Classroom.

Accept the GitHub Classroom Assignment (you can find this link on the course website version of this file)

Once you accept the GitHub Classroom assignment, you should have your own
repository containing the CPython source code.

You will then need to use `git` to clone the repository to your own machine.

If you do not have `git` installed: [follow this guide][3].

> **Note:**
> Do **NOT** follow the "Get the source code" section of the official Python
> Developer's Guide! Use our own **GitHub Classroom** repository instead.

## Building Your Own Python

Before compiling our Python interpreter from the source code, we will need to
install some additional OS dependencies for certain modules to work properly.
The most important one we need is the `tkinter` package[^2]. To install them
on your system, follow this section of the official guide:

- [Install Dependencies][5]

Now that the necessary dependencies have been installed, we can go ahead and
build our working copy of Python. Follow this section of the official guide:

- [Compile and Build][6]

Fow now, you can stick to the default configuration flags recommended by the guide.

Note that since we have already installed all the ncessary dependencies, you
should _not_ see the list of extension modules that have not been built as
mentioned in the guide. Instead, you should see something like:

```
Note: Deepfreeze may have added some global objects,
      so run 'make regen-global-objects' if necessary.
Checked 111 modules (30 built-in, 80 shared, 1 n/a on linux-x86_64, 0 disabled, 0 missing,
0 failed on import)
```

Once you have the "working build" as mentioned int he documentation, you should run
it to check that you have indeed successfully built Python with the extension
modules. For example, to check for the `tkinter` module, run the compiled Python
interpreter in the terminal, and try--

```
$ ./python
Python 3.12.0a6+ (heads/main:2cdc518, ...) [GCC 11.3.0] on linux
Type "help", "copyright", "credits" or "license" for more information.
>>> import tkinter
>>>
```

If all went well, you should be able to successfully import the `tkinter` module
without any exceptions thrown.

## Running IDLE

Once all that is set up, you can now run IDLE with _your own Python_ that is separate
from the OS version. Navigate to the `CPython` repository where your `python`
executable is, and start IDLE from the terminal like so:

```
$ cd ~/CPython  # go to the CPython repo
$ ./python -m idlelib
```

You should now see the IDLE program starting as it normally would.

## Setting Up Your Editor

You can use whichever editor you prefer, but if you do not personally have a strong
preference, we would highly recommend you to follow this guide to set up Visual Studio
Code as your primary editor.

> **Note:**
> If you choose not to follow this guide for VSCode, you should still figure out how to
select the correct Python interpreter for the language server in your editor to work
properly.

> **Warning**:
> For submission, if you choose not to use VSCode, please document your process for setting up your choice of editor in `SETUP.md`!

[Visual Studio Code][9] (VSCode) has in recent years become one of the most popular
code editors in the industry.

One of the many reasons for its enormous popularity is its extensive support for a
wide range of programming languages through *extensions*. For Python development, 
[the Python extension][10], and almost a necessity for working with large code bases[^3].

Once you have VSCode and the Python extension installed on your machine, you can 
navigate to the directory where you cloned the CPython repository, and open the directory
containing the source code for IDLE: `CPython/Lib/idlelib`.

The first thing you should do is [setting the Python interpreter][11] for your workspace.
You should set the Python executable you just compiled in the `CPython` repository as
the interpreter. (Use the "Enter interpreter path..." option here, since our own copy of
Python likely won't show up as one of the defaults, which include your system's global
Python installations.)

Once the interpreter is set, you should see the change reflected in the status bar at the
bottom of the VSCode window. The Python version displayed there should be the same as
if you ran `./python --version` with your own Python executable in the `CPython` directory, something like:
 
```
$ ./python --version
Python 3.12.2
```

[1]: https://en.wikipedia.org/wiki/CPython
[2]: https://devguide.python.org/getting-started/setup-building/#build-dependencies
[3]: https://devguide.python.org/getting-started/setup-building/#install-git
[4]: https://devguide.python.org/getting-started/setup-building/#get-the-source-code
[5]: https://devguide.python.org/getting-started/setup-building/#install-dependencies
[6]: https://devguide.python.org/getting-started/setup-building/#compile-and-build
[7]: https://www.markdownguide.org/getting-started/
[8]: https://learn.microsoft.com/en-us/windows/wsl/install
[9]: https://code.visualstudio.com/
[10]: https://marketplace.visualstudio.com/items?itemName=ms-python.python
[11]: https://code.visualstudio.com/docs/python/environments#_working-with-python-interpreters
[12]: https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request

[^1]: Python is an interpreted language, and many different versions of the Python interpreter may exist simultaneously in one system. Your operating system, particularly MacOS or Linux, likely comes with both a Python 2 and Python 3 interpreter installed.
[^2]: The tkinter package (“Tk interface”) is the standard Python interface to the Tcl/Tk GUI toolkit. Both Tk and tkinter are available on most Unix platforms, including macOS, as well as on Windows systems.
[^3]: Hey, that's the name of this class!

