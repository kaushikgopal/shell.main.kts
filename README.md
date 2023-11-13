# shell.main.kts

This is a kotlin script that has some handy utilities that I like to use in most of my kotlin shell scripts. While I would typically just put this in a local shell script and `@file:Import` it into my working script, Intellij doesn't play nice. See [KTIJ-16352](https://youtrack.jetbrains.com/issue/KTIJ-16352) (and please upvote it while you're there).

You can however include scripts from a repo using `@file:DependsOn` directive and Intellij is ok with that. So I add common functions in `shell.main.kts` and include it in my scripts.

I aim to keep this super minimal and if Jetbrains ever gets to fixing KTIJ-16352, will happily include the info here and deprecate this repo. Until then, enjoy!

## FAQ

- What is a Kotlin Script? read [this blog post](https://kau.sh/blog/kscript-copilot/).
- Looking for examples? see [my kotlin scripts repo](https://github.com/kaushikgopal/kotlin-scripts) for some handy examples.
