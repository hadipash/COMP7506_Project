package hk.hku.cs.comp7506_project.Forum;

public class ForumPost {
    private String postTitle;
    private String postContent;


    ForumPost(String _postTitle, String _postContent)
    {
        postTitle = _postTitle;
        postContent = _postContent;
    }

    public String getPostTitle()
    {
        return postTitle;
    }

    public String getPostContent()
    {
        return postContent;
    }

}
