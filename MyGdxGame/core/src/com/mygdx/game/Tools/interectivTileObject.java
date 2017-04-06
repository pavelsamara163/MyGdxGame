package com.mygdx.game.Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Ship;

public abstract  class interectivTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected MapObject object;
    protected PlayScreen  screen ;

protected Fixture fixture;

    public interectivTileObject(PlayScreen screen,MapObject object ){
        this.screen = screen ;
        this.object = object;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();


        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2) / MyGdxGame.PPM   ,(bounds.getY() + bounds.getHeight()/2) / MyGdxGame.PPM  );

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2 / MyGdxGame.PPM   ,bounds.getHeight()/2 / MyGdxGame.PPM  );
        fdef.shape = shape ;
        fixture = body.createFixture(fdef);

    }
       public abstract void onHeadHit(Ship ship);



    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(6);
        return layer.getCell((int)(body.getPosition().x *  MyGdxGame.PPM  /64),(int)(body.getPosition().y * MyGdxGame.PPM  / 64));

    }
}
