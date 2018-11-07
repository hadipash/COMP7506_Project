package hk.hku.cs.comp7506_project.News;

import android.graphics.Bitmap;

public class NewsObject {
    private String title;
    private String content;
    private Bitmap pic;

    NewsObject(String _title, String _content, Bitmap _pic){
        title = _title;
        content = _content;
        pic = _pic;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getPic() {
        return pic;
    }
}
