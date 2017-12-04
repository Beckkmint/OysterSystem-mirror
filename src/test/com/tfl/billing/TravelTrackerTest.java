package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TravelTrackerTest {

    private TravelTracker travelTracker;

    @Before
    public void setUp() {
        travelTracker = new TravelTracker();
    }

    @Test
    public void knownOysterCardScannedTest() {
        OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID cardId = myCard.id();

        OysterCardReader eustonReader = OysterReaderLocator.atStation(Station.EUSTON);
        UUID eustonReaderId = eustonReader.id();

        try{
            travelTracker.cardScanned(cardId, eustonReaderId);
        }catch (UnknownOysterCardException unknownOysterCardException) {
            fail("The method didn't throw what expected to.");
        }


    }

    @Test
    public void cardHasNotTouchedInTest() {
        OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        Set<UUID> currentlyTravelling2 = travelTracker.getCurrentlyTravelling();
        assertFalse(currentlyTravelling2.contains(myCard));
    }

    @Test
    public void cardHasTouchedInTest() {
        OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        OysterCardReader eustonReader = OysterReaderLocator.atStation(Station.EUSTON);
        eustonReader.touch(myCard);
        List<JourneyEvent> eventLog2 = travelTracker.getEventLog();
        assertTrue(eventLog2.contains(myCard));
    }
/*
    @Test
    public void startTimeTest() {
        Date satrt = Journey.startTime();
        long journeyStart;
        long journeyEventStart = JourneyEvent.time();
        assertEquals(journeyStart, journeyEventStart);
    }

    @Test
    public void endTimeTest() {

    }*/
}
