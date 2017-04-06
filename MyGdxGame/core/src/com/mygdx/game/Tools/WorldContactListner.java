package com.mygdx.game.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Ground.GroundSun;
import com.mygdx.game.Items.Item;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ShipEnemies.ShipGoomba;
import com.mygdx.game.ShipEnemies.ShipGoomba2;
import com.mygdx.game.ShipEnemies.ShipGoomba3;
import com.mygdx.game.ShipEnemies.ShipGoomba4;
import com.mygdx.game.ShipEnemies.ShipGoomba5;
import com.mygdx.game.ShipEnemies.ShipGoomba7;
import com.mygdx.game.ShipEnemies.shipBoss;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Ship;
import com.mygdx.game.Sprites.bonusItem;

public class WorldContactListner implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA =  contact.getFixtureA();
        Fixture fixB =  contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(cDef) {


            case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()). hitOnHead();
                break;

            case MyGdxGame.SHIP_GUMBA1 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA1)
                    ((ShipGoomba) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_GUMBA1 | MyGdxGame.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA1)
                    ((ShipGoomba) fixA.getUserData()).reverseVelocity(false, true);
                else
                    ((ShipGoomba) fixB.getUserData()).reverseVelocity(false, true);
                break;
            case MyGdxGame.SHIP_GUMBA2 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA2)
                    ((ShipGoomba2) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba2)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.SHIP_GUMBA2:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;


            case MyGdxGame.SHIP_GUMBA3 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA3)
                    ((ShipGoomba3) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba3)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.SHIP_GUMBA3:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;
            case MyGdxGame.SHIP_GUMBA4 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA4)
                    ((ShipGoomba4) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba4)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.SHIP_GUMBA4:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;
            case MyGdxGame.SHIP_GUMBA5 | MyGdxGame.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA5)
                    ((ShipGoomba5) fixA.getUserData()).reverseVelocity(false, true);
                else
                    ((ShipGoomba5) fixB.getUserData()).reverseVelocity(false, true);
                break;
            case MyGdxGame.SHIP_GUMBA5 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA5)
                    ((ShipGoomba5) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba5)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.SHIP_GUMBA5:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;
            case MyGdxGame.SHIP_GUMBA7 | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_GUMBA7)
                    ((ShipGoomba7) fixA.getUserData()).hitOnHead();
                else
                    ((ShipGoomba7)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.LASER_GUMBA_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.LASER_GUMBA_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;
            case MyGdxGame.SHIP_BIT | MyGdxGame.SHIP_GUMBA7:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixA.getUserData()).hit();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.SHIP_BIT)
                    ((Ship)fixB.getUserData()).hit();
                break;

            case MyGdxGame.Ground_Sun | MyGdxGame.SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.Ground_Sun)
                    ((GroundSun) fixA.getUserData()).hitOnHead();
                else
                    ((GroundSun)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BOSS | MyGdxGame.SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BOSS)
                    ((shipBoss) fixA.getUserData()).hitOnHead();
                else
                    ((shipBoss)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BOSS | MyGdxGame.LASER_SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BOSS)
                    ((shipBoss) fixA.getUserData()).hitOnHead();
                else
                    ((shipBoss)fixB.getUserData()). hitOnHead();
                break;
            case MyGdxGame.SHIP_BOSS | MyGdxGame.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.SHIP_BOSS)
                    ((shipBoss) fixA.getUserData()).reverseVelocity(false, true);
                else
                    ((shipBoss) fixB.getUserData()).reverseVelocity(false, true);
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.LASER_SHIP_BIT:
            if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                ((bonusItem)fixA.getUserData()).hitOnHead();
            else
                ((bonusItem)fixB.getUserData()).hitOnHead();
            break;
            case MyGdxGame.ITEM_BIT | MyGdxGame.SHIP_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Ship) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Ship) fixA.getUserData());
                break;







        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
