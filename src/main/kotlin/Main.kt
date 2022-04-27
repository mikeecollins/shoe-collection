import controllers.ShoeAPI
import models.Shoe
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit

private val shoeAPI = ShoeAPI()

fun main(args: Array<String>) {
    runMenu()
}


fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
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

}

fun addShoe(){
    val shoeName = readNextLine("Enter a title for the note: ")
    val shoeSize = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val shoeDescription = readNextLine("Enter a category for the note: ")
    val shoePrice = readNextDouble("Enter the Prie of the Shoe")
    val shoeType = readNextLine("What Shoe type would you like")
    val isAdded = shoeAPI.add(Shoe(shoeName,shoeSize , shoeDescription,shoePrice,shoeType, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }

    fun listShoes(){
        println(shoeAPI.listAllShoes())
    }
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

