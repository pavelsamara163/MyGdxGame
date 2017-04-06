package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemDef;
import com.mygdx.game.Items.SuperShip;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
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
import com.mygdx.game.Tools.B2WorldCreated;
import com.mygdx.game.Tools.WorldContactListner;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen  {
    private TextureAtlas atlas3;
    private TextureAtlas atlas2;
    private TextureAtlas atlas1;
    private TextureAtlas atlas4;
    private TextureAtlas atlas5;
    private TextureAtlas atlas6;
    private TextureAtlas atlas7;
    private TextureAtlas atlas8;
    private TextureAtlas atlas9;
    private TextureAtlas atlas10;

    private MyGdxGame game;
    private Hud hud;
    private TmxMapLoader mapLoader ;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera gamecam;//камера которая будет показывать мир игры
    private Viewport gamePort;//Изображение будет растягиваться под экран телефона
    //box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreated creator;//ShipGoomba
    private Ship player;
    protected boolean destroyed;


    private Array<Item> items;
    private LinkedBlockingQueue <ItemDef> itemsToSpawn;



    public PlayScreen(MyGdxGame game){
        atlas1 = new TextureAtlas("texture_add_1.pack");
        atlas2 = new TextureAtlas("texture_add_2.pack");
        atlas3 = new TextureAtlas("texture_add_3.pack");
        atlas4 = new TextureAtlas("rock_add_4.pack");
        atlas5 = new TextureAtlas("Explousing_add.pack");
        atlas6 = new TextureAtlas("bullet_Enemies.pack");
        atlas7 = new TextureAtlas("Bullet1.pack");
        atlas8 = new TextureAtlas("Boss.pack");
        atlas9 = new TextureAtlas("Explosion_add_1.pack");
        atlas10 = new TextureAtlas("planet_Pack.pack");

        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FillViewport(MyGdxGame.V_WIDTH /MyGdxGame.PPM    ,MyGdxGame.V_HEIGTH / MyGdxGame.PPM    ,gamecam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Asteroid.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM  );
        gamecam.position.set(gamePort.getWorldWidth()/2 ,gamePort.getWorldHeight()/2,0 );
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreated(this);

        player = new Ship(this);


        world.setContactListener(new WorldContactListner());

        items = new Array<Item>() ;
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>() {
        } ;



    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handlesSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == SuperShip.class  )
                items.add(new SuperShip(this, idef.position.x  , idef.position.y ));


        }
    }

    public TextureAtlas getAtlas1(){
        return atlas1;
    }
    public TextureAtlas getAtlas2(){
        return atlas2;

    }
    public TextureAtlas getAtlas3(){
        return atlas3;
    }
    public TextureAtlas getAtlas4(){
        return atlas4;
    }
    public TextureAtlas getAtlas5() {
        return atlas5;
    }
    public TextureAtlas getAtlas6() {
        return atlas6;
    }
    public TextureAtlas getAtlas7() {
        return atlas7;
    }
    public TextureAtlas getAtlas8() {
        return atlas8;
    }
    public TextureAtlas getAtlas9() {
        return atlas9;
    }
    public TextureAtlas getAtlas10() {
        return atlas10;
    }
    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if (player.currentState != Ship.State.DEAD) {
            if (Gdx.input.isKeyPressed(Input.Keys.W))
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.S))
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);

            if (Gdx.input.justTouched())
                player.fire();

        }
    }
        public void update(float dt){
            handleInput(dt);
            handlesSpawningItems();
            world.step(1 / 60f, 6, 6);
            gamecam.update();
            player.update(dt);



            for(Enemy enemy : creator.getGroundSuns())//рендер множества шипгумба
                enemy.update(dt);
            for(Enemy enemy : creator.groundBosses())//рендер множества шипгумба
                enemy.update(dt);
            for(ShipGoomba  shipGoomba : creator.getShipGoombas())  //прописываем множественное появление вражеских кораблей по x,y
               shipGoomba.update(dt);
            for(ShipGoomba2 shipGoomba2 : creator.getShipGoombas2())//прописываем множественное появление вражеских кораблей по x,y
                shipGoomba2.update(dt);
            for(ShipGoomba3 shipGoomba3 : creator.getShipGoomba3())//прописываем множественное появление вражеских кораблей по x,y
                shipGoomba3.update(dt);
            for(ShipGoomba4 shipGoomba4 : creator.getShipGoomba4())//прописываем множественное появление вражеских кораблей по x,y
                shipGoomba4.update(dt);
            for(ShipGoomba5 shipGoomba5 : creator.getShipGoomba5())//прописываем множественное появление вражеских кораблей по x,y
                shipGoomba5.update(dt);
            for(Enemy enemy : creator.getShipGoomba6())//прописываем множественное появление вражеских кораблей по x,y
                enemy.update(dt);
            for(ShipGoomba7 shipGoomba7 : creator.getShipGoomba7())//прописываем множественное появление вражеских кораблей по x,y
                shipGoomba7.update(dt);
            for(Enemy enemy : creator.getAsteroidEnemies())//прописываем множественное появление вражеских кораблей по x,y
                enemy.update(dt);
            for(Enemy enemy : creator.getAsteroidEnemies1())//прописываем множественное появление вражеских кораблей по x,y
                enemy.update(dt);
            for(Enemy enemy : creator.getAsteroidEnemies2())//прописываем множественное появление вражеских кораблей по x,y
                enemy.update(dt);
            for(Enemy enemy : creator.getAsteroidEnemies3())//прописываем множественное появление вражеских кораблей по x,y
                enemy.update(dt);
            for(shipBoss shipBoss : creator.getShipBosses())//прописываем множественное появление вражеских кораблей по x,y
                shipBoss.update(dt);
            for(bonusItem bonusItem : creator.getBonusItems())
                bonusItem.update(dt);

            for(Item item : items )
                item.update(dt);

            hud.update(dt);

            if(player.currentState != Ship.State.DEAD) {
                gamecam.position.x = player.b2body.getPosition().x;
            }
            renderer.setView(gamecam);
        }
    @Override
    public void render(float delta) {
        update(delta) ;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //Box2DDebugers
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);



        for(Enemy enemy : creator.getGroundSuns())//рендер множества шипгумба
            enemy.draw(game.batch);
        for(Enemy enemy : creator.groundBosses())//рендер множества шипгумба
            enemy.draw(game.batch);
        for(ShipGoomba  shipGoomba : creator.getShipGoombas())//рендер множества шипгумба
            shipGoomba.draw(game.batch);
        for(ShipGoomba2 shipGoomba2 : creator.getShipGoombas2())//прописываем множественное появление вражеских кораблей по x,y
            shipGoomba2.draw(game.batch);
        for(ShipGoomba3  shipGoomba3 : creator.getShipGoomba3())//прописываем множественное появление вражеских кораблей по x,y
            shipGoomba3.draw(game.batch);
        for(ShipGoomba4 shipGoomba4 : creator.getShipGoomba4())//прописываем множественное появление вражеских кораблей по x,y
            shipGoomba4.draw(game.batch);
        for(ShipGoomba5 shipGoomba5 : creator.getShipGoomba5())//прописываем множественное появление вражеских кораблей по x,y
            shipGoomba5.draw(game.batch);
        for(Enemy enemy : creator.getShipGoomba6())//прописываем множественное появление вражеских кораблей по x,y
            enemy.draw(game.batch);
        for(ShipGoomba7 shipGoomba7 : creator.getShipGoomba7())//прописываем множественное появление вражеских кораблей по x,y
            shipGoomba7.draw(game.batch);
        for(Enemy enemy : creator.getAsteroidEnemies())//прописываем множественное появление вражеских кораблей по x,y
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getAsteroidEnemies1())//прописываем множественное появление вражеских кораблей по x,y
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getAsteroidEnemies2())//прописываем множественное появление вражеских кораблей по x,y
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getAsteroidEnemies3())//прописываем множественное появление вражеских кораблей по x,y
            enemy.draw(game.batch);
        for(shipBoss shipBoss : creator.getShipBosses())//прописываем множественное появление вражеских кораблей по x,y
            shipBoss.draw(game.batch);
        for(bonusItem bonusItem : creator.getBonusItems())
            bonusItem.draw(game.batch);

        for(Item item : items )
            item.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }


        public boolean gameOver(){
            if(player.currentState  == Ship.State.DEAD && player.getStateTimer() > 2 ){
                return true ;
            }
            return false;
        }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld (){
        return world ;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
            map.dispose();
            renderer.dispose();
            world.dispose();
            b2dr.dispose();
            hud.dispose();

    }
}
