import java.lang.System.exit
import java.util.*

val scanner = Scanner(System.`in`)
fun main(args: Array<String>) {
    runMenu()
}


fun mainMenu() : Int {
    print(""" 
         > ----------------------------------
         > |        KICKS MENU              |
         > ----------------------------------
         > | KICKS MENU                     |
         > |   1) Enter Shoe Size           |
         > |   2) Enter Shoe type           |
         > |   3) Enter Shoe price          |
         > |   4) Enter Shoe Name           |
         > |   5) Enter Shoe Description    |
         > |   6) List All Shoes            |
         > |   7) Enter Product line        |
         > |   8) Delete a Shoe             |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> shoeSize()
            2  -> shoeType()
            3  -> shoePrice()
            4  -> shoeName()
            5  -> shoeDescription()
            6  -> listShoe()
            7  -> deleteShoe()
            8  -> inCurrentProductLine()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)

}
fun shoeSize(){
    println("You chose Shoe Size")
}

fun shoeType(){
    println("You chose Shoe type")
}

fun shoePrice(){
    println("You chose Shoe Price")
}

fun shoeName(){
    println("You chose Shoe Name")
}


fun shoeDescription(){
    println("You chose Shoe Description")
}

fun listShoe(){
    println("You chose List Shoe")
}

fun inCurrentProductLine(){
    println("You chose Shoe type")
}

fun deleteShoe(){
    println("You chose Delete Shoe")
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

