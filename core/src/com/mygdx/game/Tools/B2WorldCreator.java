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
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Goomba;
import com.mygdx.game.Sprites.TileObjects.Brick;
import com.mygdx.game.Sprites.TileObjects.Coin;
import com.mygdx.game.Sprites.TileObjects.Ground;
import com.mygdx.game.Sprites.TileObjects.Pipe;
import com.mygdx.game.ZickZackJump;

public class B2WorldCreator {
    private Array<Goomba> goombas;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects()
                .getByType(RectangleMapObject.class)){
            new Ground(screen, object);

        }



        //create pipe bodies/fixtures
        for (MapObject object: map.getLayers().get(3).getObjects()
                .getByType(RectangleMapObject.class)){
            new Pipe(screen, object);
        }



        //create brick bodies/fixtures
        for (MapObject object: map.getLayers().get(5).getObjects()
                .getByType(RectangleMapObject.class)){
            new Brick(screen, object);
        }



        //create coin bodies/fixtures
        for (MapObject object: map.getLayers().get(4).getObjects()
                .getByType(RectangleMapObject.class)){
            new Coin(screen, object);
        }


        //create all goombas
        goombas = new Array<Goomba>();
        for (MapObject object: map.getLayers().get(6).getObjects()
                .getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX()/ ZickZackJump.PPM,
                    rect.getY()/ ZickZackJump.PPM));
        }
    }

    public Array<Goomba> getGoombas(){
        return goombas;
    }
}
