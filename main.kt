import java.io.File

fun readBF(path: String): String{
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun getLoopBlocks (source: List<String>): HashMap <Int, Int>{
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()){
        if (char == "MOO"){
            stack.add(i)
        }
        if (char == "moo"){
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks

}

fun eval(source: List<String>) {
    val buffer = Array<Char>(200) { _ -> (0).toChar() }
    var ptr = 0
    var i = 0
    var register = (0).toChar()
    var blocks = getLoopBlocks(source)
    //println(blocks)
    while (i < source.size) {
        //println(source[i])
        when (source[i]) {
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1;
            "Moo" -> {
                if (buffer[ptr] == (0).toChar())
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                else
                    print("${buffer[ptr]}")
            }
            "MOO" -> {
               if (buffer[ptr] == (0).toChar()) {
                   i = blocks[i]!!
               }
            }
            "moo" -> {
                if (buffer[ptr] != (0).toChar()) {
                    i = blocks[i]!!
                }
            }
            "OOM" -> print("${buffer[ptr].toInt()}")
            "MMM" -> {
                if (register == (0).toChar())
                    register = buffer[ptr]
                else
                    buffer[ptr] = register
            }
            "OOO" -> buffer[ptr] = (0).toChar()
        }
        i += 1
    }
}

fun main (args: Array<String>){
    val sourceInit = readBF("cow_examples/hello.cow")
    println(sourceInit)
    var source = sourceInit.split(" ")
    eval(source)

}