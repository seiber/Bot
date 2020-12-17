import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.Players;
import org.osbot.rs07.api.Trade;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.utility.ConditionalSleep2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


@ScriptManifest(name = "Jug refiller", version =1.0, author ="Dakota" , logo ="" , info ="" )

public class jug extends Script {
    private long startTime;
    private final Area bankChest = new Area(3221, 3217, 3221, 3218);
    private final Area waterFountain = new Area(3222, 3212, 3221, 3212);
    private final Area tradeArea = new Area(3219, 3228, 3219, 3226);


    String muleName = JOptionPane.showInputDialog("Enter mule name:");
    //private int tree; //0 -> willow, 1 -> yew
    @Override
    public void onStart() throws InterruptedException
    {
        super.onStart();

       // String muleName = JOptionPane.showInputDialog("Enter mule name:");
        startTime = System.currentTimeMillis();
        //GUI g = new GUI();
       // g.setVisible(true);
       // while(g.isVisible()) {}
        //tree = g.comboBox.getSelectedIndex();
        if(dialogues.isPendingContinuation())
        {
            dialogues.completeDialogue();

        }
        getWalking().webWalk(bankChest);
        if(getWorlds().getCurrentWorld()!= 371)
        {
            log("Hopping to pvp world, 371");
            worlds.hop(371);
            dialogues.selectOption(2);
            log("sleeping until in 371");
            new ConditionalSleep(30000)
            {
                @Override
                public boolean condition() throws InterruptedException
                {
                    return worlds.hop(371);
                }
            }.sleep();
        }

            log("Sleeping 8 seconds to prepare for widget interaction");
            sleep(8000);
        RS2Widget optionSetting =getWidgets().get(548,42 );
        RS2Widget zoomSetting = getWidgets().get(261,9 );
       // RS2Widget compassNorth =getWidgets().get(548,10 );
       // RS2Widget chestClose = getWidgets().get(12, 14, 11);
        RS2Widget controlSettings=getWidgets().get(261, 1, 7);
        RS2Widget npcOptions = getWidgets().get(261, 70, 4);
        RS2Widget npcRightclick = getWidgets().get(261, 86,4);
       // RS2Widget scrollBox = getWidgets().get(261, 87, 5);
        if (optionSetting != null)
        {
            log("option widget on screen");
            optionSetting.interact("Options");
           log("3 sec sleep");
            sleep(3000);
            log("zoom setting");
            zoomSetting.interact();
            log("3 sec sleep");
            sleep(3000);
            //log("Opening control settings");
            //controlSettings.interact();
          //  log("3 sec sleep");
           // sleep(3000);
           // log("opening npcOptions");
           // npcOptions.interact();
           // log("3 sec sleep");
           // sleep(3000);

             // log("switch trying to hide");
          ///  switch (getConfigs().get(1306))
           // {
            //    case 2: myPlayer().interact("Hidden");
          //  }


            //log("Hiding npc atk options");
            //npcRightclick.interact();
        }

       // log("Facing you north");
       // compassNorth.interact();



        /*
         bank.open();
        bank.depositAll();
        if (bank.isOpen())
        {
            bank.close();
        }
         */

/*
  log("walking to bank chest");
        if (!bankChest.contains(myPosition())&& !inventory.contains("Jug"))
        {
            getWalking().webWalk(bankChest);
        }

 */


    }

    @Override
    public void onExit() throws InterruptedException {
        super.onExit();
    }
    public final String formatTime(final long ms)
    {
        long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
        s %= 60; m %= 60; h %= 24;

        return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
                h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
                        String.format("%02d:%02d", m, s);
    }
    int jugsFilled=0;

    public void onMessage(Message message) throws java.lang.InterruptedException
    {
        String txt = message.getMessage().toLowerCase();
        if (message.getMessage().contains("You fill the jug"))
        {
           jugsFilled =jugsFilled+27;
        }

        if(message.getType() == Message.MessageType.GAME)
        {
            String text = message.getMessage();
            if (text.contains("!Mule"))
            {
                log("Mule");
            }
        }
    }



    public void checkforJugs() throws InterruptedException {
        log("Getting jugs");
        if (!inventory.contains("Jug")) //&& !tradeArea.contains(myPosition())
        {
            log("trying to open bank chest");
            bank.open();

            if (!inventory.isEmpty()) {
                bank.depositAll();
            }
            if (bank.contains("Jug")) {
                log("trying to withdraw jugs");
                bank.withdrawAll("Jug");
            }
            /*
             if (inventory.contains("Jug"))
            {
                fillJug();
            }
            else
            {   log("walking to trade area");
                getWalking().webWalk(tradeArea);
                log("fish moleyyy");
                muleTrade();
            }


             */
        }

        /*
         if (!inventory.contains("Jug")||!inventory.contains("Jug of water")&& !bank.isOpen())
            {
                log("Bank does not have jugs, waiting for mule");
                muleTrade();
            }
         */


        if (inventory.contains("Jug of water")&& bank.isOpen())
        {
            bank.depositAll();
        }
        else
         {
            fillJug();
         }
    }

