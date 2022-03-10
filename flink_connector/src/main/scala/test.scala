import joptsimple._

object test {
    def main(args: Array[String]): Unit = {
        val value = new OptionParser(false)
        value.accepts("","").withOptionalArg()

    }

}
