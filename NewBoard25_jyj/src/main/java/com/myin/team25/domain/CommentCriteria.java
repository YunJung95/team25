package com.myin.team25.domain;

public class CommentCriteria {
	
	private int block;
	private int perBlockNum;
	
	
	public CommentCriteria(){
		this.block = 1;
		this.perBlockNum = 10; //ÃÑ ³ª¿À´Â °¹¼ö
	}
	
	
	public int getBlock() {
		return block;
	}
	public void setBlock(int block) {
		if(block <=1){
			this.block = 1;
			return;
		}
		this.block = block;
	}
	
	
	
	public int getPerBlockNum() {
			return perBlockNum;
	}
	
	public void setPerBlockNum(int perBlockNum) {
		if(perBlockNum <=0 || perBlockNum > 100){ 		
			this.perBlockNum = 10;
			return;
		}
		
		this.perBlockNum = perBlockNum;
	}
	
/*	public int getNextBlock() {
		int block = 0;
		block = this.block+1;
		return block;
	}
	
	*/
	@Override
	public String toString() {
		return "CommentCriteria [block=" + block + ", perBlockNum=" + perBlockNum + "]";
	}
}