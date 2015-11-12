package com.ckt.modle;

import android.content.Context;
import android.graphics.Bitmap;

import com.ckt.utils.Mp3FileUtil;

/**��¼Mp3��Ϣ��modle��
 * @author JonsonMarxy
 *
 */
public class Mp3Info {
    private String album_art; //ר��
    private String name;  //������
    private String artistName; //������
    private double size; //�ļ���С
    private long during; //ʱ��
    private String path;//·��
    private long song_id;  //����ID
    private long album_id; //ר��ID
    private Bitmap bitmap; //ר��ͼƬ-->�еĸ�û��
   

    public Mp3Info() {
        path = "";
        album_art = "";
        artistName = "";
        name = "";
        size = during = song_id = album_id = 0;
    }

    public Mp3Info(long song_id, long album_id, String album_art, String name, String artistName,
                   double size, long during, String path) {
        this();
        this.song_id = song_id;
        this.album_id = album_id;
        this.album_art = album_art;
        this.name = name;
        this.artistName = artistName;
        this.size = size;
        this.during = during;
        this.path = path;
    }

    public Mp3Info(long song_id, long album_id, String album_art, String name, String artistName,
                   double size, long during, String path, Bitmap bitmap) {
        this();
        this.song_id = song_id;
        this.album_id = album_id;
        this.album_art = album_art;
        this.name = name;
        this.artistName = artistName;
        this.size = size;
        this.during = during;
        this.path = path;
        this.bitmap = bitmap;
    }

    //���ظ���ͼƬ
    public Bitmap getBitmap(Context context) {
        if(bitmap == null) {
            this.bitmap = Mp3FileUtil.getMusicBitpMap(context, song_id, album_id);
        }
        return bitmap;
    }


    public long getDuring() {
        return during;
    }

    public void setDuring(long during) {
        this.during = during;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public long getSong_id() {
        return song_id;
    }

    public void setSong_id(long song_id) {
        this.song_id = song_id;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
