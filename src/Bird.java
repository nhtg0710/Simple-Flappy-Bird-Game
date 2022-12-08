import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.awt.event.MouseAdapter;

public class Bird {
    private final Image UP_BIRD = new Image("res/birdWingUp.png");
    private final Image DOWN_BIRD = new Image("res/birdWingDown.png");
    private Point birdLocation;
    private final int HEIGHT = Window.getHeight();
    private final int FLY_UP = 6;
    private final int MAXIMUM_FALLING_SPEED = 10;
    private final double ACCELERATION = 0.4;

    // initial position of the bird is in the specs
    // so I don't consider it necessary to declare a constant
    public Bird(){
        birdLocation = new Point (200,350);
    }

    public int getBirdHeight(){
        return birdLocation.y;
    }

    /** when SPACE is pressed, frameSinceLastFall will be set to 0
    * the bird will fly up by 6 pixels in the next frame,
    * unaffected by gravity*/
    public int calculateSpeed(int frameSinceLastFall) {
        double fallingSpeed = ACCELERATION * frameSinceLastFall;
        if (Math.abs(FLY_UP - fallingSpeed) <= MAXIMUM_FALLING_SPEED) {
            return - (int) (FLY_UP - fallingSpeed);
        } else {
            return MAXIMUM_FALLING_SPEED;
        }
    }

    /**
     * bird flies up 'speed' pixels
     */
    public void moveUp(int speed){
        birdLocation.y += speed;
    }

    public void update(int frame, int speed){
        moveUp(speed);
        if (frame%10 == 0){
            UP_BIRD.draw(birdLocation.x, birdLocation.y);
        }
        else {
            DOWN_BIRD.draw(birdLocation.x, birdLocation.y);
        }
    }

    // check if bird collided with pipes
    public boolean checkCollision(int birdHeight, int pipePosition){
        Pipe pipe = new Pipe();
        Image pipes = new Image("res/pipe.png");
        Rectangle bird =
                new Rectangle(birdLocation.x - (int) UP_BIRD.getWidth()/2,
                birdHeight - (int) UP_BIRD.getHeight()/2,
                (int) UP_BIRD.getWidth(), (int) UP_BIRD.getHeight());

        // (HEIGHT - GAP)/2 because we only count visible parts of the pipes
        Rectangle upperPipe = new Rectangle
                (pipePosition - (int) pipes.getWidth()/2,
                pipe.getUPPER_PIPE_TOP_LEFT_Y(),
                (int) pipes.getWidth(), (HEIGHT - pipe.getGAP())/2);

        // same reasoning for (HEIGHT - GAP)/2 here
        Rectangle lowerPipe = new Rectangle
                (pipePosition - (int) pipes.getWidth()/2,
                pipe.getLOWER_PIPE_TOP_LEFT_Y(),
                (int) pipes.getWidth(), (HEIGHT - pipe.getGAP())/2);

        return (bird.intersects(upperPipe) || bird.intersects(lowerPipe));
    }

    // check out-of-bound error
    public boolean isOutOfBound(int birdHeight){
        return (birdHeight < 0) || (birdHeight > HEIGHT);
    }

    // check if bird passes the pipes successfully
    public boolean hasScored (int pipePosition){
        Image pipes = new Image("res/pipe.png");
        Pipe pipe = new Pipe();

        /** I sandwich the term below between -5 and 0 to
        ensure score is recorded just once
        at the very moment the bird goes through the pipes.
        If we use <= 0 and not apply a lower limit,
        the score is incremented each frame the pipes are
        behind the bird, meaning the score will keep going up as the bird
        moves forward
        */
        return -pipe.getSPEED() <= birdLocation.x - (pipePosition + (int) pipes.getWidth()/2) &&
                birdLocation.x - (pipePosition + (int) pipes.getWidth()/2) < 0;
    }

}
