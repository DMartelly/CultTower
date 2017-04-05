package com.dmartelly.culttower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.Random;

public class CultTower extends ApplicationAdapter {

    private final int FLOOR_WIDTH = 1080;
    private final int FLOOR_HEIGHT = 960;
    private final int RESOLUTION = 32;
    private final float TEXT_POS_X = 68;
    private final float TEXT_POS_Y = 160;
    int vibePower = 5;
    private SpriteBatch main, hudBatch;
    private MainCharacter mainChar;
    private NPC npc_1, npc_2, npc_3, npc_4, npc_5;
    private Door door1, door2, door3, door4, door5;
    private Objects table, window, windowB, table2;
    private Texture backgroundTexture;
    private Sprite textBox;
    private BitmapFont environment, chat;
    private TextureRegion[][] animationFrames;
    private Animation<TextureRegion> animation;
    private float elaspedTime;
    private float vibe_x, vibe_y;
    private boolean viberate = false;
    private Random R = new Random();
    private boolean moving, debug = false, collision = false;

    private boolean locked = true;

    private OrthographicCamera cam;
    private int cameraState;
    private boolean npc4Act = false;
    private boolean npc4Dead = false;
    private boolean npc5Act = false, npc5Dead = false;

    /**
     * Loads the assets for the game
     */
    @Override
    public void create() {
        backgroundTexture = new Texture("bg_cobble.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //Sprite Batch
        main = new SpriteBatch();
        hudBatch = new SpriteBatch();

        //Text
        environment = new BitmapFont();
        chat = new BitmapFont();

        //Characters
        mainChar = new MainCharacter();

        npc_1 = new NPC(RESOLUTION * 2, RESOLUTION * 3);
        npc_1.addDictate("I can't wait to drink the punch!!\n:D");

        npc_2 = new NPC(RESOLUTION * 12, RESOLUTION * 4);
        npc_2.addDictate("That Girl is Too Excited...");

        npc_3 = new NPC(RESOLUTION * 2, RESOLUTION * 12);
        npc_3.addDictate("Are You Ready For Your Turn?");

        npc_4 = new NPC(RESOLUTION * 4, RESOLUTION * 30);
        npc_4.addDictate("Ever Since I Joined The Cult, I\n"
                + "Forgot About My Acrophobia");
        String yorN = "E = Yes / Q = NO";
        npc_4.addDictate("If I Push Him Out The Window, I\n"
                + "Should Be Able To Distract The Guards\n"
                + yorN);

        npc_5 = new NPC("Nope.png", 32, 300);
        npc_5.addDictate("Looks Like Some Good Punch");
        npc_5.addDictate("Should I Drink The Punch?\n\n"
                + yorN);

        door1 = new Door(FLOOR_WIDTH + mainChar.getWidth() / 2 - 2, FLOOR_HEIGHT - 700);
        door1.addText("I Don't Think Those Guards Are Going To\n"
                + "Let Me Through....");
        door1.addText("Next Level!!!");

        door2 = new Door(FLOOR_WIDTH + mainChar.getWidth() / 2 - 2, FLOOR_HEIGHT - 500);
        door3 = new Door(FLOOR_WIDTH + mainChar.getWidth() / 2 - 2, FLOOR_HEIGHT - 300);
        door4 = new Door(FLOOR_WIDTH - 200, FLOOR_HEIGHT + mainChar.getHeight());
        door5 = new Door(FLOOR_WIDTH - 580, FLOOR_HEIGHT + mainChar.getHeight());

        animationFrames
                = TextureRegion.split(new Texture("Cult_Guard_Left.png"), 16, 16);

        this.animation = new Animation<TextureRegion>(0.5f, this.animationFrames[0]);

        long currentTime = System.currentTimeMillis();

        vibe_x = mainChar.getX();
        vibe_y = mainChar.getY();

        //Camera
        cameraState = 0;
        cam = new OrthographicCamera(640, 480);
        cam.translate(cam.viewportWidth / 2, cam.viewportHeight / 2);
        cam.update();

        //Other
        this.table = new Objects("Punch_and_Table.png");
        this.table.setRect(table.getX(), table.getY());
        this.table.multRect(2);

        this.table2 = new Objects("Orb_and_Table.png");
        this.table2.setRect(table2.getX(), table2.getY());
        this.table2.multRect(2);

        this.window = new Objects("Window.png");
        this.windowB = new Objects("Window_Broken.png");

        textBox = new Sprite(new Texture("text_box.png"));
    }

    @Override
    public void render() {
        //Clear all renders
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set
        elaspedTime += Gdx.graphics.getDeltaTime();
        main.setProjectionMatrix(cam.combined);

        handleInput();
        handleCamera();

        if (viberate) {
            vibrate();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Start Drawing
        this.main.begin();

        //Draw Background
        this.main.draw(this.backgroundTexture, 0, 0,
                FLOOR_WIDTH + mainChar.getWidth() / 2, FLOOR_HEIGHT + mainChar.getHeight(), 0, 32, 32, 0);

        //NPC 1
        this.main.draw(npc_1.getTexture(), npc_1.getX(), npc_1.getY(),
                16 * 2, 16 * 2);
        this.npc_1.setRect(npc_1.getX(), npc_1.getY());
        this.npc_1.pacingLtoR();

        //NPC 2
        this.main.draw(npc_2.getTexture(), npc_2.getX(), npc_2.getY(),
                16 * 2, 16 * 2);
        this.npc_2.setRect(npc_2.getX(), npc_2.getY());
        this.npc_2.pacingUtoD();

        //NPC 3
        this.main.draw(npc_3.getTexture(), npc_3.getX(), npc_3.getY(),
                16 * 2, 16 * 2);
        this.npc_3.setRect(npc_3.getX(), npc_3.getY());

        //NPC 4
        if (!npc4Dead) {
            this.main.draw(npc_4.getTexture(), npc_4.getX(), npc_4.getY(),
                    16 * 2, 16 * 2);
            this.npc_4.setRect(npc_4.getX(), npc_4.getY());
        }

        //NPC 5
        if (!npc5Act) {
            this.main.draw(npc_5.getTexture(), npc_5.getX(), npc_5.getY(),
                    16 * 2, 16 * 2);
            this.npc_5.setRect(npc_5.getX(), npc_5.getY());
        }
        //Guards
        if (!npc4Dead) {
            this.main.draw(animation.getKeyFrame(elaspedTime, true),
                    FLOOR_WIDTH, door1.getY() + 40, 32, 32);
            this.main.draw(animation.getKeyFrame(elaspedTime, true),
                    FLOOR_WIDTH, door1.getY() - 34, 32, 32);
        } else {
            this.main.draw(animation.getKeyFrame(elaspedTime, true),
                    npc_4.getX() - 32, npc_4.getY(), 32, 32);
            this.main.draw(animation.getKeyFrame(elaspedTime, true),
                    npc_4.getX() + 32, npc_4.getY(), 32, 32);
            this.environment.draw(main, "OMG!!\nWhy?!?",
                    npc_4.getX() - 32, npc_4.getY());
            this.environment.draw(main, "Right?! He Should Have Just\n"
                            + "Drunk the Punch...",
                    npc_4.getX() + 32, npc_4.getY());
        }
        //Doors
        this.door1.draw(main);
        this.door2.draw(main);
        this.door3.draw(main);
        this.door4.draw(main);
        this.door5.draw(main);

        //Others
        this.main.draw(table, 0, 300, table.getWidth() * 2, table.getHeight() * 2);
        this.table.rect.setPosition(0, 300);

        this.main.draw(table2, 0, 200, table2.getWidth() * 2, table2.getHeight() * 2);
        this.table2.rect.setPosition(0, 200);

        if (!this.npc4Dead) {
            this.main.draw(window, npc_4.getX(), npc_4.getY() + 32, 32, 32);
        } else {
            this.main.draw(windowB, npc_4.getX(), npc_4.getY() + 32, 32, 32);
        }

        //Main Character
        TextureRegion a = (TextureRegion) mainChar.animation.getKeyFrame(elaspedTime, moving);
        this.main.draw(a, mainChar.getX(), mainChar.getY(), 32, 32);
        this.mainChar.setRect(mainChar.getX(), mainChar.getY());

        touchObjects();

        //Debug
        if (debug) {
            Debug();
        }

        this.main.end();

        //HUD
        hudBatch.begin();
        Chat();
        hudBatch.end();

        if (npc5Dead)

            Gdx.app.exit();
    }

    @Override
    public void dispose() {
        main.dispose();
        mainChar.getTexture().dispose();
        door1.getTexture().dispose();
        door2.getTexture().dispose();
        door3.getTexture().dispose();
        door4.getTexture().dispose();
        door5.getTexture().dispose();
        npc_1.getTexture().dispose();
        npc_2.getTexture().dispose();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        moving = false;

        if (mainChar.rect.overlaps(npc_4.rect)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                npc4Act = true;
            }
        }
        if (mainChar.rect.overlaps(npc_5.rect)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                npc5Act = true;
            }
        }

        if (!viberate) {
            //Left arrow
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    && mainChar.getX() > 0) {
                mainChar.rect.setX(mainChar.rect.getX() - mainChar.getMoveSpeed());
                if (this.ifCollision()) {
                    mainChar.rect.setX(mainChar.rect.getX() + mainChar.getMoveSpeed());
                } else {
                    mainChar.MoveLeft();
                    this.moving = true;
                }
            }

            //Right arrow
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    && mainChar.getX() < this.FLOOR_WIDTH) {
                mainChar.rect.setX(mainChar.rect.getX() + mainChar.getMoveSpeed());
                if (this.ifCollision()) {
                    mainChar.rect.setX(mainChar.rect.getX() - mainChar.getMoveSpeed());
                } else {
                    mainChar.MoveRight();
                    this.moving = true;
                }
            }
            //Up arrow
            if (Gdx.input.isKeyPressed(Input.Keys.UP)
                    && mainChar.getY() < this.FLOOR_HEIGHT) {
                mainChar.rect.setY(mainChar.rect.getY() + mainChar.getMoveSpeed());
                if (this.ifCollision()) {
                    mainChar.rect.setY(mainChar.rect.getY() - mainChar.getMoveSpeed());
                } else {
                    mainChar.MoveUp();
                    this.moving = true;
                }
            }
            //Down arrow
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)
                    && mainChar.getY() > 0) {
                mainChar.rect.setY(mainChar.rect.getY() - mainChar.getMoveSpeed());
                if (this.ifCollision()) {
                    mainChar.rect.setY(mainChar.rect.getY() + mainChar.getMoveSpeed());
                } else {
                    mainChar.MoveDown();
                    this.moving = true;
                }
            }

            //B - Vibrate
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {

                vibe_x = mainChar.getX();
                vibe_y = mainChar.getY();
                viberate = !viberate;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            this.viberate = !this.viberate;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (this.cameraState == 0) {
                this.cameraState = 1;
            } else if (this.cameraState == 1) {
                this.cameraState = 0;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            debug = !debug;
        }
        if (debug) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                locked = !locked;
            }
        }
    }

    private void vibrate() {
        mainChar.setX(vibe_x + (R.nextInt(5) - 2));
        mainChar.setY(vibe_y + (R.nextInt(5) - 2));
    }

    private void handleCamera() {
        switch (cameraState) {
            //Follow Main Character
            case 0:
                cam.position.set(this.mainChar.getX(), this.mainChar.getY(), 0);
                cam.zoom = 1f;
                cam.update();
                break;
            //Show entire room
            case 1:
                cam.position.set(this.FLOOR_WIDTH / 2, this.FLOOR_HEIGHT / 2, 0);
                cam.zoom = 3f;
                cam.update();
                break;
            //As is
            case 2:
                cam.update();
                break;
            // Follow main character on default
            default:
                cam.position.set(this.mainChar.getX(), this.mainChar.getY(), 0);
                cam.zoom = 1f;
                cam.update();
                break;
        }
    }

    private boolean ifCollision() {
        return (mainChar.rect.overlaps(table.rect)
                || mainChar.rect.overlaps(table2.rect));
    }

    private void touchObjects() {
        if (mainChar.rect.overlaps(door1.rect) && locked) {
            environment.draw(main, door1.getText(0), door1.getX() + 2, door1.getY() - 2);
        } else if (mainChar.rect.overlaps(door1.rect) && !locked) {
            environment.draw(main, door1.getText(1), door1.getX() + 2, door1.getY() - 2);
        }
        if (mainChar.rect.overlaps(door2.rect)) {
            environment.draw(main, "Just a Closet, How Boring...\n",
                    door2.getX() + 2, door2.getY() - 2);
        }
    }

    private void Chat() {

        if (mainChar.rect.overlaps(npc_1.rect)) {
            textBox.draw(hudBatch, 4f);
            chat.draw(hudBatch, npc_1.getDictate(0),
                    TEXT_POS_X, TEXT_POS_Y);
        }
        if (mainChar.rect.overlaps(npc_2.rect)) {
            textBox.draw(hudBatch, 4f);
            chat.draw(hudBatch, npc_2.getDictate(0),
                    TEXT_POS_X, TEXT_POS_Y);
        }
        if (mainChar.rect.overlaps(npc_3.rect)) {
            textBox.draw(hudBatch, 4f);
            chat.draw(hudBatch, npc_3.getDictate(0),
                    TEXT_POS_X, TEXT_POS_Y);
        }
        if (!npc4Dead) {
            if (mainChar.rect.overlaps(npc_4.rect)) {
                textBox.draw(hudBatch, 4f);
                chat.draw(hudBatch, npc_4.getDictate(0),
                        TEXT_POS_X, TEXT_POS_Y);
            }
        }
        if (this.npc4Act) {
            textBox.draw(hudBatch, 4f);
            chat.draw(hudBatch, npc_4.getDictate(1),
                    TEXT_POS_X, TEXT_POS_Y);
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                npc4Dead = true;
                this.npc4Act = false;
                this.locked = false;

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                this.npc4Act = false;
            }

        }
        if (!npc5Dead) {
            if (mainChar.rect.overlaps(npc_5.rect)) {
                textBox.draw(hudBatch, 4f);
                chat.draw(hudBatch, npc_5.getDictate(0),
                        TEXT_POS_X, TEXT_POS_Y);
            }
        }
        if (this.npc5Act) {
            textBox.draw(hudBatch, 4f);
            chat.draw(hudBatch, npc_5.getDictate(1),
                    TEXT_POS_X, TEXT_POS_Y);
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                npc5Dead = true;
                this.npc5Act = false;
                this.locked = false;

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                this.npc5Act = false;
            }

        }


    }

    private void Debug() {
        environment.draw(main, "Main Character\n"
                        + " X: " + mainChar.getX() + "\n"
                        + " Y: " + mainChar.getY(),
                mainChar.getX() + 200, mainChar.getY() + 220);
        if (mainChar.rect.overlaps(table.rect)) {
            environment.draw(main, "Touch Table...",
                    mainChar.getX(), mainChar.getY());
        }
    }
}
