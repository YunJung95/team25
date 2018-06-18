package com.myin.team25.service;

import java.util.ArrayList;
import com.myin.team25.domain.CommentVo;

public interface CommentService {
	
	public ArrayList<CommentVo> SelectCommentAll(int bbidx);
	
	public ArrayList<CommentVo> getCommentMore(int bbidx, int block, int perBlockNum);
	
	public int insertComment(CommentVo cv);

	public int deleteComment(CommentVo cv);
}
