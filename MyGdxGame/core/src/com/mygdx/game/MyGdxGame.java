package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class MyGdxGame extends Game {

	public SpriteBatch batch;//Отрисовка спрайтов

	public static final int V_WIDTH = 800;
	public static final int V_HEIGTH = 480;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short SHIP_BIT = 2;
	public static final short ASTEROID_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROIT_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short LASER_SHIP_BIT = 256;
	public static final short GUMBA = 512;
	public static final short SHIP_GUMBA1 = 1024;
	public static final short LASER_GUMBA_BIT = 2048;
	public static final short SHIP_GUMBA2 = 4096;
	public static final short SHIP_GUMBA3 = 8192;
	public static final short SHIP_GUMBA4 = 16364;
	public static final short SHIP_GUMBA5 = 16386;
	public static final short SHIP_GUMBA7 = 18654;
	public static final short Ground_Sun = 16402;
	public static final short Ground_BOSS = 16406;
	public static final short SHIP_BOSS = 16412;
	public static final short ITEM_BIT = 16424;








	public static final String TITLE = ("SUPER SPACE");

	public void create() {//Метод создания
	batch = new SpriteBatch();
		setScreen(new PlayScreen(this) );
	}

	public void render() {//Метод отрисовки
		super.render();

	}
	public void dispose(){
		super.dispose();
		batch.dispose();

	}
}
