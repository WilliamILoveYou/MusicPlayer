package com.ckt.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.ckt.modle.Mp3Info;

/**
 * mp3�ļ��Ĺ�����,������ȡ����ͼƬ,������Ϣ,��ȡ�ļ���С�ȷ���
 *
 * @author JonsonMarxy
 */
public class Mp3FileUtil {
    //��ȡ����ͼƬʱ��uri
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    /**
     * ��ȡ�ļ���С
     *
     * @param f ָ�����ļ�·��
     * @return
     */
    public static double getFileSize(File f) {
        // �����ļ���С
        double fl = (double) f.length() / 1024 / 1024;
        // ��ʽ��fl,ֻ������λС��
        fl = new BigDecimal(fl).setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return fl;
    }

    //��long�͵�ʱ��,��ʽ��Ϊxx:xx��ʽ���ַ���
    public static String getDuringString(long during) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
            Date date = new Date(during);
            return dateFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }


    //��contentProvider�����ȡ�����б�---���鿪���̶߳�ȡ
    public static ArrayList<Mp3Info> getMp3InfoList(Context context) {
        ArrayList<Mp3Info> list = new ArrayList<Mp3Info>();

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        try {
            while (true) {
                int isMusic = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // �Ƿ�Ϊ����
                if (isMusic != 0) { //�������ļ�
                    String title = cursor.getString((cursor
                            .getColumnIndex(MediaStore.Audio.Media.TITLE))); // ���ֱ���
                    String artist = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST)); // ������
                    String album = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM)); // ר��
                    long duration = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DURATION)); // ʱ��
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
                    long song_id = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));  //����ID
                    long album_id = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));  //ר��ID
                    double fileSize = getFileSize(new File(path));

                    //�ļ�����
                    if (duration > 40 * 1000 || duration == 0) {
                        Mp3Info mp3Info = new Mp3Info(song_id, album_id, album, title, artist, fileSize,
                                duration, path);
                        list.add(mp3Info);
                    }
                }
                if (cursor.isLast()) break;
                cursor.moveToNext();
            }
        } catch (Exception e) {

        }
        return list;
    }


    //��ȡָ������ר��-->����ID������ͼƬ
    public static Bitmap getMusicBitpMap(Context context, long songid, long albumid) {
        Bitmap bm = null;
        if (albumid < 0 && songid < 0) {
            return null;
        }
        try {
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {

                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver()
                        .openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (FileNotFoundException ex) {
        }
        return bm;
    }
}
