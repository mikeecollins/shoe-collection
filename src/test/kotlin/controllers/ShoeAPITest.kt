package controllers

import models.Shoe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import java.util.Locale
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
class ShoeAPITest {

    private var jordanOnes: Shoe? = null
    private var footLocker: Shoe? = null
    private var tommyHilfiger: Shoe? = null
    private var TimberLand: Shoe? = null
    private var nike: Shoe? = null
    private var populatedShoes: ShoeAPI? = ShoeAPI(XMLSerializer(File("shoes.xml")))
    private var emptyShoes: ShoeAPI? = ShoeAPI(XMLSerializer(File("shoes.xml")))

    @BeforeEach
    fun setup() {
        jordanOnes = Shoe("Jordans", 5, "For jogging ", 70.00, "Runners", false)
        footLocker = Shoe("Vapour Max", 10, "Everyday wear ", 125.00, "Sneakers", true)
        tommyHilfiger = Shoe("Badge pool", 5, "Casual wear ", 80.00, "Sandals", false)
        TimberLand = Shoe("Bradstreet", 5, "Street wear ", 100.00, "Boots", false)
        nike = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", true)

        populatedShoes!!.add(jordanOnes!!)
        populatedShoes!!.add(footLocker!!)
        populatedShoes!!.add(tommyHilfiger!!)
        populatedShoes!!.add(TimberLand!!)
        populatedShoes!!.add(nike!!)
    }

    @AfterEach
    fun tearDown() {
        jordanOnes = null
        footLocker = null
        tommyHilfiger = null
        TimberLand = null
        nike = null
        populatedShoes = null
        emptyShoes = null
    }

    @Nested
    inner class AddShoes {
        @Test
        fun `adding a Shoe to a populated list adds to ArrayList`() {
            val newShoe = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", false)
            assertEquals(5, populatedShoes!!.numberOfShoes())
            assertTrue(populatedShoes!!.add(newShoe))
            assertEquals(6, populatedShoes!!.numberOfShoes())
            assertEquals(newShoe, populatedShoes!!.findShoe(populatedShoes!!.numberOfShoes() - 1))
        }

        @Test
        fun `adding a Shoe to an empty list adds to ArrayList`() {
            val newShoe = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", false)
            assertEquals(0, emptyShoes!!.numberOfShoes())
            assertTrue(emptyShoes!!.add(newShoe))
            assertEquals(1, emptyShoes!!.numberOfShoes())
            assertEquals(newShoe, emptyShoes!!.findShoe(emptyShoes!!.numberOfShoes() - 1))
        }

        @Nested
        inner class ListShoes {

            @Test
            fun `listAllShoes returns No Shoe Stored message when ArrayList is empty`() {
                assertEquals(0, emptyShoes!!.numberOfShoes())
                assertTrue(emptyShoes!!.listAllShoes().lowercase().contains("no shoes"))
            }

            @Test
            fun `listAllShoes returns Shoes when ArrayList has shoes stored`() {
                assertEquals(5, populatedShoes!!.numberOfShoes())
                val shoesString = populatedShoes!!.listAllShoes()
                assertTrue(shoesString.contains("Jordans"))
                assertTrue(shoesString.contains("Vapour Max"))
                assertTrue(shoesString.contains("Bradstreet"))
                assertTrue(shoesString.contains("Black AF1"))
            }
        }

        @Test
        fun `listActiveShoes returns no active shoes stored when ArrayList is empty`() {
            assertEquals(0, emptyShoes!!.numberOfActiveShoes())
            assertTrue(
                emptyShoes!!.listActiveShoes().lowercase().contains("no active shoes")
            )
        }

        @Test
        fun `listActiveShoes returns active shoes when ArrayList has active shoes stored`() {
            assertEquals(3, populatedShoes!!.numberOfActiveShoes())
            val activeShoesString = populatedShoes!!.listActiveShoes()
            assertFalse(activeShoesString.contains("jordans"))
            assertFalse(activeShoesString.contains("vapour max"))
            assertFalse(activeShoesString.contains("bradstreet"))
            assertFalse(activeShoesString.contains("black af1"))
            assertFalse(activeShoesString.contains("badge pool"))
        }
    }

    @Test
    fun `listArchivedShoes returns no archived shoes when ArrayList is empty`() {
        assertEquals(0, emptyShoes!!.numberOfArchivedShoes())
        assertTrue(
            emptyShoes!!.listArchivedShoes().lowercase().contains("no archived shoes")
        )
    }

    @Test
    fun `listArchivedShoes returns archived shoes when ArrayList has archived shoes stored`() {
        assertEquals(2, populatedShoes!!.numberOfArchivedShoes())
        val archivedShoesString = populatedShoes!!.listArchivedShoes().lowercase(Locale.getDefault())
        assertFalse(archivedShoesString.contains("jordans"))
        assertTrue(archivedShoesString.contains("vapour max"))
        assertFalse(archivedShoesString.contains("bradstreet"))
        assertTrue(archivedShoesString.contains("black af1"))
        assertFalse(archivedShoesString.contains("badge pool"))
    }

