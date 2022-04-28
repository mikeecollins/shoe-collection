package controllers

import models.Shoe

class ShoeAPI {

    private var shoes = ArrayList<Shoe>()


    fun add(shoe: Shoe): Boolean {
        return shoes.add(shoe)
    }

    fun listAllShoes(): String {
        return if (shoes.isEmpty()) {
            "No Shoes Foumd"
        } else {
            var listOfShoes = ""
            for (i in shoes.indices) {
                listOfShoes += "${i}: ${shoes[i]} \n"
            }
            listOfShoes
        }


    }

    fun numberOfShoes(): Int {
        return shoes.size
    }

    fun findShoe(index: Int): Shoe? {
        return if (isValidListIndex(index, shoes)) {
            shoes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}