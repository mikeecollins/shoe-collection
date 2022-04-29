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
         >     1) Add Shoe                  |
         > |   2) List all shoes            |
         > |   3) Delete Shoe               |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))

}

fun addShoe(){
    val shoeName = readNextLine("Enter the shoe you would like to add: ")
    val shoeSize = readNextInt("Enter the size of the shoe ")
    val shoeDescription = readNextLine("What is this Shoe for ")
    val shoePrice = readNextDouble("Enter the Price of the Shoe")
    val shoeType = readNextLine("What Shoe type would you like")
    val isAdded = shoeAPI.add(Shoe(shoeName,shoeSize , shoeDescription,shoePrice,shoeType, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }


}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addShoe()
            2 ->  listShoe()
            3  -> deleteShoe()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)

}


fun listShoe(){
    println(shoeAPI.listAllShoes())
}



fun deleteShoe(){
    println("You chose Delete Shoe")
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

