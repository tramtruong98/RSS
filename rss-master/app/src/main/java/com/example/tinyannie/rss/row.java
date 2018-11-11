package com.example.tinyannie.rss;

import android.widget.TextView;

public class row {
    private TextView title, image,link;

    public row(TextView title, TextView image, TextView link) {
        this.title = title;
        this.image = image;
        this.link = link;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getImage() {
        return image;
    }

    public void setImage(TextView image) {
        this.image = image;
    }

    public TextView getLink() {
        return link;
    }

    public void setLink(TextView link) {
        this.link = link;
    }
}
