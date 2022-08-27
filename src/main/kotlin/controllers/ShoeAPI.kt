package controllers

import models.Shoe
import persistence.Serializer

class ShoeAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var shoes = ArrayList<Shoe>()

    fun add(shoe: Shoe): Boolean {
        return shoes.add(shoe)
    }

    fun isValidIndex(index: Int): Boolean {
        return index >= 0 && index <shoes.size
    }

    fun deleteShoe(indexToDelete: Int): Shoe? {
        return if (isValidListIndex(indexToDelete, shoes)) {
            shoes.removeAt(indexToDelete)
        } else null
    }

    fun listAllShoes(): String {
        return if (shoes.isEmpty()) {
            "No Shoes Found"
        } else {
            var listOfShoes = ""
            for (i in shoes.indices) {
                listOfShoes += "$i: ${shoes[i]} \n"
            }
            listOfShoes
        }
    }

    fun updateShoe(indexToUpdate: Int, shoe: Shoe?): Boolean {
        // find the note object by the index number
        val foundShoe = findShoe(indexToUpdate)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundShoe != null) && (shoe != null)) {
            foundShoe.shoeName = shoe.shoeName
            foundShoe.shoeSize = shoe.shoeSize
            foundShoe.shoeDescription = shoe.shoeDescription
            return true
        }

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun numberOfShoes(): Int {
        return shoes.size
    }

    fun findShoe(index: Int): Shoe? {
        return if (isValidListIndex(index, shoes)) {
            shoes[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listActiveShoes(): String {
        return if (numberOfActiveShoes() == 0) {
            "No active shoes stored"
        } else {
            var listOfActiveShoes = ""
            for (shoe in shoes) {
                if (!shoe.inCurrentProductLine) {
                    listOfActiveShoes += "${shoes.indexOf(shoe)}: $shoe \n"
                }
            }
            listOfActiveShoes
        }
    }

    fun listArchivedShoes(): String {
        return if (numberOfArchivedShoes() == 0) {
            "No archived shoes stored"
        } else {
            var listOfArchivedShoes = ""
            for (shoe in shoes) {
                if (shoe.inCurrentProductLine) {
                    listOfArchivedShoes += "${shoes.indexOf(shoe)}: $shoe \n"
                }
            }
            listOfArchivedShoes
        }
    }

    fun numberOfArchivedShoes(): Int {
        // return notes.stream().filter { obj: Note -> obj.isNoteArchived }.count().toInt()
        var counter = 0
        for (shoe in shoes) {
            if (shoe.inCurrentProductLine) {
                counter++
            }
        }
        return counter
    }

    fun archiveShoe(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val shoeToArchive = shoes[indexToArchive]
            if (!shoeToArchive.inCurrentProductLine) {
                shoeToArchive.inCurrentProductLine = true
                return true
            }
        }
        return false
    }

    fun numberOfActiveShoes(): Int {
        // return notes.stream().filter { p: Note -> !p.isNoteArchived }.count().toInt()
        var counter = 0
        for (shoe in shoes) {
            if (!shoe.inCurrentProductLine) {
                counter++
            }
        }
        return counter
    }

    fun listShoesBySelectedPriority(priority: Int): String {
        return if (shoes.isEmpty()) {
            "No shoes stored"
        } else {
            var listOfShoes = ""
            for (i in shoes.indices) {
                if (shoes[i].shoeSize == priority) {
                    listOfShoes +=
                        """$i: ${shoes[i]}
                        """.trimIndent()
                }
            }
            if (listOfShoes.equals("")) {
                "No shoes with priority: $priority"
            } else {
                "${numberOfShoesByPriority(priority)} shoes with priority $priority: $listOfShoes"
            }
        }
    }
    fun formatListString(shoesToFormat: List<Shoe>): String =
        shoesToFormat
            .joinToString(separator = "\n") { shoe ->
                shoes.indexOf(shoe).toString() + ": " + shoe.toString()
            }

    fun searchByName(searchString: String) =
        shoes.filter { shoe -> shoe.shoeName.contains(searchString, ignoreCase = true) }
            .joinToString(separator = "\n") {
                shoe ->
                shoes.indexOf(shoe).toString() + ": " + shoe.toString()
            }

    fun numberOfShoesByPriority(priority: Int): Int {
        var counter = 0
        for (shoe in shoes) {
            if (shoe.shoeSize == priority) {
                counter++
            }
        }
        return counter
    }
    @Throws(Exception::class)
    fun load() {
        shoes = serializer.read() as ArrayList<Shoe>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(shoes)
    }
}
