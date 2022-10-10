package com.cemalettin.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.MediaTracker;
import java.util.BitSet;
import java.util.Random;
import java.util.Timer;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdx = 0;
	float birdy = 0;
	int gameState = 0;//game started or not
	float velocity = 0;//it means speed
	float gravity = 0.5f;
	float enemyVelocity = 2.8f;//speed of bees
	Random random;

	Circle birdCircle;

	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float [] enemyx = new float[numberOfEnemies];//its bee
	float[] enemyOffSet = new float[numberOfEnemies];//for y axis
	float[] enemyOffSet2 = new float[numberOfEnemies];//for y axis
	float[] enemyOffSet3 = new float[numberOfEnemies];//for y axis
	float distance = 0;
	int score = 0;
	int scoredEnemy = 0;

	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	@Override
	public void create () {//its same with oncreate
		//create equals when the game open what will happen

		batch = new SpriteBatch();

		background = new Texture("bg.png");

		bird = new Texture("b1.png");
		bee1 = new Texture("e2.png");
		bee2 = new Texture("e2.png");
		bee3 = new Texture("e3.png");

		distance =Gdx.graphics.getWidth()/2;
		random = new Random();

		birdx = Gdx.graphics.getWidth()/2 - bird.getHeight()/2;
		birdy = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(6);//size

		font2 = new BitmapFont();
		font2.setColor(Color.RED);
		font2.getData().setScale(6);//size

		font3 = new BitmapFont();
		font3.setColor(Color.PURPLE);
		font3.getData().setScale(6);

		for (int i=0;i<numberOfEnemies;i++){
			enemyx[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-150);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-150);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-150);

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

	}

	@Override
	public void render () {
		//render equals while games continue what will happen
		//and it renews itself

		batch.begin();//game starts here
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		//for touching start place
		if(gameState == 1){
			if(enemyx[scoredEnemy] < Gdx.graphics.getWidth()/2 - bird.getHeight()/2){
				score++;
				if(scoredEnemy<50){
					enemyVelocity = enemyVelocity + 0.035f;
				}else if(enemyVelocity>49 && enemyVelocity <100){
					enemyVelocity = enemyVelocity + 0.030f;
				}else{
					enemyVelocity = enemyVelocity + 0.025f;
				}

				if(scoredEnemy < 3){
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){

				velocity = -7;
			}

			for(int i=0;i<numberOfEnemies;i++){//this loop about moving of the bees

				if(enemyx[i] < -20){
					enemyx[i] = enemyx[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());

				}else{
					enemyx[i] = enemyx[i] - enemyVelocity;
				}

				enemyx[i] = enemyx[i] - enemyVelocity;
				batch.draw(bee1,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(bee2,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(bee3,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

				enemyCircles[i] = new Circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/42);
				enemyCircles2[i] = new Circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/42);
				enemyCircles3[i] = new Circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/42);

			}

			if(birdy > 0 && birdy < Gdx.graphics.getHeight()-35){
				velocity = velocity + gravity;
				birdy = birdy - velocity;
			}else{
				gameState = 2;
				enemyVelocity = 2.8f;
			}
		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){

			font2.draw(batch,"Game Over!!!",900,750);
			font3.draw(batch,"Scored:"+score,900,650);

			if(Gdx.input.justTouched()){
				if(Gdx.input.justTouched()){

					enemyVelocity = 2.8f;
					gameState = 1;

					birdy = Gdx.graphics.getHeight()/2;

					for (int i=0;i<numberOfEnemies;i++){
						enemyx[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());
						enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());
						enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight());

						enemyCircles[i] = new Circle();
						enemyCircles2[i] = new Circle();
						enemyCircles3[i] = new Circle();

					}
					velocity = 0;
					scoredEnemy = 0;
					score = 0;
				}
			}
		}
		//for touching finish place

		batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15/*size of bird*/,Gdx.graphics.getHeight()/10/*size of bird*/);

		font.draw(batch,String.valueOf(enemyVelocity),300,250);
		font.draw(batch,String.valueOf(score),100,250);

		birdCircle.set(birdx+65,birdy+70,Gdx.graphics.getWidth()/42);

		//font.draw(batch,String.valueOf("GAME OVER!!!\nScore:"+score),1000,700);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for(int i=0;i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
			//shapeRenderer.circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);
			//shapeRenderer.circle(enemyx[i] + Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/35);

			if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])){//checking for collision
				gameState = 2;
			}

		}
		//shapeRenderer.end();
		batch.end();//game ends here

	}
	
	@Override
	public void dispose () {

	}
}
