package com.tino.base.util;

import android.media.MediaPlayer;

public class PlayerUtil {
	
	public static PlayerUtil instance;
	private MediaPlayer player;
	
	public static synchronized PlayerUtil getInstance(){
		if(instance==null)
			instance=new PlayerUtil();
		return instance;
	}
	
	public void setCompletionListener(MediaPlayer.OnCompletionListener listener) {
		if(player==null) {
			player=new MediaPlayer();
		}
		player.setOnCompletionListener(listener);
	}

	public void setPreparedListener(MediaPlayer.OnPreparedListener listener) {
		if(player==null) {
			player=new MediaPlayer();
		}
		player.setOnPreparedListener(listener);
	}

	public void setLoadListener(MediaPlayer.OnBufferingUpdateListener listener) {
		if(player==null) {
			player=new MediaPlayer();
		}
		player.setOnBufferingUpdateListener(listener);
	}

	public void Load(String url)
	{
		if(player==null)
			player=new MediaPlayer();
		
		try {
			player.reset();
			player.setDataSource(url);
			player.prepareAsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Play() {
		if(player!=null) {
			player.start();
		}
	}
	
	public void Pause() {
		if(player!=null&&player.isPlaying()){
			player.pause();
		}
	}

	public void Stop() {
		if(player!=null) {
			if(player.isPlaying()) {
				player.stop();
			}
			player.release();
			player=null;
		}

	}

	public int getProgress(){
		int p = 0;
		try{
			if(player!=null){
				p = player.getCurrentPosition();
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			return p;
		}
	}

	public int getMax(){
		int max = 0;
		try {
			if(player!=null){
				max = player.getDuration();
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			return max;
		}
	}

	public void seek(int position){
		if(player!=null){
			player.seekTo(position*getMax()/100);
		}
	}
}
