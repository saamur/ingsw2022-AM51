package it.polimi.ingsw;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class IslandTest {
    /**
     * test to check if getNumberOfIsland behaves in the right way
     */
    @Test
    public void testIsland(){
        Island i= new Island();
        int result=i.getNumberOfIslands();
        assertEquals(1, result);
    }
}
