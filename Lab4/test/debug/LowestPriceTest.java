package debug;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LowestPriceTest {
    
    // 给出的样例
    @Test
    public void testExample1(){ // [2,5], [[3,0,5],[1,2,10]], [3,2]
        LowestPrice lp = new LowestPrice();
        List<Integer> price = new ArrayList<>();
        price.add(2);
        price.add(5);
        List<List<Integer>> special = new ArrayList<>();
        List<Integer> offer = new ArrayList<>();
        offer.add(3);
        offer.add(0);
        offer.add(5);
        special.add(offer);
        offer = new ArrayList<>();
        offer.add(1);
        offer.add(2);
        offer.add(10);
        special.add(offer);
        List<Integer> needs = new ArrayList<>();
        needs.add(3);
        needs.add(2);
        assertEquals(14, lp.shoppingOffers(price, special, needs));
    }

    @Test
    public void testExample2(){ // [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1]
        LowestPrice lp = new LowestPrice();
        List<Integer> price = new ArrayList<>();
        price.add(2);
        price.add(3);
        price.add(4);
        List<List<Integer>> special = new ArrayList<>();
        List<Integer> offer = new ArrayList<>();
        offer.add(1);
        offer.add(1);
        offer.add(0);
        offer.add(4);
        special.add(offer);
        offer = new ArrayList<>();
        offer.add(2);
        offer.add(2);
        offer.add(1);
        offer.add(9);
        special.add(offer);
        List<Integer> needs = new ArrayList<>();
        needs.add(1);
        needs.add(2);
        needs.add(1);
        assertEquals(11, lp.shoppingOffers(price, special, needs));
    }
    
    @Test
    public void testOneItemOneOffer() { //[5], [[2, 8]], [3]
    	 LowestPrice lp = new LowestPrice();
    	 List<Integer> price = new ArrayList<>();
    	 price.add(5);
    	 List<List<Integer>> special = new ArrayList<>();
         List<Integer> offer = new ArrayList<>();
         offer.add(2);
         offer.add(8);
         special.add(offer);
         List<Integer> needs = new ArrayList<>();
         needs.add(3);
         assertEquals(13, lp.shoppingOffers(price, special, needs));
    }
    
    @Test
    public void testOneItemMultiOffer() { //[5], [[2, 8], [2, 7]], [3]
    	 LowestPrice lp = new LowestPrice();
    	 List<Integer> price = new ArrayList<>();
    	 price.add(5);
    	 List<List<Integer>> special = new ArrayList<>();
         List<Integer> offer = new ArrayList<>();
         offer.add(2);
         offer.add(8);
         special.add(offer);
         offer = new ArrayList<>();
         offer.add(2);
         offer.add(7);
         special.add(offer);
         List<Integer> needs = new ArrayList<>();
         needs.add(3);
         assertEquals(12, lp.shoppingOffers(price, special, needs));
    }
    
    @Test
    public void testMultiItemOneOffer() { //[5, 3], [[2, 3, 15]], [4, 4]
    	 LowestPrice lp = new LowestPrice();
    	 List<Integer> price = new ArrayList<>();
    	 price.add(5);
    	 price.add(3);
    	 List<List<Integer>> special = new ArrayList<>();
         List<Integer> offer = new ArrayList<>();
         offer.add(2);
         offer.add(3);
         offer.add(15);
         special.add(offer);
         List<Integer> needs = new ArrayList<>();
         needs.add(4);
         needs.add(4);
         assertEquals(28, lp.shoppingOffers(price, special, needs));
    }
    
    @Test
    public void testMultiItemMultiOffer() { //[5, 3], [[2, 3, 15], [1, 2, 11]], [4 ,5]
    	 LowestPrice lp = new LowestPrice();
    	 List<Integer> price = new ArrayList<>();
    	 price.add(5);
    	 price.add(3);
    	 List<List<Integer>> special = new ArrayList<>();
         List<Integer> offer = new ArrayList<>();
         offer.add(2);
         offer.add(3);
         offer.add(15);
         special.add(offer);
         offer = new ArrayList<>();
         offer.add(1);
         offer.add(2);
         offer.add(11);
         special.add(offer);
         List<Integer> needs = new ArrayList<>();
         needs.add(4);
         needs.add(5);
         assertEquals(31, lp.shoppingOffers(price, special, needs));
    }
}