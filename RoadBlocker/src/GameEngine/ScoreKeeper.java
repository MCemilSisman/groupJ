package GameEngine;
import java.time.*;

public class ScoreKeeper {
	
	private int Score;
	private LocalDateTime time;
	
	public ScoreKeeper() {
		super();
		this.Score = 0;
		this.time = LocalDateTime.now();
	}
	
	public ScoreKeeper(int Score, LocalDateTime time) {
		super();
		this.Score = Score;
		this.time = time;
	}
	
	// init score
	public void returnCurrentTime() {
		this.time = LocalDateTime.now();
	}
	


	public int getScore() {
		return Score;
	}


	public void setScore(int Score) {
		this.Score = Score;
	}


	public LocalDateTime getTime() {
		return time;
	}


	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
	

}
