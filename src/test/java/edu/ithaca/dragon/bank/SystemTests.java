package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//


    //---------------Amelia---------------//
    @Test
    public void suspiciousDaysTest(){
        CentralBank cb = new CentralBank();
        // Pretend Beforehand
        cb.createAccount("act0", 171);
        cb.createAccount("act1", 172);
        cb.createAccount("act2", 220);
        cb.createAccount("act3", 345);

        BasicAPI atm = cb;
        AdvancedAPI teller = cb;
        AdminAPI admin = cb;
        DaemonAPI daemon = cb;

        // Day 1






        // Day 1 End Asserts



        // Day 2




        // Day 2 End Asserts



    }

    //---------------Jolie---------------//
}