    public void fillJug() throws InterruptedException
    {
        if(getWorlds().getCurrentWorld()!= 371)
        {
            dialogues.selectOption(1);
            log("Hopping to pvp world, 371");
            worlds.hop(371);
            dialogues.selectOption(2);
        }
        if (inventory.contains(1936))
        {
            bank.open();
            bank.depositAll();
            bank.close();
        }
        log("Moving pitch/yaw v2");
        camera.movePitch(67);
        camera.moveYaw(91);

        RS2Object fountainId = objects.closest(879);
        log("Bot has jugs, time to fill");

        /*
         if (inventory.contains("Jug") && !waterFountain.contains(myPosition()))
        {
            log("You have jugs, walking to water fountain");
            getWalking().webWalk(waterFountain);
            log("Using Jug");
            inventory.getItem("Jug").interact("Use");
        }
         */


        int run = getSettings().getRunEnergy();
        RS2Widget enableRun =getWidgets().get(160, 24);
        if (run >30 && !getSettings().isRunning())
        {
            log("Run is greater than 30 and we arent running, time to toggle");
            enableRun.interact();
        }
        /*
         if (run>30)
        {
            log("Run is greater than 30, time to toggle");
            switch (getConfigs().get(173))
            {
                case 0: enableRun.interact();
            }

           // enableRun.interact("Toggle Run");

        }

         */


        if (fountainId!=null)
        {
                if(inventory.contains("Jug"))
                {

                     if (bank.isOpen())
                    {
                        bank.close();
                    }


                    log("You have jugs, walking to water fountain");
                    log("Using Jug");
                    inventory.getItem("Jug").interact("Use");
                    // if (inventory.isItemSelected() && fountainId != null)
                    // {
                    if (!myPlayer().isAnimating())
                    {
                        log("Attempting to interact with water fountain");
                        fountainId.interact("Use");

                        //sleep(1000);
                    }


                    log(" 8 seccondi sleep");
                    new ConditionalSleep(8000)
                    {

                        @Override
                        public boolean condition() throws InterruptedException
                        {
                            return myPlayer().isAnimating();
                        }
                    }.sleep();

                }


                if (myPlayer().isAnimating())
                {
                   //RS2Object bankChest= getObjects().closest(7411);
                  //  if (bankChest!=null)
                    //{
                       // log("Hovering over bank chest 2 sec sleep");
                      //  sleep(2000);
                      //  bankChest.hover();
                  //  }

                    log("Sleeping until jugs are full");
                    new ConditionalSleep(20000)
                    {

                        @Override
                        public boolean condition() throws InterruptedException
                        {
                            return !inventory.contains("Jug");
                        }
                    }.sleep();
                }
           // }
        }
        if (inventory.contains("Jug of water")&& !inventory.contains("Jug"))
        {
            getWalking().webWalk(bankChest);
            objects.closest("Bank chest").interact("Use");
            log("trying to open bank chest");
            bank.open();
            bank.depositAll();
        }
    }

    public void muleTrade() throws InterruptedException
    {
        /*
         if (!bankChest.contains(myPosition()))
        {
            getWalking().webWalk(bankChest);
        }
         */

            log("Needs to get items from mule");
            if (getPlayers().closest(muleName) != null) //lvl 3 mule acc
            {
                bank.open();
                if (bank.contains("Jug of water"))
                {
                    if (bank.getWithdrawMode().equals(Bank.BankMode.WITHDRAW_NOTE))
                    {
                        bank.withdrawAll("Jug of water");
                    }
                    else getBank().enableMode(Bank.BankMode.WITHDRAW_NOTE); // else enable
                }
                getPlayers().closest(muleName).interact("Trade with");
                log("Sleeping 30 secs or until in the first trade screen");
                new ConditionalSleep(30000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return trade.isFirstInterfaceOpen();
                    }
                }.sleep();
                inventory.interact("Offer-All", 1938);
                log("Sleeping until they accept");
                new ConditionalSleep(30000)
                {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return trade.didOtherAcceptTrade();
                    }
                }.sleep();

                if (trade.isFirstInterfaceOpen() && trade.getTheirOffers().contains("Jug")) {
                    log("First screen open, and they wish to trade Jugs");
                    trade.acceptTrade();
                    log("sleeping until in second screen");
                    new ConditionalSleep(5000) {

                        @Override
                        public boolean condition() throws InterruptedException
                        {
                            return trade.isSecondInterfaceOpen();
                        }
                    }.sleep();
                }


                if (trade.isSecondInterfaceOpen())
                {
                    log("second trade screen is sucessfully open,pausing until other player accepts the trade");
                    new ConditionalSleep(5000) {

                        @Override
                        public boolean condition() throws InterruptedException {
                            return trade.didOtherAcceptTrade();
                        }
                    }.sleep();
                    log("Other player accepted,Attempting to accept trade");
                    trade.acceptTrade();
                    getWalking().webWalk(bankChest);
                    bank.open();
                    bank.depositAll();
                    bank.close();

                }

            }

            else
             {
                 log("sleeping 5 seconds since mule was not in sight");
                 sleep(5000);
             }


            /*
             bank.open();
                bank.depositAll();
                checkforJugs();
             */




    }

    @Override
    public int onLoop() throws InterruptedException
    {
        if (!inventory.contains("Jug","Jug of water")&& !bank.isOpen())
        {
            log("Inventory does not have jugs, checking bank");
            getWalking().webWalk(bankChest);
            objects.closest("Bank chest").interact("Use");
            log("Sleeping until bank is open");
            new ConditionalSleep(30000)
            {
                @Override
                public boolean condition() throws InterruptedException
                {
                    return bank.isOpen();
                }
            }.sleep();

        }

        if (!bank.contains("Jug")&& !inventory.contains(1935))
        {
            log("Bank does not have jugs either");
            bank.close();
            muleTrade();
        }

        checkforJugs();
        log("Getting items out of bank or preparing to trade mule");
        //muleTrade();

        return 600;
    }
    @Override
    public void onPaint(Graphics2D g)
    {
        final long runTime = System.currentTimeMillis()-startTime;
        Font font = new Font("Open Sans", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.CYAN);
        /*
        //time,exp per hr,profit
        super.onPaint(g);
        g.drawString("Elapsed Time: "  + formatTime(runTime), 2, 70);
        g.drawString("Dak's jugFiller",2,50);
        g.drawString("Jugs Filled: " + jugsFilled ,2,90);


         */

    }

}





