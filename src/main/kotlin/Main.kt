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
         > |   3) Delete Shoe             |
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
            2 ->  updateShoe()
            5 ->  archiveShoe()
            4 ->  deleteShoe()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)

}

fun listShoes() {
    if (shoeAPI.numberOfShoes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {

            1 -> listActiveShoes();
            2 -> listArchivedShoes();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
}



fun listShoe(){
    println(shoeAPI.listAllShoes())
}

fun listActiveShoes() {
    println(shoeAPI.listActiveShoes())
}

fun deleteShoe(){
    //logger.info { "deleteNotes() function invoked" }
    listShoes()
    if (shoeAPI.numberOfShoes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val shoeToDelete = shoeAPI.deleteShoe(indexToDelete)
        if (shoeToDelete != null) {
            println("Delete Successful! Deleted note: ${shoeToDelete.shoeName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}


fun updateShoe() {
    //logger.info { "updateNotes() function invoked" }
    listShoes()
    if (shoeAPI.numberOfShoes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the Shoe to update: ")
        if (shoeAPI.isValidIndex(indexToUpdate)) {
            val shoeName = readNextLine("Enter a Shoe for the Shoe: ")
            val shoeSize = readNextInt("Enter the Size of the Shoe between 2-14 ")
            val shoeDescription = readNextLine("Enter a Description for the shoe: ")
            val shoePrice = readNextDouble("How much will the shoe cost: ")
            val shoeType = readNextLine("What type of shoe is this: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (shoeAPI.updateShoe(indexToUpdate, Shoe(shoeName, shoeSize, shoeDescription, shoePrice,shoeType,false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun listArchivedShoes() {
    println(shoeAPI.listArchivedShoes())
}

fun archiveShoe() {
    listActiveShoes()
    if (shoeAPI.numberOfActiveShoes() > 0) {
        //only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (shoeAPI.archiveShoe(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }

}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

