package blacksmith.com.sandorln.blacksmith.SQL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import blacksmith.com.sandorln.blacksmith.Dto.cDto;
import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.Dto.nDto;
import blacksmith.com.sandorln.blacksmith.Dto.uDto;

/**
 * Created by SanDorln on 2016-08-16.
 */
public class SQLData_Management {
    SQLiteDatabase sqLiteDatabase;
    String sql;

    public SQLData_Management(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public uDto Select_Userinfo() {
        uDto uDtos = null;
        sql = "select * from userinfo;";
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        while (c.moveToNext()) {
            uDtos = new uDto(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6));
        }
        return uDtos;
    }

    public ArrayList<cDto> Select_charinfo() {
        ArrayList<cDto> cDtos = new ArrayList<>();
        sql = "select * from characterinfo;";
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        while (c.moveToNext()) {
            cDto temp = new cDto(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getString(5));
            //cId	cName	cBuy	cLv	cMoney	cInformation
            Log.d("SelectDB", "cId : " + temp.getcId() + " cName : " + temp.getcName() + " cBuy : " + temp.getcBuy() + " cLv : " + temp.getcLv() + " cMoney : " + temp.getcMoney() + " cInformation : " + temp.getcInformation());
            cDtos.add(temp);
        }
        return cDtos;
    }

    public ArrayList<iDto> Select_iteminfo() {
        ArrayList<iDto> iDtos = new ArrayList<>();
        sql = "select * from iteminfo;";
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        while (c.moveToNext()) {
            iDto temp = new iDto(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c.getInt(9), c.getString(10));
            iDtos.add(temp);
        }
        return iDtos;
    }

    public ArrayList<nDto> Select_npcinfo() {
        ArrayList<nDto> nDtos = new ArrayList<>();
        sql = "select * from npcinfo;";
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        while (c.moveToNext()) {
            nDto temp = new nDto(c.getInt(0), c.getString(1), c.getInt(2));
            nDtos.add(temp);
        }
        return nDtos;
    }

    public void Update_userinfo(uDto userinfo) {
        sql = "update userinfo set uGold = " + userinfo.getuGold() +
                ",uMissionLv =" + userinfo.getuMissionLv() +
                ",uRoom =" + userinfo.getuRoomLv() +
                ",uSmithyLv =" + userinfo.getuSmithyLv() +
                ",uNextMission =" + userinfo.getuNextMissionLv() +
                ",uTouchLv =" + userinfo.getuTouchLv() +
                ",uBuyerLv =" + userinfo.getuBuyerLv() + ";";
        sqLiteDatabase.execSQL(sql);
    }

    public void Update_iteminfo(ArrayList<iDto> iteminfo) {
        for (iDto dto : iteminfo) {
            sql = "update iteminfo set iBuyMoney =" + dto.getiBuyMoney() +
                    ",iLv =" + dto.getiLv() +
                    ",iBuy =" + dto.getiBuy() +
                    " where iId = " + dto.getiId() + ";";
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void Update_characterinfo(ArrayList<cDto> characterinfo) {
        for (cDto dto : characterinfo) {
            sql = "update characterinfo set cBuy = " + dto.getcBuy() + ", cMoney = " + dto.getcMoney() + ", cLv =" + dto.getcLv() +
                    " where cId = " + dto.getcId() + ";";
            sqLiteDatabase.execSQL(sql);
        }
    }

}
