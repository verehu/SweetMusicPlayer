package de.greenrobot.daogenerator;

public class MyDaoGenerator {
    public static final int VERSION = 1;

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(VERSION, "com.huwei.sweetmusicplayer.models");

        addMusicInfo(schema);
        addAlbumInfo(schema);

        new DaoGenerator().generateAll(schema,"./greendaogenerator/src-gen");
    }

    public static void addMusicInfo(Schema schema) {
        Entity entity = schema.addEntity("MusicInfo");
        entity.addLongProperty("songId").primaryKey();
        entity.addLongProperty("albumId");
        entity.addStringProperty("title");
        entity.addStringProperty("artist");
        entity.addIntProperty("duration");
        entity.addStringProperty("path");
        entity.addBooleanProperty("favorite");
    }

    public static void addAlbumInfo(Schema schema){
        Entity entity=schema.addEntity("AlbumInfo");
        entity.addLongProperty("albumId").primaryKey();
        entity.addStringProperty("title");  //专辑名
        entity.addStringProperty("artist");
        entity.addIntProperty("numSongs");
        entity.addStringProperty("albumArt");   //封面地址
    }
}
