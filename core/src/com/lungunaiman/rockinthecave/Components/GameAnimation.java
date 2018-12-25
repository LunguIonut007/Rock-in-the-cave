package com.lungunaiman.rockinthecave.Components;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class GameAnimation
{

    private TextureRegion currentFrame;
    private float elapsedTime;
    private Vector2 pos;
    private Animation animation;
    private float scale  = 1/8f;

    public GameAnimation(String frameName,float frameDuration,float scale,Vector2 pos)
    {
        this.pos = pos;
        this.scale = scale;
        TextureAtlas textureAtlas = GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(),TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> runningFrames = textureAtlas.findRegions(frameName);
        animation = new Animation(frameDuration,runningFrames);
        currentFrame = animation.getKeyFrame(0);

    }

    public GameAnimation(FileHandle file, String frameName, float frameDuration, float scale)
    {
        pos = new Vector2(0,0);
        this.scale = scale;
        TextureAtlas textureAtlas = new TextureAtlas(file);
        Array<TextureAtlas.AtlasRegion> runningFrames = textureAtlas.findRegions(frameName);
        animation = new Animation(frameDuration,runningFrames);
        currentFrame = animation.getKeyFrame(0);
    }

    public void update(Vector2 pos,float delta,boolean isLoop)
    {
        this.pos = pos;

        elapsedTime +=delta;
        currentFrame = animation.getKeyFrame(elapsedTime,isLoop);

    }

    public void render(float delta, SpriteBatch spriteBatch,int sign)
    {

        spriteBatch.draw(currentFrame,
                pos.x-sign* currentFrame.getRegionWidth()/2*scale,
                pos.y- currentFrame.getRegionHeight()/2*scale,
                0,0,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                sign*scale,scale,0);

    }
}
