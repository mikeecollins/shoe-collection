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
}