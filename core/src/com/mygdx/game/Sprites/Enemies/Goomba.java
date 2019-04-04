package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.ZickZackJump;

public class Goomba extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private float initPos, finalPos;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        initPos = b2body.getPosition().x - 1 / ZickZackJump.PPM;
        System.out.println(" Init Goomba" + initPos);
        finalPos = initPos + 20/ ZickZackJump.PPM;
        System.out.println(" FInal Goomba" + finalPos);
        setBounds(getX(), getY(), 16/ ZickZackJump.PPM, 16/ZickZackJump.PPM);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        if (b2body.getPosition().x <= initPos || b2body.getPosition().x >=finalPos)
            reverseVelocity(true, false);
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() / 2);

        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
    }


    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ ZickZackJump.PPM);
        fdef.filter.categoryBits = ZickZackJump.ENEMY_BIT;
        fdef.filter.maskBits = ZickZackJump.GROUND_BIT |
                ZickZackJump.COIN_BIT |
                ZickZackJump.BRICK_BIT |
                ZickZackJump.ENEMY_BIT |
                ZickZackJump.OBJECT_BIT |
                ZickZackJump.JUMPER_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / ZickZackJump.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / ZickZackJump.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / ZickZackJump.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / ZickZackJump.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = ZickZackJump.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }



    @Override
    public void hitOnHead(Jumper jumper) {

    }

    @Override
    public void hitByEnemy(Enemy enemy) {

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
