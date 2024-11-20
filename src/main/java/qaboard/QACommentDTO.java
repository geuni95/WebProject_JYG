package qaboard;

import java.sql.Date;

public class QACommentDTO {

    private int idx;               // 댓글 고유 ID (Primary Key)
    private int boardIdx;          // 게시글 ID (qaboard 테이블의 외래키)
    private String id;             // 댓글 작성자 ID
    private String content;        // 댓글 내용
    private Date postdate;         // 댓글 작성 일시
    private Integer parentIdx;     // 부모 댓글 ID (답글인 경우, 없으면 null)
    private String isDeleted;      // 댓글 삭제 여부 ('Y' 또는 'N')
    private int likes;             // 댓글 좋아요 수
    
    
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPostdate() {
		return postdate;
	}
	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}
	public Integer getParentIdx() {
		return parentIdx;
	}
	public void setParentIdx(Integer parentIdx) {
		this.parentIdx = parentIdx;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
    
}
