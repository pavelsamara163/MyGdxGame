package com.mygdx.game.Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Asteroids.Asteroid;
import com.mygdx.game.Asteroids.AsteroidEnemy;
import com.mygdx.game.Asteroids.AsteroidEnemy1;
import com.mygdx.game.Asteroids.AsteroidEnemy2;
import com.mygdx.game.Asteroids.AsteroidEnemy3;
import com.mygdx.game.Ground.GroundBoss;
import com.mygdx.game.Ground.GroundSun;
import com.mygdx.game.Items.SuperShip;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.ShipEnemies.ShipGoomba;
import com.mygdx.game.ShipEnemies.ShipGoomba2;
import com.mygdx.game.ShipEnemies.ShipGoomba3;
import com.mygdx.game.ShipEnemies.ShipGoomba4;
import com.mygdx.game.ShipEnemies.ShipGoomba5;
import com.mygdx.game.ShipEnemies.ShipGoomba6;
import com.mygdx.game.ShipEnemies.ShipGoomba7;
import com.mygdx.game.ShipEnemies.shipBoss;
import com.mygdx.game.Sprites.Coin;
import com.mygdx.game.Sprites.bonusItem;

public class B2WorldCreated {
    private Array<GroundSun> groundSuns;
    private Array<GroundBoss> groundBosses;
    private Array<ShipGoomba> shipGoomba;
    private Array <ShipGoomba2> shipGoombas2;
    private Array <ShipGoomba3> shipGoomba3;
    private Array <ShipGoomba4> shipGoomba4;
    private Array <ShipGoomba5> shipGoomba5;
    private Array <ShipGoomba6> shipGoomba6;
    private Array <ShipGoomba7> shipGoomba7;
    private Array <AsteroidEnemy> asteroidEnemies;
    private Array <AsteroidEnemy1> asteroidEnemies1;
    private Array <AsteroidEnemy2> asteroidEnemies2;
    private Array <AsteroidEnemy3> asteroidEnemies3;
    private Array <shipBoss> shipBosses;
    private Array<bonusItem> bonusItems;
    private Array<SuperShip> superShips;

    public Array<GroundSun> getGroundSuns() {
        return groundSuns;
    }
    public Array<GroundBoss> groundBosses() {
        return groundBosses;
    }
    public Array<ShipGoomba> getShipGoombas() {
        return shipGoomba;
    }
    public Array<ShipGoomba2> getShipGoombas2() {
        return shipGoombas2;
    }
    public Array<ShipGoomba3> getShipGoomba3(){ return shipGoomba3;}
    public Array<ShipGoomba4> getShipGoomba4(){ return shipGoomba4;}
    public Array<ShipGoomba5> getShipGoomba5(){ return shipGoomba5;}
    public Array<ShipGoomba6> getShipGoomba6(){ return shipGoomba6;}
    public Array<ShipGoomba7> getShipGoomba7(){ return shipGoomba7;}
    public Array<AsteroidEnemy> getAsteroidEnemies(){ return asteroidEnemies;}
    public Array<AsteroidEnemy1> getAsteroidEnemies1(){ return asteroidEnemies1;}
    public Array<AsteroidEnemy2> getAsteroidEnemies2(){ return asteroidEnemies2;}
    public Array<AsteroidEnemy3> getAsteroidEnemies3(){ return asteroidEnemies3;}
    public Array<shipBoss> getShipBosses(){ return shipBosses;}
    public Array<bonusItem> getBonusItems(){return bonusItems;}
    public Array<SuperShip> getSuperShips(){return superShips;}

    public B2WorldCreated(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Asteroid(screen, object);
        }
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM  , (rect.getY() + rect.getHeight() / 2 ) / MyGdxGame.PPM  );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /  2 / MyGdxGame.PPM   , rect.getHeight() / 2 / MyGdxGame.PPM   );
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new Coin(screen, object);

        }
        //Create world ground fon
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM , (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM , rect.getHeight() / 2 / MyGdxGame.PPM );
            fdef.shape = shape;
            fdef.filter.categoryBits = MyGdxGame.GROUND_BIT;
            body.createFixture(fdef);
        }
        //спавн своих кораблей
             groundSuns = new Array<GroundSun>();
        for (MapObject object : map.getLayers().get(20).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            groundSuns.add(new GroundSun(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }
        //спавн обьектов за боссом
        groundBosses  = new Array<GroundBoss>();
        for (MapObject object : map.getLayers().get(21).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            groundBosses.add(new GroundBoss(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }
        //Спавн шипнГумбас в мире
       shipGoomba = new Array<ShipGoomba>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba.add(new ShipGoomba(screen,rect.getX() / MyGdxGame.PPM ,rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн шипнГумбас2 в мире
        shipGoombas2 = new Array<ShipGoomba2>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoombas2.add(new ShipGoomba2(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн шипнГумбас3 в мире
        shipGoomba3 = new Array<ShipGoomba3>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba3.add(new ShipGoomba3(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн шипнГумбас4 в мире
        shipGoomba4 = new Array<ShipGoomba4>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba4.add(new ShipGoomba4(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн шипнГумбас5 в мире
        shipGoomba5 = new Array<ShipGoomba5>();
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba5.add(new ShipGoomba5(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
    }
        //Спавн шипнГумбас6 в мире
        shipGoomba6 = new Array<ShipGoomba6>();
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba6.add(new ShipGoomba6(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн шипнГумбас7 в мире
        shipGoomba7 = new Array<ShipGoomba7>();
        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipGoomba7.add(new ShipGoomba7(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн Астеройда0 в мире
       asteroidEnemies = new Array<AsteroidEnemy>();
        for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            asteroidEnemies.add(new AsteroidEnemy(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн Астеройда1 в мире
        asteroidEnemies1 = new Array<AsteroidEnemy1>();
        for (MapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            asteroidEnemies1.add(new AsteroidEnemy1(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн Астеройда2 в мире
        asteroidEnemies2 = new Array<AsteroidEnemy2>();
        for (MapObject object : map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            asteroidEnemies2.add(new AsteroidEnemy2(screen, rect.getX() / MyGdxGame.PPM , rect.getY()/ MyGdxGame.PPM ));
        }
        //Спавн Астеройда3 в мире
        asteroidEnemies3 = new Array<AsteroidEnemy3>();
        for (MapObject object : map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            asteroidEnemies3.add(new AsteroidEnemy3(screen, rect.getX()/ MyGdxGame.PPM , rect.getY()/ MyGdxGame.PPM ));
        }
        //Спавн shipSergey в мире
        shipBosses = new Array<shipBoss>();
        for (MapObject object : map.getLayers().get(19).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            shipBosses.add(new shipBoss(screen, rect.getX() / MyGdxGame.PPM , rect.getY() / MyGdxGame.PPM ));
        }
        //Спавн Бонусного обьекта
         bonusItems  = new Array<bonusItem>();
        for (MapObject object : map.getLayers().get(22).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bonusItems.add(new bonusItem(screen, rect.getX()/ MyGdxGame.PPM , rect.getY()/ MyGdxGame.PPM ));

        }
        //Спавн супершип
      /*  superShips  = new Array<SuperShip>();
        for (MapObject object : map.getLayers().get(23).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            superShips.add(new SuperShip(screen, rect.getX()/ MyGdxGame.PPM , rect.getY()/ MyGdxGame.PPM ));

        }*/

        /*for(MapObject object : map.getLayers().get(23).getObjects().getByType(RectangleMapObject.class)) {
            new bonusItem(screen, object);*/

    }
}


