package com.joey.soldier_wars;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import sun.audio.ContinuousAudioDataStream;
import sun.awt.image.BufferedImageGraphicsConfig;
import sun.java2d.pipe.LoopBasedPipe;

public class backup extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener {
	Graphics bufferGraphics;
	Image offscreen;

	public tank tank;

	public Image tankPic;
	public Image enemyPic;
	public Image wallPic;
	public Image parachutePic;
	public Image cannonPic;
	public Image jetLPic;
	public Image jetRPic;
	public Image bombPic;
	public Image fireballPic;
	public Image explosionPic;
	public Image fireworksPic;
	public Image boomhithitPic;
	public Image healthDropPic;
	public Image ammoDropPic;
	public Image ammoOpenPic;
	public Image bossFrontPic;
	public Image bossLSidePic;
	public Image bossRSidePic;
	public Image bulletPic;
	public Image jetfireballPic;

	public AudioClip cannonSound;
	public AudioClip emptySound;
	public AudioClip explosionSound;
	public AudioClip gatlingSound;
	public AudioClip locknloadSound;
	public AudioClip healthSound;
	public AudioClip bosshitSound;
	public AudioClip jethitSound;
	public AudioClip bossexplodeSound;
	public AudioClip jetbg;
	public AudioClip helicopterbg;
	public AudioClip soundtrack;

	public wall wall;
	public wall wallRight;
	public wall floor;
	public wall parachuteZone;

	public Enemy eArray[];
	public projectile cannon[];
	public bomb airstrike[];
	public bomb nullbomber[];
	public plane jet[];
	public SupplyDrop healthDrop[];
	public SupplyDrop ammoDrop[];
	public boss boss;
	public bullet bullet[];

	public int fireCount = 0;
	public int levelCount = 6;
	public int aliveCount;
	public int jetCount;
	public int randomJet;
	public int random;
	public int bombCount = 0;
	public int deathCount = 0;
	public int healthCount = 0;
	public int ammoCount = 0;
	public int bossShot = 0;
	public int boss2Shot = 0;
	public int bossTime = 100;
	public int textCount = 50;
	public int ammoTime = 50;
	public boolean ammoStatus = false;
	public boolean bossAlive = false;
	public boolean regBossShot = false;
	public int regBossShotTime = 100;

	public boolean gameOver = false;
	public boolean gameStart = false;
	public boolean move = true;
	public boolean tutorial = false;
	public boolean realgame = false;
	public boolean sound = false;
	public int soundCount = 20;

	public button tutorialbutton;
	public Image tutorialPic;
	public button realgamebutton;
	public Image realgamePic;

	Thread thread;

