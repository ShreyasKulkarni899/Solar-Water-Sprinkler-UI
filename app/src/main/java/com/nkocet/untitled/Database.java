package com.nkocet.untitled;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Variable Initialisations
    private static final String
            TABLE_NAME = "Devices",
            COL_0 = "DEV_ID",
            COL_1 = "Object",
            TAG = "Database";

    private static final ArrayList<String[]> lightPalette = new ArrayList<>(),
            darkPalette = new ArrayList<>();

    static {
        lightPalette.add(new String[]{"#FCF0F0", "#F8A9A9", "#FFFFFF"});
        lightPalette.add(new String[]{"#EFFFF9", "#95FFD6", "#0A0057"});
        lightPalette.add(new String[]{"#FFF0F7", "#FF98C8", "#FFFFFF"});
        lightPalette.add(new String[]{"#EAF4FF", "#8BC1FF", "#FFFFFF"});
        lightPalette.add(new String[]{"#CBE9FF", "#2A6B9B", "#FFFFFF"});
        lightPalette.add(new String[]{"#EAFFF8", "#3D715F", "#FFFFFF"});
        darkPalette.add(new String[]{"#404040", "#3B3B3B", "#FFFFFF"});
    }

    public Database(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd = String.format("CREATE TABLE %s (%s TEXT, %s BLOB)",
                TABLE_NAME, COL_0, COL_1);
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cmd = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(cmd);
        onCreate(db);
    }

    public void insertCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(card);
            objectOutputStream.flush();
            byte[] objArray = byteArrayOutputStream.toByteArray();
            int nextId = Integer.parseInt(getLastId()) + 1;
            contentValues.put(COL_0, nextId);
            contentValues.put(COL_1, objArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Card> getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Card> cards = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                cards.add((Card) objectInputStream.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return cards;
    }

    public String getLastId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) return "0";
        cursor.moveToLast();
        return cursor.getString(0);
    }

    public void editCard(Card initCard, Card finalCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(finalCard);
            objectOutputStream.flush();
            byte[] objArray = byteArrayOutputStream.toByteArray();
            contentValues.put(COL_1, objArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.update(TABLE_NAME, contentValues, COL_0 + "=?", new String[]{initCard.id});
    }

    public void delete(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, String.format("delete: %s", card.id));
        db.delete(TABLE_NAME, COL_0 + "=?", new String[]{card.id});
    }

    public ArrayList<Card> getCardsByDarkMode(boolean darkMode) {
        ArrayList<Card> initCards = getData(),
                finalCards = new ArrayList<>();
        if (darkMode) {
            for (int j = 0; j < initCards.size(); j++) {
                Card initCard = initCards.get(j);
                finalCards.add(new Card(initCard.id, initCard.name, initCard.location, darkPalette.get(0), initCard.sprinkler));
                editCard(initCard, finalCards.get(j));
            }
        } else {
            int k = 0;
            for (int j = 0; j < initCards.size(); j++) {
                Card initCard = initCards.get(j);
                finalCards.add(new Card(initCard.id, initCard.name, initCard.location, lightPalette.get(k++ % 6), initCard.sprinkler));
                editCard(initCard, finalCards.get(j));
            }
        }
        return finalCards;
    }

    public Card toggleStatus(Card card) {
        Card newCard = new Card(card.id,
                card.name,
                card.location,
                card.colors,
                new Sprinkler(
                        card.sprinkler.status == Sprinkler.ONLINE ? Sprinkler.OFFLINE : Sprinkler.ONLINE,
                        card.sprinkler.rate,
                        card.sprinkler.activeDays,
                        card.sprinkler.activeTime,
                        card.sprinkler.auto
                ));
        editCard(card, newCard);
        return newCard;
    }

    public ArrayList<Card> getTimeControlCards(boolean darkMode) {
        ArrayList<Card> cards = getCardsByDarkMode(darkMode),
                finalCards = new ArrayList<>();
        for (Card card : cards)
            if (card.sprinkler.activeTime[0] != null && card.sprinkler.activeTime[1] != null)
                finalCards.add(card);
        return finalCards;
    }
}