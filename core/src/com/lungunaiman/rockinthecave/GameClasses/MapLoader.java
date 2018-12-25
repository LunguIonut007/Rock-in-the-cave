package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Entities.EntityUpdater;
import com.lungunaiman.rockinthecave.Entities.Player;
import com.lungunaiman.rockinthecave.Events.SpawnEvent;
import com.lungunaiman.rockinthecave.Events.SpawnEventManager;


public class MapLoader
{
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteMapRenderer spriteMapRenderer;
    private EntityUpdater updater;
    private EntityFactory entityFactory;

    public MapLoader(EntityUpdater updater)
    {
        this.updater = updater;
        entityFactory = new EntityFactory();
    }

    public void loadMap(String name)
    {
        map = GameAssetManager.getInstance().getManager().get("maps/"+name+".tmx",TiledMap.class);
        spriteMapRenderer = new SpriteMapRenderer();
        renderer = new OrthogonalTiledMapRenderer(map,1/8f);
        loadObjectLayer("ground");
        loadObjectLayer("coins");
        loadObjectLayer("tepi");
        loadObjectLayer("moving");
        loadObjectLayer("turn");
        loadObjectLayer("jumping");
        loadObjectLayer("diss");
        loadObjectLayer("spikes");
        loadObjectLayer("mapch");
        loadObjectLayer("button");
        loadObjectLayer("turnt");
        loadObjectLayer("nivel");
        loadObjectLayer("lie");
        loadSpawnPoints("spawn");

    }

    public void loadSpawnPoints(String name)
    {

        MapLayer mapLayer = map.getLayers().get(name);
        if(mapLayer!=null)
        {
            MapObjects mapObjects = mapLayer.getObjects();
            for (MapObject object : mapObjects)
            {
                RectangleMapObject obj = (RectangleMapObject)object;
                int index = Integer.parseInt(object.getProperties().get("index",String.class));
                SpawnSaver.getInstance().addSpawn(new Vector2(obj.getRectangle().x,obj.getRectangle().y),index);
            }
        }
    }

    public void loadObjectLayer(String name)
    {

       MapLayer mapLayer = map.getLayers().get(name);
        if(mapLayer!=null)
        {
            MapObjects mapObjects = mapLayer.getObjects();

            for (MapObject object : mapObjects)
            {
                Entity entity = entityFactory.produce(name, object);
                if (!entity.getName().equals("null")) updater.addEntity(entity);
            }
        }
    }


    public void renderMap(int[] a)
    {

       renderer.setView((OrthographicCamera) Player.viewport.getCamera());
        renderer.render(a);

    }

    public void renderObj(String name)
    {
        spriteMapRenderer.setView((OrthographicCamera) Player.viewport.getCamera());
        spriteMapRenderer.renderObjects(map.getLayers().get(name));
    }

    public void dispose()
    {
        map.dispose();
        renderer.dispose();
    }
}
