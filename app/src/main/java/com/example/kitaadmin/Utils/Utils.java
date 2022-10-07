package com.example.kitaadmin.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Utils {

    //Convierte de bitmap a byte array
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //Convierte de byte array a bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //Da formato a la fecha
    public static String getDate(String fecha) throws ParseException {

        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

        Date date = inputFormat.parse(fecha);

        return outputFormat.format(date);
    }

    //Formatea la duración de sueño dada quitando los segundos
    public static String formatTimeDuration(String time){
        String originalStringFormat = "HH:mm:ss";
        String desiredStringFormat = "HH:mm";

        SimpleDateFormat readingFormat = new SimpleDateFormat(originalStringFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(desiredStringFormat);
        Date dated = null;
        try {
            dated = readingFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        return outputFormat.format(dated);
    }

    //Convierte de String a Date
    public static LocalDate giveDate(String fecha) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    //Añade segundos para almacenar en la bd correctamente el tiempo
    public static String stringToTime(String time){
        return time.concat(":00");
    }


}
