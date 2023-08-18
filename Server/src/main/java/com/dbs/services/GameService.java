package com.dbs.services;

import com.dbs.enumerations.CommandType;
import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.utils.UnitControlUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Getter
    private final Map<String, Game> games = new HashMap<>();

    public void moveUnit(Unit unit, Double newX, Double newY) {
        Runnable moveTask = () -> {
            try {
                UnitControlUtil.moveUnit(unit, newX, newY);
            } catch (InterruptedException e) {
                LOGGER.debug("Changed movement direction of " + unit + " to " + newX + "," + newY);
            }
        };
        assignAndRunTask(unit, moveTask, CommandType.MOVE);
    }

    public void attackUnit(Unit attacker, Unit defender) {
        Runnable attackTask = () -> {
            try {
                UnitControlUtil.attackUnit(attacker, defender);
            } catch (Exception e) {
                LOGGER.debug(attacker + " changed attack target to" + defender);
            }
        };
        assignAndRunTask(attacker, attackTask, CommandType.ATTACK);
    }

    public void createUnit(UnitType type, double posX, double posY, Player player) {
        Thread createUnitThread = new Thread(() -> {
            try {
                UnitControlUtil.createNewUnit(type, posX, posY, player);
            } catch (InterruptedException e) {
                LOGGER.debug("Unit creating interrupted");
            }
        });
        createUnitThread.start();
    }

    public Optional<Unit> getUnitOfGivenId(Long unitId, Game game) {
        List<Unit> allUnits = game.getPlayers().stream()
                .flatMap(player -> player.getUnits().stream())
                .toList();
        for (Unit unit : allUnits)
            if (Objects.equals(unit.getId(), unitId))
                return Optional.of(unit);
        return Optional.empty();
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
