package com.example.pantallainicio.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Transaccion {

    ConexionSQLiteHelper conn;

    public Transaccion(Context context){
        conn=new ConexionSQLiteHelper(context,"bd_usuarios", null, 1);
    }

    public void registrarPuntuacion(Context context, String CampoNickname, String MapuntajeJ1, String MapuntajeJ2, String MapuntajeJ3, String MapuntajeJ4) {

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.Campo_Nickname,CampoNickname);
        values.put(Utilidades.MaPuntajeJ1,MapuntajeJ1);
        values.put(Utilidades.MaPuntajeJ2,MapuntajeJ2);
        values.put(Utilidades.MaPuntajeJ3,MapuntajeJ3);
        values.put(Utilidades.MaPuntajeJ4,MapuntajeJ4);

        try{
            Long idResultante=db.insert(Utilidades.TABLA_JUGADOR,Utilidades.Campo_Nickname,values);
            Toast.makeText(context,"Puntaje registrado de "+CampoNickname+". Registro número: "+idResultante,Toast.LENGTH_SHORT).show();
            db.close();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public String[] consultar(Context context, String nickname){

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={nickname};
        String[] campos={Utilidades.MaPuntajeJ1,Utilidades.MaPuntajeJ2,Utilidades.MaPuntajeJ3,Utilidades.MaPuntajeJ4};

        try{
            Cursor cursor=db.query(Utilidades.TABLA_JUGADOR, campos,Utilidades.Campo_Nickname+"=?", parametros,null,null,null);
            cursor.moveToFirst();
            return new String[]{cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)};
        }catch (Exception e){
            Toast.makeText(context, "Nickname sin registros",Toast.LENGTH_SHORT).show();
        }

        return new String[]{"","","","",""};
    }

}
