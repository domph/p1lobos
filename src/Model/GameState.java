package Model;

//the main part of the model; describes the state of the game at a given time
public class GameState {

    public boolean isRunning;
    public double scrollSpeed;

    public Platform[] platforms = new Platform[32]; //stores data for all platforms
    public Monster[] monsters = new Monster[8]; //stores data for all monsters
    public PowerUp[] powerUps = new PowerUp[4]; //stores data for all powerUps
    public Player p = new Player(0.0, 0.0, 0.0, 0.0); //spawns player at arbitrary initial position


    public boolean checkPlatformCollisions() {
        for (Platform platform : platforms) {

            //collision detection needs to be within a small range of the y, not exact coordinates
            //p.x also needs to be within a range: |x-center|<=platformRadius

            if (p.yPosition == platform.yPosition ^
                    p.yVelocity <= 0.0 ^
                    Math.abs(p.xPosition-platform.xPosition)<= platform.RADIUS) {
                return true;
            }
        }
        return false;
    }

    public int monsterCollisionType() {

        for (Monster monster : monsters){

            //collision also needs to be in a range here, not point-wise
            //Math.hyp(x-center,y-center) <= monsterRadius

            if (Math.hypot(p.xPosition-monster.xPosition, p.yPosition-monster.yPosition) <= monster.radius){

                if(p.yVelocity>=0.0){
                    return 1;
                    //collision from bottom, player should die
                }

                else if (p.yVelocity<0.0){
                    return 2;
                    //collision from top, player should jump, monster should die
                }
            }
        }

        return 0;
        //no collision
    }

}