    @Test
    fun `listShoesBySelectedPriority returns No Shoes when ArrayList is empty`() {
        assertEquals(0, emptyShoes!!.numberOfShoes())
        assertTrue(
            emptyShoes!!.listShoesBySelectedPriority(1).lowercase().contains("no shoes")
        )
    }

    @Test
    fun `listShoesBySelectedPriority returns no shoes when no shoes of that priority exist`() {
        // Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
        assertEquals(5, populatedShoes!!.numberOfShoes())
        val priority2String = populatedShoes!!.listShoesBySelectedPriority(2).lowercase()
        assertTrue(priority2String.contains("no shoes"))
        assertTrue(priority2String.contains("2"))
    }

    @Test
    fun `listShoesBySelectedPriority returns all shoes that match that priority when shoes of that priority exist`() {
        // Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
        assertEquals(5, populatedShoes!!.numberOfShoes())
        val priority1String = populatedShoes!!.listShoesBySelectedPriority(1).lowercase()
        assertFalse(priority1String.contains("1 note"))
        assertFalse(priority1String.contains("priority 1"))
        assertFalse(priority1String.contains("jordans"))
        assertFalse(priority1String.contains("vapour max"))
        assertFalse(priority1String.contains("bradstreet"))
        assertFalse(priority1String.contains("black af1"))
        assertFalse(priority1String.contains("badge pool"))

        val priority4String = populatedShoes!!.listShoesBySelectedPriority(4).lowercase(Locale.getDefault())
        assertFalse(priority4String.contains("2 note"))
        assertFalse(priority4String.contains("priority 4"))
        assertFalse(priority4String.contains("jordans"))
        assertFalse(priority4String.contains("vapour max"))
        assertFalse(priority4String.contains("bradstreet"))
        assertFalse(priority4String.contains("black af1"))
        assertFalse(priority4String.contains("badge pool"))
    }

    @Nested
    inner class DeleteNotes {

        @Test
        fun `deleting a Shoe that does not exist, returns null`() {
            assertNull(emptyShoes!!.deleteShoe(0))
            assertNull(populatedShoes!!.deleteShoe(-1))
            assertNull(populatedShoes!!.deleteShoe(5))
        }

        @Test
        fun `deleting a shoe that exists delete and returns deleted object`() {
            assertEquals(5, populatedShoes!!.numberOfShoes())
            assertEquals(nike, populatedShoes!!.deleteShoe(4))
            assertEquals(4, populatedShoes!!.numberOfShoes())
            assertEquals(jordanOnes, populatedShoes!!.deleteShoe(0))
            assertEquals(3, populatedShoes!!.numberOfShoes())
        }
    }

    @Nested
    inner class UpdateShoes {
        @Test
        fun `updating a shoe that does not exist returns false`() {
            assertFalse(populatedShoes!!.updateShoe(6, Shoe("Updating Shoe", 11, "Work ", 110.00, "Runners", false)))
            assertFalse(populatedShoes!!.updateShoe(-1, Shoe("Updating Shoe", 11, "Work ", 110.00, "Runners", false)))
            assertFalse(emptyShoes!!.updateShoe(0, Shoe("Updating Shoe", 11, "Work ", 110.00, "Runners", false)))
        }
    }

