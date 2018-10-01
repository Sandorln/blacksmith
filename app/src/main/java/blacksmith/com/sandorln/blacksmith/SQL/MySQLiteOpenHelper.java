package blacksmith.com.sandorln.blacksmith.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import blacksmith.com.sandorln.blacksmith.Dto.cDto;
import blacksmith.com.sandorln.blacksmith.Dto.iDto;
import blacksmith.com.sandorln.blacksmith.Dto.nDto;
import blacksmith.com.sandorln.blacksmith.Dto.uDto;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by SanDorln on 2016-08-16.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    String sql;
    SQLiteDatabase db_sql;
    Workbook openWorkbook;

    // EXCEL 시트 정의
    private final int EXCEL_USERINFO = 0, EXCEL_CHARACTERINFO = 1, EXCEL_ITEMINFO = 2, EXCEL_NPC = 3;

    private Context context;
    uDto uDtos;                      // 사용자 정보 담는 DTO
    ArrayList<cDto> cDtos;           // 캐릭터 정보 담는 DTO
    ArrayList<iDto> iDtos;           // 아이템 정보 담는 DTO
    ArrayList<nDto> nDtos;           // NPC 정보 담는 DTO

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초의 데이터 베이스가 없을 경우
        // 테이블을 생성하는 코드 작성
        db_sql = db;
        ExcelData_Get();            // 엑셀에서 값 가져오기
        NewCreate();                // 가져온 값으로 SQL에 저장
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void NewCreate() {
        // 모든 테이블 초기화
        Userinfo_Create();
        Characterinfo_Create();
        Iteminfo_Create();
        Npcinfo_Create();
    }

    private void Userinfo_Create() {
        // USERINFO 테이블 생성
        sql = "create table userinfo(uGold int, uMissionLv int, uRoom int, uSmithyLv int," +
                "uNextMission int, uTouchLv int, uBuyerLv int);";
        db_sql.execSQL(sql);
        // 테이블에 초기 값 저장
        sql = "insert into userinfo(uGold,uMissionLv,uRoom,uSmithyLv,uNextMission,uTouchLv,uBuyerLv)" +
                " values(" +
                uDtos.getuGold() + "," +
                uDtos.getuMissionLv() + "," +
                uDtos.getuRoomLv() + "," +
                uDtos.getuSmithyLv() + "," +
                uDtos.getuNextMissionLv() + "," +
                uDtos.getuTouchLv() + "," +
                uDtos.getuBuyerLv() +
                ");";
        //Log.d("GameDataLOAD", sql);
        db_sql.execSQL(sql);
    }

    private void Characterinfo_Create() {
        // CHARACTERINFO 테이블 생성
        sql = "create table characterinfo(cId int,cName text,cBuy int,cLv int,cMoney int,cInformation text);";
        db_sql.execSQL(sql);

        // 테이블에 초기 값 저장
        for (cDto values : cDtos) {
            sql = "insert into characterinfo(cId,cName,cBuy,cLv,cMoney,cInformation) values(" +
                    values.getcId() + "," +
                    "'" + values.getcName() + "'," +
                    values.getcBuy() + "," +
                    values.getcLv() + "," +
                    values.getcMoney() + "," +
                    "'" + values.getcInformation() + "');";
            db_sql.execSQL(sql);
        }
    }

    private void Iteminfo_Create() {
        // ITEMINFO 테이블 생성
        sql = "create table iteminfo(iId int, iName text, iSLv int," +
                "iSellMoney int, iMadeMoney int, iHp int,iLv int, iHp_Sub int, iBuy int, iBuyMoney int, iInformation text);";
        db_sql.execSQL(sql);

        for (iDto value : iDtos) {
            sql = "insert into iteminfo(iId,iName,iSLv,iSellMoney,iMadeMoney,iHp,iLv,iHp_Sub,iBuy,iBuyMoney,iInformation) values(" +
                    value.getiId() + "," +
                    "'" + value.getiName() + "'," +
                    value.getiSLv() + "," +
                    value.getiSellMoney() + "," +
                    value.getiMadeMoney() + "," +
                    value.getiHp() + "," +
                    value.getiLv() + "," +
                    value.getiHp_Sub() + "," +
                    value.getiBuy() + "," +
                    value.getiBuyMoney() + "," +
                    "'" + value.getiInformation() + "');";
            Log.d("INSERTDATA", sql);
            db_sql.execSQL(sql);
        }
    }

    private void Npcinfo_Create() {
        // ROOMINFO 테이블 생성
        sql = "create table npcinfo(nId int, nTalk text, nType int);";
        db_sql.execSQL(sql);

        for (nDto value : nDtos) {
            sql = "insert into npcinfo(nId,nTalk,nType) values(" +
                    value.getnId() + "," +
                    "'" + value.getnTalk() + "'," +
                    value.getnType() + ");";
            Log.d("INSERTDATA", sql);
            db_sql.execSQL(sql);
        }
    }


    // 엑셀 관련
    private void ExcelData_Get() { // 엑셀 데이터 값 가져오기
        iDtos = new ArrayList<>();
        cDtos = new ArrayList<>();
        nDtos = new ArrayList<>();

        try {
            // 엑셀 파일 openWorkbook에 넣기
            InputStream is = context.getResources().getAssets().open("data.xls");
            openWorkbook = Workbook.getWorkbook(is);

            // 엑셀에 있는 값 DTO에 담기
            Sheet_GetData();
            Log.d("GameDataLOAD", "EXCEL FILE LOAD : 성공");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Sheet_GetData() {

        for (int sheetNum = 0; sheetNum < openWorkbook.getSheets().length; sheetNum++) {

            Sheet value = openWorkbook.getSheet(sheetNum);
            int max_row = value.getRows();
            Log.d("EXCELSANDORLN", "sheetNum : " + sheetNum);
            Log.d("EXCELSANDORLN", "Length : " + openWorkbook.getSheets().length);
            Log.d("EXCELSANDORLN", "max_row : " + value.getRows());
            Log.d("EXCELSANDORLN", "max_columns : " + value.getColumns());

            switch (sheetNum) {
                case EXCEL_USERINFO:
                    // 유저 정보 담기
                    int uGold = Integer.parseInt(value.getCell(0, 1).getContents());
                    int uMissionLv = Integer.parseInt(value.getCell(1, 1).getContents());
                    int uRoomLv = Integer.parseInt(value.getCell(2, 1).getContents());
                    int uSmithyLv = Integer.parseInt(value.getCell(3, 1).getContents());
                    int uNextMissionLv = Integer.parseInt(value.getCell(4, 1).getContents());
                    int uTouchLv = Integer.parseInt(value.getCell(5, 1).getContents());
                    int uAuto = Integer.parseInt(value.getCell(6, 1).getContents());
                    uDtos = new uDto(uGold, uMissionLv, uRoomLv, uSmithyLv, uNextMissionLv, uTouchLv, uAuto);
                    break;

                case EXCEL_CHARACTERINFO:
                    //캐릭터 정보 담기
                    for (int row = 1; row < max_row; row++) {
                        int cId = Integer.parseInt(value.getCell(0, row).getContents());
                        String cName = value.getCell(1, row).getContents();
                        int cBuy = Integer.parseInt(value.getCell(2, row).getContents());
                        int cLv = Integer.parseInt(value.getCell(3, row).getContents());
                        int cMoney = (int) Float.parseFloat(value.getCell(4, row).getContents());
                        String cInformation = value.getCell(5, row).getContents();
                        cDto temp = new cDto(cId, cName, cBuy, cLv, cMoney, cInformation);
                        cDtos.add(temp);
                    }
                    break;

                case EXCEL_ITEMINFO:
                    //아이템 정보 담기
                    for (int row = 1; row < max_row; row++) {
                        int iId = Integer.parseInt(value.getCell(0, row).getContents());
                        String iName = value.getCell(1, row).getContents();
                        int iSLv = Integer.parseInt(value.getCell(2, row).getContents());
                        int iSellMoney = Integer.parseInt(value.getCell(3, row).getContents());
                        int iMadeMoney = Integer.parseInt(value.getCell(4, row).getContents());
                        int iTime = (int) Float.parseFloat(value.getCell(5, row).getContents());
                        int iLv = (int) Float.parseFloat(value.getCell(6, row).getContents());
                        int iMissionLv = Integer.parseInt(value.getCell(7, row).getContents());
                        int iBuy = Integer.parseInt(value.getCell(8, row).getContents());
                        int iBuyMoney = Integer.parseInt(value.getCell(9, row).getContents());
                        String iInformation = value.getCell(10, row).getContents();
                        iDto temp = new iDto(iId, iName, iSLv, iSellMoney, iMadeMoney, iTime, iLv, iMissionLv, iBuy, iBuyMoney, iInformation);
                        iDtos.add(temp);
                    }
                    break;
                case EXCEL_NPC:
                    for (int row = 1; row < max_row; row++) {
                        int nId = Integer.parseInt(value.getCell(0, row).getContents());
                        String nTalk = value.getCell(1, row).getContents();
                        int nType = Integer.parseInt(value.getCell(2, row).getContents());
                        nDto temp = new nDto(nId, nTalk, nType);
                        nDtos.add(temp);
                    }
                    break;
            }
        }
    }
}