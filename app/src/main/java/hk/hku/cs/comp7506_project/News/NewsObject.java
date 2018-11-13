package hk.hku.cs.comp7506_project.News;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class NewsObject implements Parcelable {
    private String title;
    private String content;
    private Bitmap pic;

    public NewsObject(String _title, String _content, Bitmap _pic){
        title = _title;
        content = _content;
        pic = _pic;
    }

    protected NewsObject(Parcel in) {
        title = in.readString();
        content = in.readString();
//        pic = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<NewsObject> CREATOR = new Creator<NewsObject>() {
        @Override
        public NewsObject createFromParcel(Parcel in) {
            return new NewsObject(in);
        }

        @Override
        public NewsObject[] newArray(int size) {
            return new NewsObject[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getPic() {
        return pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
//        dest.writeParcelable(pic, flags);
    }
}
