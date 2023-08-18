package com.dbs.services;

import com.dbs.enumerations.CommandType;
import com.dbs.enumerations.UnitType;
import com.dbs.exceptions.TooManyConnectionsException;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.utils.UnitControlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
public class GameService {

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final UnitService unitService;
    private final Game game;

    public GameService(UnitService unitService, Game game) {
        this.unitService = unitService;
        this.game = game;
    }

    public void moveUnit(Unit unit, Double newX, Double newY) throws InterruptedException {
        Runnable moveTask = () -> {
            try {
                UnitControlUtil.moveUnit(unit, newX, newY);
            } catch (InterruptedException e) {
                System.out.println("Direction change");
            }
        };
        assignAndRunTask(unit, moveTask, CommandType.MOVE);
    }


    public void attackUnit(Long attackerId, Long defenderId) throws InterruptedException {
        Unit attacker;
        Unit defender;
        double damage;
        synchronized (this) {
            attacker = getUnitOfGivenId(attackerId);
            defender = getUnitOfGivenId(defenderId);
            if (attacker == null || defender == null)
                return;

            double distance = sqrt(pow(attacker.getPositionX() - defender.getPositionX(), 2)
                    + pow(attacker.getPositionY() - defender.getPositionY(), 2));
            if (distance > attacker.getRange()) {
                System.out.println("Out of range");
                return;
            }
            double damageFactor = unitService.getCounterFactor(attacker.getClass(), defender.getClass());

            damage = attacker.getDamage() * damageFactor;
            defender.setHealth(defender.getHealth() - damage);
            if (defender.getHealth() <= 0)
                killUnit(defender);
        }
        LOGGER.debug(attacker.getName() + " attacked " + defender.getName() + " for " + damage + " damage");
        LOGGER.debug("Defender " + defender.getName() + " has now " + defender.getHealth() + " health");

        Thread.sleep(500);
        attackUnit(attackerId, defenderId);
    }

    public Unit getUnitOfGivenId(Long id) {
        List<Unit> allUnits = game.getPlayers().stream()
                .flatMap(player -> player.getUnits().stream())
                .toList();
        for (Unit unit : allUnits)
            if (Objects.equals(unit.getId(), id))
                return unit;
        return null;
    }

    public void createUnit(UnitType type, double posX, double posY, Player player) throws InterruptedException {
        Thread.sleep(2500);
        Unit newUnit = UnitFactory.createUnit(type, posX, posY, player);
        player.getUnits().add(newUnit);
    }

    public void killUnit(Unit unit) {
        LOGGER.debug(unit.getName() + " has been defeated");
        unit.getPlayer().getUnits().remove(unit);
    }

    public void initializeNewPlayer() throws TooManyConnectionsException {
        if (game.getPlayers().size() > 3)
            throw new TooManyConnectionsException();
        game.getPlayers().add(new Player());
    }

    private void assignAndRunTask(Unit unitToMove, Runnable task, CommandType command) {
        Thread taskThread = new Thread(task);
        switch (command) {
            case MOVE -> {
                if (unitToMove.getMoveTask() != null)
                    unitToMove.getMoveTask().interrupt();
                unitToMove.setMoveTask(taskThread);
            }
            case ATTACK -> {
                if (unitToMove.getAttackTask() != null)
                    unitToMove.getAttackTask().interrupt();
                unitToMove.setAttackTask(taskThread);
            }
        }
        taskThread.start();
    }
}