	public void init() {
		setSize(1000, 1000);
		offscreen = createImage(1000, 1000);
		bufferGraphics = offscreen.getGraphics();

		wall = new wall(0, 0, 100, 1000);
		wallRight = new wall(900, 0, 1000, 1000);
		floor = new wall(-100, 700, 1000, 1000);
		wallPic = getImage(getDocumentBase(), "wall.jpg");

		parachuteZone = new wall(0, floor.ypos - 400, 1000, 1000);
		parachutePic = getImage(getDocumentBase(), "parachute.png");

		tankPic = getImage(getDocumentBase(), "tank.png");
		tank = new tank(450, floor.ypos - 100, 100, 100, 5);
		bossFrontPic = getImage(getDocumentBase(), "bossFront.png");
		bossLSidePic = getImage(getDocumentBase(), "bossLSide.png");
		bossRSidePic = getImage(getDocumentBase(), "bossRSide.png");
		jetLPic = getImage(getDocumentBase(), "jetL.png");
		jetRPic = getImage(getDocumentBase(), "jetR.png");
		explosionPic = getImage(getDocumentBase(), "explosion.png");
		fireworksPic = getImage(getDocumentBase(), "fireworks.png");

		enemyPic = getImage(getDocumentBase(), "soldier.png");
		cannonPic = getImage(getDocumentBase(), "projectile.png");
		bombPic = getImage(getDocumentBase(), "bomb.png");
		fireballPic = getImage(getDocumentBase(), "fireball.png");
		boomhithitPic = getImage(getDocumentBase(), "boomhithit.png");
		bulletPic = getImage(getDocumentBase(), "bullet.png");
		healthDropPic = getImage(getDocumentBase(), "healthdrop.png");
		ammoDropPic = getImage(getDocumentBase(), "ammodrop.png");
		ammoOpenPic = getImage(getDocumentBase(), "ammoopen.png");
		jetfireballPic = getImage(getDocumentBase(), "jetfireball.png");

		tutorialPic = getImage(getDocumentBase(), "tutorialbutton.png");
		tutorialbutton = new button(450, 500, 150, 50);
		realgamePic = getImage(getDocumentBase(), "realdeal.png");
		realgamebutton = new button(450, 700, 150, 50);

		cannonSound = getAudioClip(getDocumentBase(), "cannonsound.wav");
		emptySound = getAudioClip(getDocumentBase(), "emptysound.wav");
		explosionSound = getAudioClip(getDocumentBase(), "explosion.wav");
		gatlingSound = getAudioClip(getDocumentBase(), "gatling.wav");
		locknloadSound = getAudioClip(getDocumentBase(), "locknload.wav");
		healthSound = getAudioClip(getDocumentBase(), "health.wav");
		bosshitSound = getAudioClip(getDocumentBase(), "bosshit.wav");
		jethitSound = getAudioClip(getDocumentBase(), "jethit.wav");
		bossexplodeSound = getAudioClip(getDocumentBase(), "bossexplode.wav");
		jetbg = getAudioClip(getDocumentBase(), "jetbg.wav");
		helicopterbg = getAudioClip(getDocumentBase(), "helicopterbg.wav");
		soundtrack = getAudioClip(getDocumentBase(), "soundtrack.wav");

		healthDrop = new SupplyDrop[2];
		for (int x = 0; x < healthDrop.length; x++) {
			healthDrop[x] = new SupplyDrop((int) (700 * Math.random() + 100), -(int) (8000 * Math.random() + 1500), 65,
					80);
		}
		ammoDrop = new SupplyDrop[2];
		for (int x = 0; x < ammoDrop.length; x++) {
			ammoDrop[x] = new SupplyDrop((int) (700 * Math.random() + 100), -(int) (6000 * Math.random() + 1000), 60,
					110);
		}

		cannon = new projectile[26];
		for (int x = 0; x < cannon.length; x++) {
			cannon[x] = new projectile(0, 0, 17, 17);
		}

		eArray = new Enemy[10];
		for (int x = 0; x < eArray.length; x++) {
			eArray[x] = new Enemy((int) (700 * Math.random() + 150), -500 + (int) (25 * Math.random() * x), 40, 60,
					2 + (int) (2 * Math.random()), 1);
		}

		jet = new plane[(int) (6 * Math.random() + 3)];
		for (int e = 0; e < jet.length; e++) {
			randomJet = (int) (2 * Math.random());
			if (randomJet == 0) {
				jet[e] = new plane(-200 + (int) (200 * Math.random()), 10 + (int) (200 * Math.random()), 100, 50, 1,
						(int) (4 * Math.random() + 2));
				jet[e].moveRight = true;
			}
			if (randomJet == 1) {
				jet[e] = new plane(1000 + (int) (200 * Math.random()), 10 + (int) (200 * Math.random()), 100, 50, 1,
						(int) (4 * Math.random() + 2));
				jet[e].moveLeft = true;
			}
		}

		airstrike = new bomb[100];
		for (int x = 0; x < airstrike.length; x++) {
			airstrike[x] = new bomb(0, 0, 20, 40, 2);
		}

		bullet = new bullet[100];
		for (int x = 0; x < bullet.length; x++) {
			bullet[x] = new bullet(0, 0, 30, 60, 1);
		}

		boss = new boss(0, -500, 250, 200, 20);

		aliveCount = eArray.length;
		jetCount = jet.length;

		soundtrack.loop();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		thread = new Thread(this);
		thread.start();
	}

	public void moveEverything() {
		if (gameStart == true) {
			tank.move();

			for (int i = 0; i < eArray.length; i++) {
				eArray[i].move();
			}

			for (int x = 0; x < cannon.length; x++) {
				cannon[x].move();
			}

			for (int x = 0; x < healthDrop.length; x++) {
				healthDrop[x].move();
			}

			for (int x = 0; x < ammoDrop.length; x++) {
				ammoDrop[x].move();
			}

			if (levelCount == 5 || levelCount == 4) {
				for (int e = 0; e < jet.length; e++) {
					jet[e].moveRight();
					jet[e].moveLeft();
				}

				for (int x = 0; x < airstrike.length; x++) {
					airstrike[x].move();
					airstrike[x].moveDown = true;
				}
			}
			if (levelCount == 6) {
				boss.move();
				boss.reset();
				if (boss.isMoving == true) {
					if (move == true) {
						boss.right();
						boss.up();
					}
					if (move == false) {
						boss.left();
						boss.up();
					}
					if (boss.xpos >= 1000) {
						move = false;
						boss.dx = (int) (6 * Math.random() + 2);
						boss.ypos = (int) (400 * Math.random());
					}
					if (boss.xpos <= -200) {
						move = true;
						boss.dx = (int) (6 * Math.random() + 2);
						boss.dy = (int) (4 * Math.random() - 4);
						boss.ypos = (int) (400 * Math.random());
					}
					if (boss.ypos >= 400) {
						boss.ypos = 400;
					}
					if(boss.ypos >= 500){
						boss.dy = 4;
					}
				}

				if (boss.isAlive == true && random == 11 && boss.ypos > 0 && boss.ypos < 400 && boss.xpos > 100
						&& boss.xpos < 650) {

					regBossShot = true;
					locknloadSound.play();

				}

				if (random == 10 && boss.ypos >= 0 && boss.ypos <= 400 && boss.xpos > 100 && boss.xpos < 650) {
					bossAlive = true;
				}
				if (bossAlive == true) {
					boss.isMoving = false;
					bossTime--;
					boss.reset();
					if (bossTime == 0) {
						bossAlive = false;
						bossTime = 100;
					}
				}
				boss.isMoving = true;

				for (int x = 0; x < airstrike.length; x++) {
					airstrike[x].move();
					airstrike[x].moveDown = true;
				}
				for (int x = 0; x < bullet.length; x++) {
					bullet[x].move();
					bullet[x].moveDown = true;
				}
			}
		}
	}

