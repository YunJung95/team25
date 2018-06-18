package com.myin.team25.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import com.myin.team25.domain.CommentVo;

public interface CommentService_Mapper {
	
	public ArrayList<CommentVo> SelectCommentAll(int bbidx);
	
	public ArrayList<CommentVo> getCommentMore(int bbidx, int block,int perBlockNum);
	
	public int insertComment(CommentVo cv);

	public int deleteComment(CommentVo cv);
}
