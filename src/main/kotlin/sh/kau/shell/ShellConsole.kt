package sh.kau.shell

import sh.kau.shell.ShellConsole.Companion.ANSI_GRAY
import sh.kau.shell.ShellConsole.Companion.ANSI_RED
import sh.kau.shell.ShellConsole.Companion.ANSI_RESET
import sh.kau.shell.ShellConsole.Companion.ANSI_YELLOW
import java.io.BufferedReader
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class ShellConsole {
  companion object {
    const val ANSI_RESET = "\u001B[0m"
    const val ANSI_GRAY = "\u001B[90m"   // use this mostly
    const val ANSI_PURPLE = "\u001B[35m" // input commands
    const val ANSI_GREEN = "\u001B[32m"  // highlighting values
    const val ANSI_RED = "\u001B[31m"    // error
    const val ANSI_YELLOW = "\u001B[33m" // important messages
    const val ANSI_BLUE = "\u001B[34m"
    const val ANSI_CYAN = "\u001B[36m"
    const val ANSI_WHITE = "\u001B[37m"
  }
}

fun String.runInShell(
  exitOnError: Boolean = false,
  verbose: Boolean = true,
  timeoutInMinutes: Long = 3,
): String {
  if (verbose) println("${ANSI_GRAY}[command] $this $ANSI_RESET")
  val process =
      ProcessBuilder()
          // .directory(workingDirectory)
          .redirectErrorStream(true)
          .redirectOutput(ProcessBuilder.Redirect.PIPE)
          .redirectError(ProcessBuilder.Redirect.PIPE)
          // /bin/bash -c -l necessary to use programs like "find"
          // from your real user shell environment
          .command("/bin/bash", "-c", "-l", this)
          .start()

  process.waitFor(timeoutInMinutes, TimeUnit.MINUTES)
  return process.retrieveOutput(exitOnError, verbose)
}

private fun Process.retrieveOutput(exitOnError: Boolean, verbose: Boolean): String {
  val outputText = inputStream.bufferedReader().use(BufferedReader::readText)
  val exitCode = exitValue()

  val color: String
  val sign: String
  when {
    exitOnError -> {
      color = ANSI_RED
      sign = "✗ | err: $exitCode"
    }
    exitCode != 0 -> {
      color = ANSI_YELLOW
      sign = "⚠️  | err: $exitCode"
    }
    else -> {
      color = ANSI_GRAY
      sign = ""
    }
  }

  if (verbose || exitCode != 0) {
    println(
        """$color[output] $sign:
${outputText.trim()}
$ANSI_RESET""",
    )
  }

  if (exitOnError) {
    println("${ANSI_RED}✗ Exiting... $ANSI_RESET")
    exitProcess(1)
  }
  return outputText.trim()
}
