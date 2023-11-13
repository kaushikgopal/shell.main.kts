# shell.main.kts

This is a kotlin script that has some handy utilities that I like to use in most of my kotlin shell scripts. While I would typically just put this in a local shell script and `@file:Import` it directly into my working script, Intellij doesn't play nice yet with this strategy. See [KTIJ-16352](https://youtrack.jetbrains.com/issue/KTIJ-16352) (and please upvote it while you're there).

You can however include scripts from a repo using `@file:DependsOn` directive and Intellij is ok with that. So I add common functions in `shell.main.kts` and include it in my scripts.

I aim to keep this super minimal and if Jetbrains ever gets to fixing KTIJ-16352, will happily include the info here and deprecate this repo. Until then, enjoy!

# Usage

```kotlin
# in your xxx.main.kts script

@file:Repository("https://jitpack.io")
@file:DependsOn("com.github.kaushikgopal:shell.main.kts:1.0.1")

import sh.kau.shell.runInShell

"ls -altr".runInShell()
```

Here's a sample `.main.kts` script:

![screenshot_20231113_000107@2x.png](..%2F..%2FLibrary%2FApplication%20Support%2FCleanShot%2Fmedia%2Fmedia_leWuLYGcWj%2Fscreenshot_20231113_000107%402x.png)

Here's the output:

![screenshot_20231113_000106@2x.png](..%2F..%2FLibrary%2FApplication%20Support%2FCleanShot%2Fmedia%2Fmedia_q84JqaRArR%2Fscreenshot_20231113_000106%402x.png)

## FAQ

- What is a Kotlin Script? read [this blog post](https://kau.sh/blog/kscript-copilot/).
- Looking for examples? see [my kotlin scripts repo](https://github.com/kaushikgopal/kotlin-scripts) for some handy examples.