	public void checkIntersections() {
		if (levelCount < 4) {
			for (int i = 0; i < eArray.length; i++) {
				if (eArray[i].rec.intersects(wall.rec) || eArray[i].rec.intersects(wallRight.rec)) {
					if (eArray[i].isAlive == true) {
						eArray[i].isAlive = false;
						aliveCount--;
					}
				}
				if (eArray[i].rec.intersects(parachuteZone.rec)) {
					eArray[i].parachute = true;
				}
				if (eArray[i].rec.intersects(parachuteZone.rec) && eArray[i].parachute == true) {
					eArray[i].dy = 1;
				}
				for (int x = 0; x < cannon.length; x++) {
					if (eArray[i].isAlive == true && cannon[x].isAlive == true
							&& eArray[i].rec.intersects(cannon[x].rec)) {
						eArray[i].isAlive = false;
						cannon[x].isAlive = false;
						aliveCount--;
					}
				}
				if (eArray[i].rec.intersects(floor.rec) && eArray[i].isAlive == true) {
					eArray[i].isAlive = false;
					deathCount++;
					aliveCount--;
				}
			}
		}

		if (tutorial == true) {
			if (healthDrop[0].rec.intersects(tank.rec) && healthDrop[0].isAlive == true) {
				tank.health++;
				healthSound.play();
				healthDrop[0].isAlive = false;
			}
			if (ammoDrop[0].rec.intersects(tank.rec) && ammoDrop[0].isAlive == true) {
				ammoDrop[0].isAlive = false;
				locknloadSound.play();
				cannon = new projectile[cannon.length + 5];
				for (int y = 0; y < cannon.length; y++) {
					cannon[y] = new projectile(0, 0, 17, 17);
				}
			}
		}

		if (levelCount == 5 || levelCount == 4 || levelCount == 6) {
			for (int i = 0; i < jet.length; i++) {
				for (int x = 0; x < cannon.length; x++) {
					if (jet[i].isAlive == true && cannon[x].isAlive == true && jet[i].rec.intersects(cannon[x].rec)) {
						jet[i].stop();
						jet[i].explodeStatus = true;
						jethitSound.play();
						jet[i].isAlive = false;
						cannon[x].isAlive = false;
						jetCount--;
					}
				}
			}

			for (int x = 0; x < airstrike.length; x++) {
				for (int z = 0; z < bullet.length; z++) {

					if (bullet[z].rec.intersects(tank.rec) && bullet[z].isAlive == true && tank.isAlive == true) {
						bosshitSound.play();
						tank.health--;
						bullet[z].isAlive = false;
					}
				}
				if (airstrike[x].rec.intersects(wall.rec) || airstrike[x].rec.intersects(wallRight.rec)) {
					airstrike[x].isAlive = false;
				}
				if (airstrike[x].rec.intersects(tank.rec) && airstrike[x].isAlive == true && tank.isAlive == true) {
					airstrike[x].ypos = floor.ypos - 51;
					airstrike[x].recAlive = false;
					airstrike[x].rec = new Rectangle(0, airstrike[x].ypos, 0, 0);
					airstrike[x].boom = true;
					airstrike[x].boomhit = true;
					airstrike[x].boomhithit = true;
					tank.health--;
				}

				if (airstrike[x].ypos >= floor.ypos - 50 && airstrike[x].isAlive == true) {
					airstrike[x].stop();
					airstrike[x].ypos = floor.ypos - 51;
					airstrike[x].boom = true;
					airstrike[x].boomStatus = true;
				}
				for (int p = 0; p < cannon.length; p++) {
					if (airstrike[x].rec.intersects(cannon[p].rec) && cannon[p].isAlive == true
							&& airstrike[x].isAlive == true) {
						airstrike[x].fireball = true;
						cannon[p].isAlive = false;
						airstrike[x].health--;
					}
					if (boss.rec.intersects(cannon[p].rec) && cannon[p].isAlive == true && boss.isAlive == true) {
						bosshitSound.play();
						boss.health--;
						cannon[p].isAlive = false;
					}
				}
				if (airstrike[x].rec.intersects(floor.rec) && tank.isAlive == false) {
					gameOver = true;
					for (int i = 0; i < jet.length; i++) {
						if (jet[i].xpos <= wall.xpos || jet[i].xpos > wallRight.xpos) {
							jet[i].stop();
						}
					}
				}

				if (airstrike[x].health == 0) {
					airstrike[x].isAlive = false;
				}
			}

			if (random == 50) {
				airstrike[bombCount].isAlive = true;
				if (airstrike.length == bombCount) {
					bombCount = 0;
				}
				for (int x = 0; x < jet.length; x++) {
					if (airstrike[bombCount].isAlive == true && jet[x].isAlive == true) {
						airstrike[bombCount].xpos = jet[x].xpos;
						airstrike[bombCount].ypos = jet[x].ypos;

						bombCount++;
					}
				}
			}
			if (levelCount == 5 && random == 20) {
				airstrike[bombCount].isAlive = true;
				if (airstrike.length == bombCount) {
					bombCount = 0;
				}
				for (int x = 0; x < jet.length; x++) {
					if (airstrike[bombCount].isAlive == true && jet[x].isAlive == true) {
						airstrike[bombCount].xpos = jet[x].xpos;
						airstrike[bombCount].ypos = jet[x].ypos;

						bombCount++;

					}
				}
			}

			for (int x = 0; x < healthDrop.length; x++) {
				if (random == 99) {
					healthDrop[healthCount].isAlive = true;
					if (healthDrop.length - 1 == healthCount) {
						healthCount = 0;
					}
					if (healthDrop[healthCount].isAlive == true) {
						healthCount++;
					}
				}

				if (healthDrop[x].rec.intersects(tank.rec) && tank.isAlive == true && healthDrop[x].isAlive == true) {
					healthSound.play();
					healthDrop[x].isAlive = false;
					healthDrop[x].ypos = floor.ypos + 100;
					tank.health++;
				}
				if (healthDrop[x].rec.intersects(floor.rec) && tank.isAlive == true && healthDrop[x].isAlive == true) {
					healthDrop[x].isAlive = false;
				}
				if (gameOver == true) {
					healthDrop[x].isAlive = false;
				}
			}

			for (int x = 0; x < ammoDrop.length; x++) {
				if (random == 80) {
					ammoDrop[ammoCount].isAlive = true;
					if (ammoDrop.length - 1 == ammoCount) {
						ammoDrop[ammoCount].isAlive = false;
					}
					if (ammoDrop[ammoCount].isAlive == true) {
						ammoCount++;
					}
				}
				if (random == 81 && fireCount == 0) {
					ammoDrop[ammoCount].isAlive = true;
					if (ammoDrop.length - 1 == ammoCount) {
						ammoDrop[ammoCount].isAlive = false;
					}
					if (ammoDrop[ammoCount].isAlive == true) {
						ammoCount++;
					}
				}

				if (ammoDrop[x].rec.intersects(tank.rec) && tank.isAlive == true && ammoDrop[x].isAlive == true) {
					locknloadSound.play();
					ammoDrop[x].isAlive = false;
					ammoDrop[x].ypos = floor.ypos + 100;
					makeCannon();
				}
				if (ammoDrop[x].rec.intersects(floor.rec) && ammoDrop[x].isAlive == true) {
					ammoDrop[x].open = true;
					ammoDrop[x].stop();
					ammoStatus = true;
				}
				for (int y = 0; y < airstrike.length; y++) {
					if (ammoDrop[x].rec.intersects(airstrike[y].rec) && tank.isAlive == true
							&& airstrike[y].isAlive == true) {
						ammoDrop[x].isAlive = false;
						ammoDrop[x].hitairstrike = true;
					}
				}
				if (gameOver == true) {
					ammoDrop[x].isAlive = false;
				}
			}
		}

		if (levelCount == 6) {
			if (bossTime == 75 || bossTime == 50 || bossTime == 25) {
				airstrike[bossShot].isAlive = true;
				if (airstrike.length == bossShot) {
					bossShot = 0;
				}
				if (airstrike[bossShot].isAlive == true && boss.isAlive == true) {
					airstrike[bossShot].xpos = boss.xpos + 110;
					airstrike[bossShot].ypos = boss.ypos + 50;
					regBossShot = false;
					bossShot++;
				}
			}
			if (regBossShot == true) {
				regBossShotTime--;
				if (regBossShotTime % 25 == 0 && regBossShotTime >= 0) {
					bullet[boss2Shot].isAlive = true;
					if (bullet.length == boss2Shot) {
						boss2Shot = 0;
					}
					if (bullet[boss2Shot].isAlive == true && boss.isAlive == true) {
						gatlingSound.play();
						bullet[boss2Shot].xpos = boss.xpos + 110;
						bullet[boss2Shot].ypos = boss.ypos + 50;

						boss2Shot++;
					}

				}
				if (regBossShotTime == 0) {
					regBossShot = false;
					regBossShotTime = 100;
				}
			}

		}

		if (tank.health <= 0) {
			tank.isAlive = false;
		}
		if (boss.health <= 0) {
			boss.isMoving = false;
			boss.isAlive = false;
			boss.health = -1;
			boss.explodeStatus = true;
		}
	}

