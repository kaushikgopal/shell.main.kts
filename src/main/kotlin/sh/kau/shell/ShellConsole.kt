package sh.kau.shell

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
  verbose: Boolean = false,
): String {
  if (verbose) println("${ShellConsole.ANSI_GRAY}[command] $this ${ShellConsole.ANSI_RESET}")
  val process =
      ProcessBuilder()
          // .directory(workingDirectory)
          .redirectErrorStream(true)
          .redirectOutput(ProcessBuilder.Redirect.PIPE)
          .redirectError(ProcessBuilder.Redirect.PIPE)
          // the /bin/bash -c -l is necessary if you use programs like "find" etc.
          .command("/bin/bash", "-c", "-l", this)
          .start()
  process.waitFor(3, TimeUnit.MINUTES)
  return process.retrieveOutput(exitOnError)
}

private fun Process.retrieveOutput(exitOnError: Boolean): String {
  val outputText = inputStream.bufferedReader().use(BufferedReader::readText)
  val exitCode = exitValue()
  if (exitCode != 0) {
    val color = if(exitOnError) ShellConsole.ANSI_RED else ShellConsole.ANSI_YELLOW
    val sign = if(exitOnError) "✗" else "⚠️"

    println(
        """$color
$sign  [err: $exitCode] output:
------------------
${outputText.trim()}
${ShellConsole.ANSI_RESET}""",
    )
    if (exitOnError) {
      println("${ShellConsole.ANSI_RED}✗ Exiting... ${ShellConsole.ANSI_RESET}")
      exitProcess(1)
    }
  }
  return outputText.trim()
}
