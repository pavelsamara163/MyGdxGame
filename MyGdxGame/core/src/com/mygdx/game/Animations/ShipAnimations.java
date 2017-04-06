package com.mygdx.game.Animations;


/*public class ShipAnimations  {
    private static final int FRAME_COLS = 4; // Сколько спрайтов по вертикали
    private static final int FRAME_ROWS = 1; // Сколько спрайтов по горизонтали
    Animation shippAnimations; // обьявление обьекта который будет анимацией
    Texture shipAnimat; // текстура коробля
    TextureRegion[] shipFrames; // массив который содержит в себе каждый кадр анимации
    TextureRegion currentFrame; // будет содержать текущий кадр
    float stateTime; // кол во секунд прошедших с начала анимации
    Vector2 walkSHLoacation = new Vector2(0, 0);//Перемен0ая нахождения коробля

    public Vector2 getWalkSHLoacation() {
        return walkSHLoacation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public TextureRegion[] getShipFrames() {
        return shipFrames;
    }

    public Texture getShipAnimat() {
        return shipAnimat;
    }

    public Animation getShippAnimations() {
        return shippAnimations;
    }

    public ShipAnimations(int x,int y){
        shipAnimat  = new Texture("shipAnimation1.png");
        walkSHLoacation = new Vector2(20, (MyGdxGame.screenHidth / 2) - (shipAnimat.getHeight()));//Где появиться кораблс
        TextureRegion[][] tmp = TextureRegion.split(shipAnimat, shipAnimat.getWidth() / FRAME_COLS, shipAnimat.getHeight() / FRAME_ROWS); // #10
        shipFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {

            for (int j = 0; j < FRAME_COLS; j++) {
                shipFrames[index++] = tmp[i][j];

            }
        }
        shippAnimations = new Animation(0.150f,shipFrames); // #11
        stateTime = 0f; // #13

    }
    public void update(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {//Движение вверх
            if (walkSHLoacation.y < MyGdxGame.screenHidth - shipAnimat.getHeight());//Этим мы закрываем вверхнюю границу экрана чтобы корабль неулетал впустоту
            walkSHLoacation.y += 5;//движение коробля вверх

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && (walkSHLoacation.y > 0)) {//Так мы закрываем нижнюю границу экрана
            walkSHLoacation.y -= 5;//движение коробля вниз
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && (walkSHLoacation.x > 0)) {//Заблокировали экран слева
            walkSHLoacation.x -= 5;//движение коробля влеево
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && (walkSHLoacation.x < MyGdxGame.screenWidth + shipAnimat.getWidth())) {//Блок право
            walkSHLoacation.x += 5;//движение коробля вправо
        }

    }
}*/
