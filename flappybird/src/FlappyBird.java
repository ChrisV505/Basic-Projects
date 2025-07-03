import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    final int boardWidth = 360;
    final int boardHeight = 640;
    ArrayList<Pipe> pipes;
    Random random = new Random();

    //timers
    Timer gameLoop;
    Timer placePipesTimer;

//load images
    final Image backgroundImg = new ImageIcon(getClass().getResource("images/flappybirdbg.png")).getImage();
    final Image birdImg = new ImageIcon(getClass().getResource("images/flappybird.png")).getImage();
    final Image topPipeImg = new ImageIcon(getClass().getResource("images/toppipe.png")).getImage();
    final Image bottomPipeImg = new ImageIcon(getClass().getResource("images/bottompipe.png")).getImage();

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    final int birdWidth = 34;
    final int birdHeight = 24;

    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    final int pipeWidth = 64; //scaled by 1/6
    final int pipeHeight = 512;

    //game logic
    Bird bird;
    int velocityX = -4; //move pipes to the left speed (simulates bird moving right)
    int velocityY = 0; //move bird up/down speed
    int gravity = 1;

    boolean gameOver = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //bird
        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);
        pipes = new ArrayList<>();

        //place pipes timer
        placePipesTimer = new Timer(1500, __ -> placePipes()); //'_' used to prevent unused warning
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this); //1000/60 = 16.6;
        gameLoop.start();
    }

    public void placePipes() {
        //(0 - 1) * pipeHeight/2 -> (0 - 256)
        //128
        //0 - 128 - (0-256) --> pipeHeight/4 -> 3/4 pipeHeight
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = boardHeight/4;

        Pipe topPipe = new Pipe(pipeX, pipeY, pipeWidth, pipeHeight, topPipeImg);
        topPipe.setY(randomPipeY);;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeX, pipeY, pipeWidth, pipeHeight, bottomPipeImg);
        bottomPipe.setY(topPipe.getY() + pipeHeight + openingSpace);
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
         //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, this);

        //bird
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);

        // 
        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(gameOver) {
            g.drawString("Game over: " + String.valueOf((int) score), 10, 50);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        //bird
        velocityY += gravity;
        bird.setY(bird.getY() + velocityY);
        bird.setY(Math.max(bird.getY(), 0));

        //pipes
        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setX(pipe.getX() + velocityX);

            if(!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed(true);
                score += 0.5; //0.5 because ther are 2 pipes so 0.5*2 = 1, 1 for each set up pipes
            }

            if(collision(bird, pipe)) {
                gameOver = true;
            }
        }

        //gameOver checks
        if(bird.getY() > boardHeight) {
            gameOver = true;
        } 
        else if(bird.getY() == 0) { //gameOver if top of frame is reached "0"
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getWidth() && //a's top left corner doesn't reach b's top right corner
                a.getX() + a.getWidth() > b.getX() &&  //a's top right corner passes b's top left corner
                a.getY() < b.getY() + b.getHeight() && //a's top left corner doesn't reach b's bottom left corner
                a.getY() + a.getHeight() > b.getY(); //a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if(gameOver) {
                //restart the game by resetting the conditions
                bird.setY(birdY);
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}