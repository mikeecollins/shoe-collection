import controllers.ShoeAPI
import models.Shoe
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

//private val shoeAPI = ShoeAPI(XMLSerializer(File("shoes.xml")))
private val shoeAPI = ShoeAPI(JSONSerializer(File("shoes.json")))

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
         > |   3) Update Shoe               |
         > |   4) archive Shoe              |
         > |   5) Delete shoe               |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > |                                |
         > ----------------------------------
         >     20) Save notes               |
         > |   21) Load notes               |
         > |   0) Exit                      |
         > |                                |
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
            3 ->  updateShoe()
            4 ->  archiveShoe()
            5 ->  deleteShoe()
            20 -> save()
            21 -> load()
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
                  > |   1) View ALL shoes          |
                  > |   2) View ACTIVE shoes       |
                  > |   3) View ARCHIVED shoes     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllNotes();
            2 -> listActiveShoes();
            3 -> listArchivedShoes();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No shoes stored");
    }
}

fun listAllNotes() {
    println(shoeAPI.listAllShoes())
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
        val indexToDelete = readNextInt("Enter the index of the shoe to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val shoeToDelete = shoeAPI.deleteShoe(indexToDelete)
        if (shoeToDelete != null) {
            println("Delete Successful! Deleted shoe: ${shoeToDelete.shoeName}")
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
            println("There are no shoe for this index number")
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
        val indexToArchive = readNextInt("Enter the index of the +shoe to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (shoeAPI.archiveShoe(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }

}

fun save() {
    try {
        shoeAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        shoeAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

