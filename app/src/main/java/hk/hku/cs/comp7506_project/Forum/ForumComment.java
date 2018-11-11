package hk.hku.cs.comp7506_project.Forum;

public class ForumComment {
    private String comment;
    private String cUsrname;

    private class CommentReply
    {
        private String reply;
        private String rUsrname;

        CommentReply(String _reply, String _rUsrname)
        {
            reply = _reply;
            rUsrname = _rUsrname;
        }

        public String getReply()
        {
            return reply;
        }

        public String getrUsrname()
        {
            return rUsrname;
        }
    }


    ForumComment(String _comment, String _cUsrname)
    {
        comment = _comment;
        cUsrname = _cUsrname;
    }


    public String getComment()
    {
        return comment;
    }

    public String getUsrname()
    {
        return cUsrname;
    }
}