	public void makeCannon() {
		cannon = new projectile[cannon.length + 10];
		for (int y = 0; y < cannon.length; y++) {
			cannon[y] = new projectile(0, 0, 17, 17);
		}
	}

	public void checkAlive() {
		if (numDead() == true) {
			gameOver = true;
		}
		if (allJDead() == true || allEDead() == true) {
			if (tutorial == true && deathCount != 5 && deathCount != 4 && deathCount != 3) {
				realgame = true;
				tutorial = false;
			}
			if (gameOver == false && tutorial == false) {
				sleep(200);
				nextLevel();
			}
		}
	}

	public void sleep(int x) {
		try {
			thread.sleep(x);
		} catch (Exception e) {
		}
	}

	public boolean numDead() {
		for (int i = 0; i < eArray.length; i++) {
			if (deathCount >= 3) {
				return (true);
			}
		}
		return (false);
	}

	public boolean allEDead() {
		if (aliveCount == 0) {
			return (true);
		}
		return (false);
	}

	public boolean allJDead() {
		if (jetCount == 0) {
			return (true);
		}
		return (false);
	}

	public void paint(Graphics g) {
		textCount--;
		bufferGraphics.clearRect(0, 0, 1000, 1000);
		if (gameStart == false) {
			bufferGraphics.drawString("PRESS 'S' TO START", 465, 350);
			bufferGraphics.drawString("ARROW KEYS TO START AND 'SPACE' TO SHOOT", 380, 300);
			bufferGraphics.drawImage(tutorialPic, tutorialbutton.xpos, tutorialbutton.ypos, tutorialbutton.width,
					tutorialbutton.height, this);
		}
		if (gameStart == true) {
			if (levelCount < 4) {
				for (int x = 0; x < eArray.length; x++) {
					if (eArray[x].isAlive == true && eArray[x].parachute == false) {
						bufferGraphics.drawImage(enemyPic, eArray[x].xpos, eArray[x].ypos, eArray[x].width,
								eArray[x].height, this);
					}
					if (eArray[x].isAlive == true && eArray[x].rec.intersects(parachuteZone.rec)
							&& eArray[x].parachute == true) {
						bufferGraphics.drawImage(parachutePic, eArray[x].xpos - 15, eArray[x].ypos - 40, 70, 120, this);
					}
				}
			}

			if (levelCount == 5 || levelCount == 4 || levelCount == 6 || tutorial == true) {
				for (int x = 0; x < ammoDrop.length; x++) {
					if (ammoDrop[x].isAlive == true) {
						if (ammoDrop[x].open == false) {
							bufferGraphics.drawImage(ammoDropPic, ammoDrop[x].xpos, ammoDrop[x].ypos, ammoDrop[x].width,
									ammoDrop[x].height, this);
							if (ammoDrop[x].hitairstrike == true) {
								bufferGraphics.drawImage(jetfireballPic, ammoDrop[x].xpos, ammoDrop[x].ypos,
										ammoDrop[x].width, ammoDrop[x].height, this);
							}
						}
						if (ammoDrop[x].open == true && ammoStatus == true) {
							ammoTime--;
							bufferGraphics.drawImage(ammoOpenPic, ammoDrop[x].xpos,
									ammoDrop[x].ypos + (ammoDrop[x].height - ammoDrop[x].width), ammoDrop[x].width,
									ammoDrop[x].width, this);
							if (ammoTime == 0) {
								ammoStatus = false;
								ammoDrop[x].isAlive = false;
								ammoTime = 50;
							}
						}
					}
				}

				for (int x = 0; x < healthDrop.length; x++) {
					if (healthDrop[x].isAlive == true) {
						bufferGraphics.drawImage(healthDropPic, healthDrop[x].xpos, healthDrop[x].ypos,
								healthDrop[x].width, healthDrop[x].height, this);
					}
				}

				for (int x = 0; x < airstrike.length; x++) {
					for (int y = 0; y < bullet.length; y++) {
						if (bullet[y].isAlive == true) {
							bufferGraphics.drawImage(bulletPic, bullet[y].xpos, bullet[y].ypos, bullet[y].width,
									bullet[y].height, this);
						}
					}
					if (airstrike[x].isAlive == true && airstrike[x].boom == false) {
						bufferGraphics.drawImage(bombPic, airstrike[x].xpos, airstrike[x].ypos, airstrike[x].width,
								airstrike[x].height, this);
					}

					if (airstrike[x].boomStatus == true) {
						if (airstrike[x].isAlive == true && airstrike[x].boomhit == true) {
							bufferGraphics.drawImage(fireworksPic, airstrike[x].xpos - 40, airstrike[x].ypos - 50, 100,
									100, this);
						}
						if (airstrike[x].isAlive == true && airstrike[x].boomhithit == true) {
							bufferGraphics.drawImage(boomhithitPic, airstrike[x].xpos - 40, airstrike[x].ypos - 50, 100,
									100, this);
						}
						if (airstrike[x].isAlive == true && airstrike[x].boom == true) {
							bufferGraphics.drawImage(explosionPic, airstrike[x].xpos - 35, airstrike[x].ypos - 50, 100,
									100, this);
						}
						airstrike[x].boomTime--;

						if (airstrike[x].boomTime == 39) {
							explosionSound.play();
						}

						if (airstrike[x].boomTime == 0) {
							airstrike[x].boomStatus = false;
							airstrike[x].isAlive = false;
							airstrike[x].boomTime = 40;
						}
					}
					if (airstrike[x].isAlive == true && airstrike[x].boom == false && airstrike[x].fireball == true) {
						bufferGraphics.drawImage(fireballPic, airstrike[x].xpos - 15, airstrike[x].ypos - 100, 50, 150,
								this);
					}
				}

				for (int e = 0; e < jet.length; e++) {
					if (jet[e].isAlive == true && jet[e].moveRight == true) {
						bufferGraphics.drawImage(jetLPic, jet[e].xpos, jet[e].ypos, jet[e].width, jet[e].height, this);
					}
					if (jet[e].isAlive == true && jet[e].moveLeft == true) {
						bufferGraphics.drawImage(jetRPic, jet[e].xpos, jet[e].ypos, jet[e].width, jet[e].height, this);
					}
					if (jet[e].explodeStatus == true) {

						jet[e].explodeTime--;
						bufferGraphics.drawImage(jetfireballPic, jet[e].xpos, jet[e].ypos, 120, 100, this);

						if (jet[e].explodeTime == 0) {
							jet[e].explodeTime = 40;
							jet[e].explodeStatus = false;
						}
					}
				}
			}

			if (levelCount == 6) {
				if (boss.isAlive == true) {
					if (boss.moveUp == true || boss.moveDown == true || boss.isMoving == true) {
						if (boss.moveLeft == false && boss.moveRight == false) {
							bufferGraphics.drawImage(bossFrontPic, boss.xpos, boss.ypos, boss.width, boss.height - 20,
									this);
						}
					}
					if (boss.moveLeft == true && boss.moveRight == false) {
						bufferGraphics.drawImage(bossRSidePic, boss.xpos, (boss.ypos + 15), boss.width, boss.height,
								this);
					}
					if (boss.moveRight == true && boss.moveLeft == false) {
						bufferGraphics.drawImage(bossLSidePic, boss.xpos, (boss.ypos + 15), boss.width, boss.height,
								this);
					}
					bufferGraphics.setColor(Color.red);
					bufferGraphics.fillRect(boss.xpos + 75, boss.ypos - 10, boss.health * 5, 10);
				}
				if (boss.explodeStatus == true) {
					boss.explodeTime--;
					if (boss.explodeTime <= 30) {
						bufferGraphics.drawImage(jetfireballPic, boss.xpos, boss.ypos, 100, 100, this);
					}
					if (boss.explodeTime <= 25) {
						bufferGraphics.drawImage(jetfireballPic, boss.xpos + 50, boss.ypos, 150, 150, this);
					}
					if (boss.explodeTime <= 20) {
						bufferGraphics.drawImage(jetfireballPic, boss.xpos + 50, boss.ypos + 50, 100, 150, this);
					}
					if (boss.explodeTime <= 15) {
						bufferGraphics.drawImage(jetfireballPic, boss.xpos, boss.ypos + 50, 150, 100, this);
					}
					if (boss.explodeTime <= 10) {
						bufferGraphics.drawImage(jetfireballPic, boss.xpos - 30, boss.ypos - 30, 200, 200, this);
					}
					if (boss.explodeTime == 7) {
						bossexplodeSound.play();
					}
				}

			}

			for (int x = 0; x < cannon.length; x++) {
				if (cannon[x].isAlive == true) {
					bufferGraphics.drawImage(cannonPic, cannon[x].xpos, cannon[x].ypos, cannon[x].width,
							cannon[x].height, this);
				}
			}

			bufferGraphics.drawImage(wallPic, wall.xpos, wall.ypos, wall.width, wall.height, this);
			bufferGraphics.drawImage(wallPic, wallRight.xpos, wallRight.ypos, wallRight.width, wallRight.height, this);

			if (tank.isAlive == true) {
				bufferGraphics.drawImage(tankPic, tank.xpos, tank.ypos, tank.width, tank.height, this);
				bufferGraphics.setColor(Color.green);
				bufferGraphics.setColor(Color.getHSBColor(.35f, 1f, .6f));
				bufferGraphics.fillRect(tank.xpos + 10, tank.ypos, tank.health * 15, 10);
			}

			bufferGraphics.drawImage(wallPic, floor.xpos, floor.ypos, floor.width, floor.height, this);

			bufferGraphics.setColor(Color.white);
			bufferGraphics.drawString("Shots remaining: " + (cannon.length - fireCount - 1), 425, floor.ypos + 25);
			if (levelCount < 4) {
				if (textCount > 25 && deathCount > 2) {
					bufferGraphics.setColor(Color.RED);
				}
				bufferGraphics.drawString("ENEMIES LANDED: " + (deathCount), 425, floor.ypos + 50);
			}

			if (gameOver == true) {
				bufferGraphics.setColor(Color.white);
				bufferGraphics.drawString("PRESS 'R' FOR A NEW GAME", 100, floor.ypos + 50);
				if (textCount > 25) {
					bufferGraphics.setColor(Color.black);
				}
				bufferGraphics.drawString("GAME OVER", 100, floor.ypos + 25);
			}

			if (aliveCount >= 0 && gameOver == false && levelCount < 4) {
				bufferGraphics.drawString("ENEMY COUNT: " + (aliveCount), 100, floor.ypos + 25);
			}
			if (jetCount >= 0 && gameOver == false) {
				if (levelCount == 5 || levelCount == 4) {
					bufferGraphics.drawString("JET COUNT: " + (jetCount), 100, floor.ypos + 25);
				}
			}
			if (tutorial == false) {
				bufferGraphics.drawString("LEVEL " + levelCount, 850, floor.ypos + 25);
			}
			if (tank.health > 0) {
				bufferGraphics.drawString("LIVES LEFT: " + tank.health, 850, floor.ypos + 50);
			}
			if (tank.health <= 0) {
				bufferGraphics.setColor(Color.white);
				if (textCount > 25) {
					bufferGraphics.setColor(Color.red);
				}
				bufferGraphics.drawString("LIVES LEFT: " + 0, 850, floor.ypos + 50);
			}

			if (gameOver == false && tank.isAlive == true && boss.isAlive == false && levelCount > 5) {
				bufferGraphics.setColor(Color.black);
				bufferGraphics.fillRect(0, 700, 1000, 300);
				bufferGraphics.setColor(Color.white);
				bufferGraphics.drawString("PRESS 'R' FOR A NEW GAME", 100, floor.ypos + 50);
				bufferGraphics.drawString("WINNER", 500, floor.ypos + 25);
				jetbg.stop();
				helicopterbg.stop();
				soundtrack.stop();
			}
			if (realgame == true) {
				bufferGraphics.setColor(Color.black);
				bufferGraphics.fillRect(0, 700, 1000, 300);
				bufferGraphics.drawImage(realgamePic, realgamebutton.xpos, realgamebutton.ypos, realgamebutton.width,
						realgamebutton.height, this);
			}
			if(tutorial == true){
				bufferGraphics.setColor(Color.black);
				bufferGraphics.fillRect(0, 700, 1000, 300);
				bufferGraphics.setColor(Color.white);
				bufferGraphics.drawString("AMMO DROP", ammoDrop[0].xpos + 100, ammoDrop[0].ypos + 20);
				bufferGraphics.drawString("ADDS BULLETS", ammoDrop[0].xpos + 100, ammoDrop[0].ypos + 35);
				bufferGraphics.drawString("HEALTH DROP", healthDrop[0].xpos - 200, healthDrop[0].ypos + 20);
				bufferGraphics.drawString("ADDS A LIFE", healthDrop[0].xpos - 200, healthDrop[0].ypos + 35);
				bufferGraphics.drawString("PRESS 'H' TO TRADE A LIFE FOR 5 BULLETS", 100, 750);
				bufferGraphics.drawString("YOU ONLY HAVE A LIMITED AMOUNT OF BULLETS!", 100, 725);
				bufferGraphics.drawString("Shots remaining: " + (cannon.length - fireCount - 1), 425, floor.ypos + 25);
				bufferGraphics.drawString("If 3 or more soldiers land you lose", 425, floor.ypos + 50);
				bufferGraphics.drawString("LIVES LEFT: " + tank.health, 850, floor.ypos + 50);
				bufferGraphics.drawString("TUTORIAL LEVEL", 850, floor.ypos + 25);
				bufferGraphics.setColor(Color.white);
				if(gameOver == true){
				bufferGraphics.setColor(Color.black);
				bufferGraphics.fillRect(0, 700, 1000, 300);
				bufferGraphics.setColor(Color.white);
				bufferGraphics.drawString("PRESS 'R' FOR A NEW GAME", 100, floor.ypos + 50);
				bufferGraphics.drawString("GAME OVER", 100, floor.ypos + 25);
				}
			}
			if (textCount == 0) {
				textCount = 50;
			}
		}
		g.drawImage(offscreen, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void nextLevel() {
		if (tutorial == false) {
			levelCount++;
			System.out.println("          NEXT LEVEL");
			System.out.println("LEVEL " + levelCount);

			if (levelCount < 4) {
				cannon = new projectile[26];
			}
			if (levelCount == 4 || levelCount == 5) {
				cannon = new projectile[16];
				jetbg.loop();
				System.out.println("music");
			}
			if (levelCount == 6) {
				cannon = new projectile[21];
				helicopterbg.loop();
			}

			for (int x = 0; x < cannon.length; x++) {
				cannon[x] = new projectile(0, 0, 17, 17);
			}

			eArray = new Enemy[(int) (7 * Math.random() + 3) * 2];
			for (int x = 0; x < eArray.length; x++) {
				eArray[x] = new Enemy((int) (700 * Math.random() + 150), -500 + (int) (25 * Math.random() * x), 40, 60,
						2 + (int) (levelCount * Math.random()), 1);
				if (eArray.length > 14) {
					eArray[x].ypos = -1500 + (int) (75 * Math.random() * x);
				}
			}

			healthDrop = new SupplyDrop[2];
			for (int x = 0; x < healthDrop.length; x++) {
				healthDrop[x] = new SupplyDrop((int) (700 * Math.random() + 100), -(int) (8000 * Math.random() + 1500),
						65, 80);
			}
			ammoDrop = new SupplyDrop[2];
			for (int x = 0; x < ammoDrop.length; x++) {
				ammoDrop[x] = new SupplyDrop((int) (700 * Math.random() + 100), -(int) (6000 * Math.random() + 1000),
						60, 110);
			}

			jet = new plane[(int) (6 * Math.random() + 3)];
			for (int e = 0; e < jet.length; e++) {
				randomJet = (int) (2 * Math.random());
				if (randomJet == 0) {
					jet[e] = new plane(-200 + (int) (200 * Math.random()), 10 + (int) (200 * Math.random()), 100, 50, 1,
							(int) (4 * Math.random() + 2));
					jet[e].moveRight = true;
				}
				if (randomJet == 1) {
					jet[e] = new plane(1000 + (int) (200 * Math.random()), 10 + (int) (200 * Math.random()), 100, 50, 1,
							(int) (4 * Math.random() + 2));
					jet[e].moveLeft = true;
				}

				airstrike = new bomb[100];
				for (int x = 0; x < airstrike.length; x++) {
					airstrike[x] = new bomb(0, 0, 20, 40, 2);
				}
			}

			bullet = new bullet[100];
			for (int x = 0; x < bullet.length; x++) {
				bullet[x] = new bullet(0, 0, 30, 60, 1);
			}

			boss = new boss(0, -500, 250, 200, 20);

			aliveCount = eArray.length;
			jetCount = jet.length;

			fireCount = 0;
			bombCount = 0;
			deathCount = 0;
			healthCount = 0;
			ammoCount = 0;
			bossShot = 0;

			move = true;
		}
	}

	public void restart() {
		realgame = false;
		if (tutorial == true) {
			tutorial();
		} else {
			System.out.println("          RESTART LEVEL");
			thread.suspend();

			cannon = new projectile[26];
			eArray = new Enemy[10];

			for (int x = 0; x < cannon.length; x++) {
				cannon[x] = new projectile(450, 1000, 17, 17);
			}

			for (int x = 0; x < eArray.length; x++) {
				eArray[x] = new Enemy((int) (700 * Math.random() + 150), -500 + (int) (25 * Math.random() * x), 40, 60,
						2 + (int) (2 * Math.random()), 1);
			}

			aliveCount = eArray.length;
			fireCount = 0;
			levelCount = 1;
			deathCount = 0;
			tank.health = 5;
			tank.isAlive = true;

			gameOver = false;

			thread = new Thread(this);
			thread.start();
		}
	}

	public void tutorial() {
		System.out.println("          TUTORIAL LEVEL");
		thread.suspend();

		cannon = new projectile[4];
		eArray = new Enemy[5];

		healthDrop = new SupplyDrop[1];
		healthDrop[0] = new SupplyDrop(200, -600, 65, 80);

		ammoDrop = new SupplyDrop[1];
		ammoDrop[0] = new SupplyDrop(800, -300, 60, 110);

		for (int x = 0; x < cannon.length; x++) {
			cannon[x] = new projectile(450, 1000, 17, 17);
		}

		for (int x = 0; x < eArray.length; x++) {
			eArray[x] = new Enemy((int) (700 * Math.random() + 150), -2000 + (int) (25 * Math.random() * x), 40, 60,
					2 + (int) (2 * Math.random()), 1);
		}

		aliveCount = eArray.length;
		fireCount = 0;
		levelCount = 1;
		deathCount = 0;
		tank.health = 5;
		tank.isAlive = true;

		gameOver = false;

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (true) {
			random = (int) (100 * Math.random());
			moveEverything();
			checkIntersections();
			checkAlive();
			if(sound == true){
				soundCount--;
				if (soundCount == 0) {
					soundCount = 20;
					sound = false;
				}
			}
			repaint();

			try {
				thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	public void keyPressed(KeyEvent event) {
		String keyin;
		keyin = "" + event.getKeyText(event.getKeyCode());
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			if (tank.xpos > wall.xpos && gameOver == false) {
				tank.moveLeft = true;
			}

		}
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (tank.xpos < wallRight.xpos && gameOver == false) {
				tank.moveRight = true;
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			if (gameOver == false && sound == false) {
				cannon[fireCount].isAlive = true;
				sound = true;

				if ((cannon.length - fireCount - 1) == 0) {
					emptySound.play();
					cannon[fireCount].isAlive = false;
				}
				if (cannon[fireCount].isAlive == true) {
					for (int x = 0; x < cannon.length; x++) {
						cannonSound.play();
						cannon[fireCount].xpos = tank.xpos + 68;
						cannon[fireCount].ypos = tank.ypos + 20;
					}
					fireCount++;
				}
			}
		}
		if (keyin.equals("R") && gameOver == true) {
			restart();
		}
		if (keyin.equals("R") && gameOver == false && tank.isAlive == true && boss.isAlive == false) {
			restart();
		}
		if (keyin.equals("S")) {
			System.out.println("LEVEL " + levelCount);
			gameStart = true;
		}
		if (keyin.equals("H") && tank.health > 1) {
			locknloadSound.play();
			tank.health--;
			cannon = new projectile[cannon.length + 5];
			for (int y = 0; y < cannon.length; y++) {
				cannon[y] = new projectile(0, 0, 17, 17);
			}
		}
	}

	public void keyReleased(KeyEvent event) {
		String keyin;
		keyin = "" + event.getKeyText(event.getKeyCode());
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			tank.moveLeft = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			tank.moveRight = false;
		}

	}

	public void keyTyped(KeyEvent event) {
		char keyin;
		keyin = event.getKeyChar();
		// System.out.println("Key Typed;"+keyin);
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (tutorialbutton.rec.contains(mouseX, mouseY)) {
			tutorial();
			tutorial = true;
			gameStart = true;
		}

		if (realgame == true) {
			if (realgamebutton.rec.contains(mouseX, mouseY)) {
				tutorial = false;
				restart();
				gameStart = true;
			}
		}

	}
}