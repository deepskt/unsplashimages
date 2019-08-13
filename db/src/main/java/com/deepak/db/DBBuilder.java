package com.deepak.db;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DBBuilder {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.deepak.mobikwikimage.database");
        addPhotoTable(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addPhotoTable(Schema schema) {
        Entity photoUnsplash = schema.addEntity("PhotoUnsplash");
        photoUnsplash.addStringProperty("id").primaryKey();
        photoUnsplash.addStringProperty("alt_description");
        photoUnsplash.addByteArrayProperty("imageData");
        photoUnsplash.addStringProperty("urls");
        photoUnsplash.addIntProperty("page").index();
    }
}
