package ir.sass.sampleprojects.retrofit;

import com.google.gson.annotations.SerializedName;

public class Model
{
    @SerializedName("title")
    public String title;

    @SerializedName("url")
    public String url;

    public Model(String title , String url)
    {
        this.title = title;
        this.url = url;
    }
}
