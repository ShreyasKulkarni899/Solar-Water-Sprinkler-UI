package com.nkocet.untitled;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Devices",
            COL_0 = "ID",
            COL_1 = "Name",
            COL_2 = "Location",
            COL_3 = "Rate",
            COL_4 = "Working_days",
            COL_5 = "Auto",
            COL_6 = "Body_Color",
            COL_7 = "Bottom_Color",
            COL_8 = "Text_Color";

    static ArrayList<String[]> lightPalette = new ArrayList<>();

    static ArrayList<String[]> darkPalette = new ArrayList<>();

    static int i = 0;

    ArrayList<Card> cards;

    SharedPreferences preferences;

    public Database(Context context) {
        super(context, "database.db", null, 1);
        this.preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        lightPalette.add(new String[]{"#FCF0F0", "#F8A9A9", "#FFFFFF"});
        lightPalette.add(new String[]{"#EFFFF9", "#95FFD6", "#0A0057"});
        lightPalette.add(new String[]{"#FFF0F7", "#FF98C8", "#FFFFFF"});
        lightPalette.add(new String[]{"#EAF4FF", "#8BC1FF", "#FFFFFF"});
        lightPalette.add(new String[]{"#CBE9FF", "#2A6B9B", "#FFFFFF"});
        lightPalette.add(new String[]{"#EAFFF8", "#3D715F", "#FFFFFF"});

        darkPalette.add(new String[]{"#404040", "#3B3B3B", "#FFFFFF"});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8);
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cmd = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(cmd);
        onCreate(db);
    }

    public void insertData(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG", "insertData: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, card.name);
        contentValues.put(COL_2, card.location);
        contentValues.put(COL_3, card.sprinkler.rate);

        StringBuilder activeDays = new StringBuilder();
        for (int i = 0; i < card.sprinkler.activeDays.length; i++)
            activeDays.append(card.sprinkler.activeDays[i] == 1 ? "1" : "0");
        contentValues.put(COL_4, activeDays.toString());
        contentValues.put(COL_5, String.valueOf(card.sprinkler.auto));

        boolean darkMode = preferences.getBoolean("darkMode", false);
        if (darkMode) {
            contentValues.put(COL_6, darkPalette.get(i)[0]);
            contentValues.put(COL_7, darkPalette.get(i)[1]);
            contentValues.put(COL_8, darkPalette.get(i)[2]);
        } else {
            contentValues.put(COL_6, lightPalette.get(i)[0]);
            contentValues.put(COL_7, lightPalette.get(i)[1]);
            contentValues.put(COL_8, lightPalette.get(i++ % 6)[2]);
        }
        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Card> getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Card> cards = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String location = cursor.getString(2);
            String rate = cursor.getString(3);

            char[] workingDays = cursor.getString(4).toCharArray();
            int[] active_days = new int[7];
            for (int i = 0; i < workingDays.length; i++)
                active_days[i] = workingDays[i] == '1' ? 1 : 0;

            boolean auto = cursor.getString(5).equals("true");
            String cardBackground = cursor.getString(6),
                    cardBottom = cursor.getString(7),
                    text = cursor.getString(8);

            Sprinkler sprinkler = new Sprinkler(1, Integer.parseInt(rate), active_days, auto);
            Card card = new Card(id, name, location, new String[]{cardBackground, cardBottom, text}, sprinkler);
            cards.add(card);
        }
        cursor.close();
        return cards;
    }

    public String getLastId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        return String.valueOf(cursor.getCount());
    }

    public void edit(Card initCard, Card finalCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, finalCard.name);
        contentValues.put(COL_2, finalCard.location);
        contentValues.put(COL_3, finalCard.sprinkler.rate);

        StringBuilder activeDays = new StringBuilder();
        for (int i = 0; i < finalCard.sprinkler.activeDays.length; i++)
            activeDays.append(finalCard.sprinkler.activeDays[i] == 1 ? "1" : "0");
        contentValues.put(COL_4, activeDays.toString());
        contentValues.put(COL_5, String.valueOf(finalCard.sprinkler.auto));
        contentValues.put(COL_6, finalCard.cardBackgroundColor);
        contentValues.put(COL_7, finalCard.cardBottomColor);
        contentValues.put(COL_8, finalCard.textColor);

        String[] whereArgs = {initCard.id};
        db.update(TABLE_NAME, contentValues, COL_0 + "=?", whereArgs);
    }

    public void delete(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = card.id;
        String[] whereArgs = {id};
        db.delete(TABLE_NAME, COL_0 + "=?", whereArgs);
    }

    public ArrayList<Card> toggleDarkMode(boolean darkMode) {
        ArrayList<Card> initCards = getData(),
                finalCards = new ArrayList<>();
        if (darkMode) {
            for (int j = 0; j < initCards.size(); j++) {
                Card initCard = initCards.get(j);
                finalCards.add(new Card(initCard.id, initCard.name, initCard.location, darkPalette.get(0), initCard.sprinkler));
                edit(initCard, finalCards.get(j));
            }
        } else {
            int k = 0;
            for (int j = 0; j < initCards.size(); j++) {
                Card initCard = initCards.get(j);
                finalCards.add(new Card(initCard.id, initCard.name, initCard.location, lightPalette.get(k++ % 6), initCard.sprinkler));
                edit(initCard, finalCards.get(j));
            }
        }
        return finalCards;
    }

    public Card get(Card card) {
        for (Card c : getData()) if (c == card) return c;
        return null;
    }

    public ArrayList<Card> toggleStatus(int position) {
        cards = getData();
        Card initCard = cards.get(position);
        cards.remove(position);
        cards.add(position, new Card(
                initCard.id,
                initCard.name,
                initCard.location,
                new String[]{initCard.cardBackgroundColor, initCard.cardBottomColor, initCard.textColor},
                new Sprinkler(
                        initCard.sprinkler.status == 0 ? 1 : 0,
                        initCard.sprinkler.rate,
                        initCard.sprinkler.activeDays,
                        initCard.sprinkler.auto
                )
        ));
        edit(initCard, cards.get(position));
        return this.cards;
    }
}