package com.dbs.dbs;

import com.dbs.dbs.models.units.Footman;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameTests {

    GameService gameService = new GameService();

    @Test
    void getMovingDirectionAngleTest(){
        Unit unit = new Footman(0.0, 0.0);
        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) > 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) < 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) < 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) > 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
    }


}