    @Test
    fun `updating a shoe that exists returns true and updates`() {
        // check note 5 exists and check the contents
        assertEquals(nike, populatedShoes!!.findShoe(4))
        assertEquals("Black AF1", populatedShoes!!.findShoe(4)!!.shoeName)
        assertEquals(11, populatedShoes!!.findShoe(4)!!.shoeSize)
        assertEquals("Casual", populatedShoes!!.findShoe(4)!!.shoeDescription)

        // update note 5 with new information and ensure contents updated successfully
        assertTrue(populatedShoes!!.updateShoe(4, Shoe("Updating Shoe", 11, "Work ", 110.00, "Runners", false)))
        assertEquals("Updating Shoe", populatedShoes!!.findShoe(4)!!.shoeName)
        assertEquals(2, populatedShoes!!.findShoe(4)!!.shoeSize)
        assertEquals("College", populatedShoes!!.findShoe(4)!!.shoeDescription)
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingShoes = ShoeAPI(XMLSerializer(File("shoes.xml")))
            storingShoes.store()

            // Loading the empty notes.xml file into a new object
            val loadedShoes = ShoeAPI(XMLSerializer(File("shoes.xml")))
            loadedShoes.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingShoes.numberOfShoes())
            assertEquals(0, loadedShoes.numberOfShoes())
            assertEquals(storingShoes.numberOfShoes(), loadedShoes.numberOfShoes())
        }
    }

    @Test
    fun `saving and loading an loaded collection in XML doesn't loose data`() {
        // Storing 3 notes to the notes.XML file.
        val storingShoes = ShoeAPI(XMLSerializer(File("shoes.xml")))
        storingShoes.add(nike!!)
        storingShoes.add(tommyHilfiger!!)
        storingShoes.add(TimberLand!!)
        storingShoes.store()

        // Loading notes.xml into a different collection
        val loadedShoes = ShoeAPI(XMLSerializer(File("shoes.xml")))
        loadedShoes.load()

        // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
        assertEquals(3, storingShoes.numberOfShoes())
        assertEquals(3, loadedShoes.numberOfShoes())
        assertEquals(storingShoes.numberOfShoes(), loadedShoes.numberOfShoes())
        assertEquals(storingShoes.findShoe(0), loadedShoes.findShoe(0))
        assertEquals(storingShoes.findShoe(1), loadedShoes.findShoe(1))
        assertEquals(storingShoes.findShoe(2), loadedShoes.findShoe(2))
    }

    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty notes.json file.
        val storingShoes = ShoeAPI(JSONSerializer(File("shoes.json")))
        storingShoes.store()

        // Loading the empty notes.json file into a new object
        val loadedShoes = ShoeAPI(JSONSerializer(File("shoes.json")))
        loadedShoes.load()

        // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        assertEquals(0, storingShoes.numberOfShoes())
        assertEquals(0, loadedShoes.numberOfShoes())
        assertEquals(storingShoes.numberOfShoes(), loadedShoes.numberOfShoes())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 notes to the notes.json file.
        val storingShoes = ShoeAPI(JSONSerializer(File("shoes.json")))
        storingShoes.add(nike!!)
        storingShoes.add(tommyHilfiger!!)
        storingShoes.add(jordanOnes!!)
        storingShoes.store()

        // Loading notes.json into a different collection
        val loadedShoes = ShoeAPI(JSONSerializer(File("shoes.json")))
        loadedShoes.load()

        // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        assertEquals(3, storingShoes.numberOfShoes())
        assertEquals(3, loadedShoes.numberOfShoes())
        assertEquals(storingShoes.numberOfShoes(), loadedShoes.numberOfShoes())
        assertEquals(storingShoes.findShoe(0), loadedShoes.findShoe(0))
        assertEquals(storingShoes.findShoe(1), loadedShoes.findShoe(1))
        assertEquals(storingShoes.findShoe(2), loadedShoes.findShoe(2))
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfShoesCalculatedCorrectly() {
            assertEquals(5, populatedShoes!!.numberOfShoes())
            assertEquals(0, emptyShoes!!.numberOfShoes())
        }

        @Test
        fun numberOfArchivedShoesCalculatedCorrectly() {
            assertEquals(2, populatedShoes!!.numberOfArchivedShoes())
            assertEquals(0, emptyShoes!!.numberOfArchivedShoes())
        }

        @Test
        fun numberOfActiveShoesCalculatedCorrectly() {
            assertEquals(3, populatedShoes!!.numberOfActiveShoes())
            assertEquals(0, emptyShoes!!.numberOfActiveShoes())
        }

        @Test
        fun numberOfShoesByPriorityCalculatedCorrectly() {
            assertEquals(1, populatedShoes!!.numberOfShoesByPriority(1))
            assertEquals(0, populatedShoes!!.numberOfShoesByPriority(2))
            assertEquals(1, populatedShoes!!.numberOfShoesByPriority(3))
            assertEquals(2, populatedShoes!!.numberOfShoesByPriority(4))
            assertEquals(1, populatedShoes!!.numberOfShoesByPriority(5))
            assertEquals(0, +emptyShoes!!.numberOfShoesByPriority(1))
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search shoes by title returns no notes when no shoes with that title exist`() {
            // Searching a populated collection for a title that doesn't exist.
            assertEquals(5, populatedShoes!!.numberOfShoes())
            val searchResults = populatedShoes!!.searchByName("no results expected")
            assertTrue(searchResults.isEmpty())

            // Searching an empty collection
            assertEquals(0, emptyShoes!!.numberOfShoes())
            assertTrue(emptyShoes!!.searchByName("").isEmpty())
        }

        @Test
        fun `search notes by title returns notes when notes with that title exist`() {
            assertEquals(5, populatedShoes!!.numberOfShoes())

            // Searching a populated collection for a full title that exists (case matches exactly)
            var searchResults = populatedShoes!!.searchByName("Code App")
            assertTrue(searchResults.contains("Black AF1"))
            assertFalse(searchResults.contains("Test App"))

            // Searching a populated collection for a partial title that exists (case matches exactly)
            searchResults = populatedShoes!!.searchByName("App")
            assertTrue(searchResults.contains("bradstreet"))
            assertTrue(searchResults.contains("Badge pool"))
            assertFalse(searchResults.contains("vapour max"))

            // Searching a populated collection for a partial title that exists (case doesn't match)
            searchResults = populatedShoes!!.searchByName("aPp")
            assertTrue(searchResults.contains("vapour max"))
            assertTrue(searchResults.contains("Black AF1"))
            assertFalse(searchResults.contains("TimberLands"))
        }
    }
}
