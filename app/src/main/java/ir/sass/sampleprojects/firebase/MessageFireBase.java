package ir.sass.sampleprojects.firebase;

public class MessageFireBase
{
    public String title , message , id;
    public MessageFireBase(String id ,String title , String message)
    {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "the message : id -> "+id+" title -> "+title+" message -> "+message;
    }
}
